package cy.ac.ucy.cs.epl425.LMS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    // 1. Define in-memory users
    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetailsList = new ArrayList<>();

        userDetailsList.add(User.withUsername("jsmith")
                .password(passwordEncoder().encode("epl42$"))
                .roles("EMPLOYEE")
                .build());

        userDetailsList.add(User.withUsername("atrevor")
                .password(passwordEncoder().encode("letmein"))
                .roles("EMPLOYEE", "MANAGER")
                .build());

        userDetailsList.add(User.withUsername("dalves")
                .password(passwordEncoder().encode("secure"))
                .roles("MANAGER")
                .build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }

    // 2. Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. Security filter chain with role-based permissions
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        AuthenticationManager authManager = authBuilder.build();

        http
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth

                // EMPLOYEE & MANAGER: view employee info
                .requestMatchers(HttpMethod.GET, "/api/employees/**").permitAll()

                // MANAGER: full control of employees
                .requestMatchers(HttpMethod.POST, "/api/employees/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/employees/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("MANAGER")

                // EMPLOYEE & MANAGER: create leave
                .requestMatchers(HttpMethod.POST, "/api/leaves/employees/**").hasAnyRole("EMPLOYEE", "MANAGER")

                // EMPLOYEE & MANAGER: view/update leave
                .requestMatchers(HttpMethod.GET, "/api/leaves/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/leaves/**").hasAnyRole("EMPLOYEE", "MANAGER")

                // MANAGER ONLY: delete leave
                .requestMatchers(HttpMethod.DELETE, "/api/leaves/**").hasRole("MANAGER")

                .requestMatchers("/", "/index.html").permitAll()
                // All others: authenticated users
                .anyRequest().authenticated()
            )
            .authenticationManager(authManager);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost")); // or "*" if no credentials
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // or list specific headers if needed

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

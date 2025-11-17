package cy.ac.ucy.cs.epl425.LMS.model;

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

@Table("employees")
public class Employee {

	@Id
	private Integer id;

	@Column("firstname")
	private String firstname;

	@Column("lastname")
	private String lastname;

	@Column("department")
	private String department;

    @Column("date_of_birth")
	private LocalDate dateOfBirth;

	@MappedCollection(idColumn = "id")
  	private Set<Leave> leaves;

	public Employee() {

	}

	public Employee(String firstname, String lastname, String department, LocalDate dateOfBirth) {
		this.firstname = firstname;
		this.lastname = lastname;
        this.department = department;
        this.dateOfBirth = dateOfBirth;
		leaves = new HashSet<>();
	}

	// getters
	public Integer getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}
	
	public String getLastname() {
		return lastname;
	}

	public String getDepartment() {
		return department;
	}

    public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

    public Set<Leave> getLeaves() {
		return leaves;
	}

	// setters
	public void setFirstname(String firstname) {
		this.firstname = firstname;
    }

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

    public void setDepartment(String department) {
		this.department = department;
	}

    public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setLeaves(Set<Leave> leaves) {
		this.leaves = leaves;
	}

	public void setLeave(Leave leave) {
		this.leaves.add(leave);
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", department=" + department
                + ", dateOfBirth=" + dateOfBirth + ", leaves=" + leaves + "]";
	}
}

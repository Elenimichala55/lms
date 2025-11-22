# Leave Management System (LMS)

A full-stack Leave Management System built using Spring Boot, MySQL, and Spring Security.
Employees can request leaves, managers can approve or reject them, and all data is stored securely in a relational database.

## Features

### Employee Features
- View employee information
- Submit leave requests
- View submitted leave history

### Manager Features
- Create, update, or delete employees
- Approve or reject leave requests
- Full access to employee and leave data

### Security
- Role-based access control (RBAC)
- In-memory authentication for:
- - EMPLOYEE
- - MANAGER
- Secured API endpoints using Spring Security

## Database Structure
### employees
| Column        | Type     |
| ------------- | -------- |
| id            | INT (PK) |
| firstname     | VARCHAR  |
| lastname      | VARCHAR  |
| department    | VARCHAR  |
| date_of_birth | DATE     |

### leaves
| Column      | Type     |
| ----------- | -------- |
| id          | INT (PK) |
| employee_id | INT (FK) |
| description | TEXT     |
| start_date  | DATE     |
| end_date    | DATE     |
| approved    | BOOLEAN  |

## Technologies Used
- Java 17
- Spring Boot 3
- Spring Data JDBC
- Spring Security (Basic Auth)
- MySQL
- Maven
- Tomcat Embedded

## Installation & Setup

1. Clone the repository
```bash
git clone https://github.com/Elenimichala55/lms.git
  cd lms
```

3. Configure MySQL connection
   Edit src/main/resources/application.properties:
```bash
  spring.datasource.url=jdbc:mysql://localhost:3306/lms
  spring.datasource.username=your_mysql_user
  spring.datasource.password=your_mysql_password
  spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

  spring.sql.init.mode=always
  spring.sql.init.schema-locations=classpath:schema.sql
  spring.sql.init.data-locations=classpath:data.sql
```

3. Create the database:
```bash
   CREATE DATABASE lms;
```

5. Run the application
```bash
   ./mvnw spring-boot:run
```

## Default Users (In-Memory)

| Username | Password | Roles            |
| -------- | -------- | ---------------- |
| jsmith   | 12345$   | EMPLOYEE         |
| atrevor  | letmein  | EMPLOYEE,MANAGER |
| dalves   | secure   | MANAGER          |

## API Endpoints
### Employees
```  
GET /api/employees — view all employees
POST /api/employees — create employee (Manager only)
PUT /api/employees/{id} — update employee (Manager only)
DELETE /api/employees/{id} — delete employee (Manager only)
```

### Leaves
```
POST /api/leaves/employees/{id} — request leave
GET /api/leaves — view all leaves
PUT /api/leaves/{id} — approve/update leave (Employee/Manager)
DELETE /api/leaves/{id} — delete leave (Manager only)
```

## License
This project is released under the MIT License.


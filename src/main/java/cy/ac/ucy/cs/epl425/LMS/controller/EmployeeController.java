package cy.ac.ucy.cs.epl425.LMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cy.ac.ucy.cs.epl425.LMS.model.Employee;
import cy.ac.ucy.cs.epl425.LMS.service.EmployeeService;

@RequestMapping("/api")
@RestController
public class EmployeeController {

  @Autowired
  EmployeeService employeeService;

  @GetMapping("/employees")
  //@RequestMapping(value = "/employees", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String department) {
    try {
      List<Employee> employees;

      if (department == null)
        employees = employeeService.getAllEmployees();
      else
        employees = employeeService.getEmployeesByDepartment(department);

      if (employees.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(employees, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @GetMapping("/employees/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer id) {
    Employee employee = employeeService.getEmployeeById(id);

    if (employee != null) {
      return new ResponseEntity<>(employee, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/employees")
  public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {  
    try {
      Employee _employee = employeeService
          .saveEmployee(new Employee(employee.getFirstname(), employee.getLastname(), employee.getDepartment(), employee.getDateOfBirth()));
      return new ResponseEntity<>(_employee, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/employees/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {
    Employee _employee = employeeService.getEmployeeById(id);

    if (_employee != null) {
        _employee.setFirstname(employee.getFirstname ());
        _employee.setLastname(employee.getLastname());
        _employee.setDepartment(employee.getDepartment());
        _employee.setDateOfBirth(employee.getDateOfBirth());

      return new ResponseEntity<>(employeeService.saveEmployee(_employee), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/employees")
  public ResponseEntity<HttpStatus> deleteAllEmployees() {
    try {
      employeeService.deleteAllEmployees();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @DeleteMapping("/employees/{id}")
  public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") Integer id) {
    try {
      employeeService.deleteEmployeeById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}

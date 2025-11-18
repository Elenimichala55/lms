package LMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import LMS.repository.EmployeeRepository;
import LMS.model.Employee;

@Service
public class EmployeeService {
    @Autowired EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<Employee>();
        this.employeeRepository.findAll().forEach(employees::add);
        return employees;
    }
    
    public Employee getEmployeeById(Integer id) {
        Optional<Employee> employee = this.employeeRepository.findById(id);
        if(employee.isPresent())
	        return employee.get();
        else 
            return null;
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartmentContaining(department);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeById(Integer id) {
        employeeRepository.deleteById(id);
    }

    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

}

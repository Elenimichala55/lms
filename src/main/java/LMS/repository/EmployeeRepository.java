package LMS.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import LMS.model.Employee;
import java.util.List;

// We can use CrudRepository’s methods: save(), findOne(), findById(), 
// findAll(), count(), delete(), deleteById() without implementing these methods.
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    //Retrieves a list of all Employees belonging to a specific
    //department (all departments containing the given string will be
    //taken in account).
    List<Employee> findByDepartmentContaining(String department);
    
}
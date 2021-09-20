/**
 * 
 */
package employee.management.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import employee.management.system.exception.InvalidEmailAddressExcpetion;
import employee.management.system.exception.ResourceNotFoundException;
import employee.management.system.exception.RootUserAlreadyPresentExcpetion;
import employee.management.system.model.Employee;
import employee.management.system.service.EmployeeService;
import employee.management.system.validations.EmployeeRequestValidator;

/**
 * @author omnitya.jha
 *
 */

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	private EmployeeRequestValidator validator;

	// get All Employees
	@GetMapping("/")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	// create new Employee
	@PostMapping("/")
	public Employee createEmployee(@Validated @RequestBody Employee employee)
			throws ResourceNotFoundException, InvalidEmailAddressExcpetion, RootUserAlreadyPresentExcpetion {
		validator.validateEmployee(employee, HttpMethod.POST.name());
		return employeeService.createEmployee(employee);
	}

	// get Employee by id
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long empId)
			throws ResourceNotFoundException {
		Employee employee = employeeService.getEmployeeById(empId);
		return ResponseEntity.ok().body(employee);
	}

	// update Employee by Id
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") long empId,
			@Validated @RequestBody Employee empDetails)
			throws ResourceNotFoundException, InvalidEmailAddressExcpetion, RootUserAlreadyPresentExcpetion {
		validator.validateEmployee(empDetails, HttpMethod.PUT.name());
		Employee employee = employeeService.updateEmployee(empId, empDetails);
		return ResponseEntity.ok().body(employee);
	}

	// delete Employee By Id
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") long empId) throws ResourceNotFoundException {
		employeeService.delete(empId);
		return ResponseEntity.ok().build();
	}

	// fetch the reportees
	@GetMapping("/{id}/reportees")
	public List<Employee> getReportees(@PathVariable(value = "id") long empId) {
		return employeeService.getReportees(empId);
	}

	// fetch the managers
	@GetMapping("/{id}/manager")
	public Employee getManager(@PathVariable(value = "id") long empId) throws ResourceNotFoundException {
		return employeeService.getManager(empId);
	}

}

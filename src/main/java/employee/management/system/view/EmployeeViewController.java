/**
 * 
 */
package employee.management.system.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import employee.management.system.exception.InvalidEmailAddressExcpetion;
import employee.management.system.exception.ResourceNotFoundException;
import employee.management.system.exception.RootUserAlreadyPresentExcpetion;
import employee.management.system.model.Employee;
import employee.management.system.controller.EmployeeController;

/**
 * @author omnitya.jha
 *
 */

@Controller
public class EmployeeViewController {
	
	@Autowired
	private EmployeeController employeeController;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		model.addAttribute("listEmployees", employeeController.getAllEmployees());
		return "index";
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		Employee emp = new Employee();
		model.addAttribute("employee", emp);
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) throws ResourceNotFoundException, InvalidEmailAddressExcpetion, RootUserAlreadyPresentExcpetion {
		employeeController.createEmployee(employee);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long empId, Model model) throws ResourceNotFoundException {
		ResponseEntity<Employee> employee = employeeController.getEmployeeById(empId);
		model.addAttribute("employee", employee.getBody());
		return "update_employee";
	}
	
	@PostMapping("/updateEmployee/{id}")
	public String updateEmployee(@PathVariable(value = "id") long empId, @ModelAttribute("employee") Employee employee) throws ResourceNotFoundException, InvalidEmailAddressExcpetion, RootUserAlreadyPresentExcpetion {
		employeeController.updateEmployee(empId, employee);
		return "redirect:/";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") long empId) throws ResourceNotFoundException {
		employeeController.deleteEmployee(empId);
		return "redirect:/";
	}

	//show all reportees
	
	@GetMapping("/showReportees/{id}")
	public String showReportees(@PathVariable(value = "id") long empId, Model model) throws ResourceNotFoundException {
		ResponseEntity<Employee> employee = employeeController.getEmployeeById(empId);
		model.addAttribute("employees", employeeController.getReportees(empId));
		model.addAttribute("manager", employee.getBody());
		return "show_reportees";
	}
	
	//show manager
	
	@GetMapping("/showManager/{id}")
	public String showManager(@PathVariable(value = "id") long empId, Model model) throws ResourceNotFoundException {
		Employee employee = employeeController.getManager(empId);
		model.addAttribute("manager", employee);
		return "show_manager";
	}
}

/**
 * 
 */
package employee.management.system.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;

import employee.management.system.dao.EmployeeDAO;
import employee.management.system.exception.InvalidEmailAddressExcpetion;
import employee.management.system.exception.ResourceNotFoundException;
import employee.management.system.exception.RootUserAlreadyPresentExcpetion;
import employee.management.system.model.Employee;

/**
 * @author omnitya.jha
 *
 */

@Validated
@ControllerAdvice
public class EmployeeRequestValidator {

	@Autowired
	private EmployeeDAO employeeDAO;

	static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	// validate employee object
	public void validateEmployee(Employee emp, String operation)
			throws ResourceNotFoundException, InvalidEmailAddressExcpetion, RootUserAlreadyPresentExcpetion {
		validateManagerId(emp, operation);
		validateEmailId(emp.getEmailId(), operation);
	}

	private void validateEmailId(String emailId, String operation) throws InvalidEmailAddressExcpetion {
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(EMAIL_PATTERN);
		java.util.regex.Matcher m = p.matcher(emailId);
		if (!m.matches()) {
			throw new InvalidEmailAddressExcpetion("Email Address is not valid : " + emailId);
		}
		if(operation == HttpMethod.POST.name()) {
			Employee emp = employeeDAO.findByEmailId(emailId);
			if(emp != null) {
				throw new InvalidEmailAddressExcpetion("Email Address already exist : " + emailId);
			}
		}
	}

	private void validateManagerId(Employee employee, String operation) throws ResourceNotFoundException, RootUserAlreadyPresentExcpetion {
		//manager with no id is root employee empId = 0.
		if (employee.getManagerId() != 0) {
			employeeDAO.findById(employee.getManagerId()).orElseThrow(
					() -> new ResourceNotFoundException("No record present for Manager Id : " + employee.getManagerId()));
		}
		if(operation == HttpMethod.PUT.name() && employee.getId() == employee.getManagerId()) {
			throw new ResourceNotFoundException("Employee Id cannot be same as Manager Id");
		}
	}

}

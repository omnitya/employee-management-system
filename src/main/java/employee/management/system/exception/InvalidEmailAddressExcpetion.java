/**
 * 
 */
package employee.management.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author omnitya.jha
 *
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidEmailAddressExcpetion extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7551959873191574726L;

	public InvalidEmailAddressExcpetion(String msg) {
		super(msg);
	}

}

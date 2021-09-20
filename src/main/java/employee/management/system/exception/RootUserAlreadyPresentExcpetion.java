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
public class RootUserAlreadyPresentExcpetion extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RootUserAlreadyPresentExcpetion(String msg) {
		super(msg);
	}
}

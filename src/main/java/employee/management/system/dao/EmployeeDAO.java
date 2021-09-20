/**
 * 
 */
package employee.management.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import employee.management.system.model.Employee;

/**
 * @author omnitya.jha
 *
 */

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Long>{
	
	 @Query("SELECT t FROM Employee t WHERE t.emailId = ?1")
	    Employee findByEmailId(String emailId);
	 
	 @Query("SELECT t FROM Employee t WHERE t.managerId = ?1")
	 List<Employee> findAllReportees(long id);
	 
}

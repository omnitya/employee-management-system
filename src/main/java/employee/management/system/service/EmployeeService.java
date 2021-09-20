package employee.management.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import employee.management.system.dao.EmployeeDAO;
import employee.management.system.exception.ResourceNotFoundException;
import employee.management.system.model.Employee;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    public Employee createEmployee(Employee employee) {
        return employeeDAO.save(employee);
    }

    public Employee getEmployeeById(@PathVariable(value = "id") long empId)
            throws ResourceNotFoundException {
        return employeeDAO.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("No record present for Employee Id : " + empId));
    }

    public Employee updateEmployee(long empId, Employee employee) throws ResourceNotFoundException {
        Employee existingEmp = employeeDAO.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("No record present for Employee Id : " + empId));
        existingEmp.setFirstName(employee.getFirstName());
        existingEmp.setLastName(employee.getLastName());
        existingEmp.setEmailId(employee.getEmailId());
        existingEmp.setManagerId(employee.getManagerId());
        return employeeDAO.save(employee);
    }

    public void delete(long empId) throws ResourceNotFoundException {
        assertExists(empId);
        employeeDAO.deleteById(empId);
    }

    private void assertExists(long empId) throws ResourceNotFoundException {
        employeeDAO.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("No record present for Employee Id : " + empId));
    }

    public List<Employee> getReportees(long empId) {
        return employeeDAO.findAllReportees(empId);
    }

    public Employee getManager(long empId) throws ResourceNotFoundException {
        Optional<Employee> employee = employeeDAO.findById(empId);
        return employeeDAO.findById(employee.get().getManagerId())
                .orElseThrow(() -> new ResourceNotFoundException("No record present for Employee Id : " + empId));
    }

}

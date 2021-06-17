package server.restservice.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import server.restservice.models.Employee;
import server.restservice.repository.EmployeeRepository;

@Service
@AllArgsConstructor
public class AdminService {

    @Autowired
    @Qualifier("repositoryImplementation")
    private EmployeeRepository employeeRepository;

    @Autowired
    PasswordEncoder encoder;

    public List<Employee> getEmployees() {
        return employeeRepository.getAllEmployees();
    }

    public void addEmployee(Map<String, String> employee) {
        Employee emp = new Employee(UUID.randomUUID(), employee.get("username"), employee.get("name"), employee.get("manager"), 0, 0, 0);
        Employee manager = employeeRepository.findEmployeeByUsername(employee.get("manager"));
        manager.writelock();
        manager.getEmployees().add(emp.getUsername());
        employeeRepository.addEmployee(emp, encoder.encode(employee.get("password")));
        employeeRepository.save(manager);
        manager.writeunlock();
    }

    public void deleteEmployee(String username) {
        employeeRepository.deleteEmployee(username);
    }

    public Integer[] getDays() {
        return employeeRepository.getDays();
    }

    public void editDays(Integer[] days) {
        employeeRepository.editDays(days);
    }

    public void updateManager(String employee_username, String new_manager_username) {
        Employee employee = employeeRepository.findEmployeeByUsername(employee_username);
        employee.writelock();
        String last_manager_username = employee.getManager();
        employee.setManager(new_manager_username);
        employeeRepository.save(employee);
        employee.writeunlock();
        Employee lastManager = employeeRepository.findEmployeeByUsername(last_manager_username);
        lastManager.writelock();
        lastManager.getEmployees().removeIf(emp->emp.equals(employee_username));
        employeeRepository.save(lastManager);
        lastManager.writeunlock();
        Employee new_manager = employeeRepository.findEmployeeByUsername(new_manager_username);
        new_manager.writelock();
        new_manager.getEmployees().add(employee_username);
        employeeRepository.save(new_manager);
        new_manager.writeunlock();

    }

    public void updatePassword(String username, String password) {
        employeeRepository.updateEmployeePassword(username, password);
    }

}

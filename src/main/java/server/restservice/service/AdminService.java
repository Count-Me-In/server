package server.restservice.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import server.restservice.models.Bid;
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
        if (employeeRepository.findEmployeeByUsername(employee.get("username")) != null) {
            throw new IllegalArgumentException("Username allready exists");
        }
        Employee emp = new Employee(UUID.randomUUID(), employee.get("username"), employee.get("name"), employee.get("manager"), 0, 0, 0);
        Bid[] bids = emp.getBids();
        for ( int i = 0; i < 5; i++) {
            bids[i] = new Bid(emp.getUsername(), i+1);
        }
        Employee manager = employeeRepository.findEmployeeByUsername(employee.get("manager"));
        manager.writelock();
        manager.getEmployees().add(emp.getUsername());
        employeeRepository.addEmployee(emp, encoder.encode(employee.get("password")));
        employeeRepository.save(manager);
        manager.writeunlock();
    }

    public void deleteEmployee(String username) {
        if (username.equals("admin")) {
            throw new IllegalArgumentException("cant delete admin");
        }
        Employee employee = employeeRepository.findEmployeeByUsername(username);
        employeeRepository.deleteEmployee(username);
        Employee manager = employeeRepository.findEmployeeByUsername(employee.getManager());
        manager.writelock();
        manager.setWeeklyPoints(manager.getWeeklyPoints() + employee.getWeeklyPoints());
        manager.setManagerPoints(manager.getManagerPoints() + employee.getManagerPoints());
        manager.getEmployees().removeIf(emp->emp.equals(username));
        manager.getEmployees().addAll(employee.getEmployees());
        employeeRepository.save(manager);
        manager.writeunlock();
        for (String emp_name : employee.getEmployees()) {
            Employee emp = employeeRepository.findEmployeeByUsername(emp_name);
            emp.writelock();
            emp.setManager(manager.getUsername());
            employeeRepository.save(emp);
            emp.writeunlock();
        }
    }

    public Integer[] getDays() {
        return employeeRepository.getDays();
    }

    public void editDays(Integer[] days) {
        employeeRepository.editDays(days);
    }

    public void updateManager(String employee_username, String new_manager_username) {
        if (employee_username.equals("admin")) {
            throw new IllegalArgumentException("Can't update admin");
        }
        Employee employee = employeeRepository.findEmployeeByUsername(employee_username);
        employee.writelock();
        String last_manager_username = employee.getManager();
        employee.setManager(new_manager_username);
        employee.setManagerPoints(0);
        employee.setWeeklyPoints(0);
        for(Bid bid : employee.getBids()) {
            bid.clearPoints();
        }
        employeeRepository.save(employee);
        employee.writeunlock();
        Employee lastManager = employeeRepository.findEmployeeByUsername(last_manager_username);
        lastManager.writelock();
        lastManager.getEmployees().removeIf(emp->emp.equals(employee_username));
        lastManager.setManagerPoints(lastManager.getManagerPoints() + employee.getManagerPoints());
        lastManager.setWeeklyPoints(lastManager.getWeeklyPoints() + employee.getManagerPoints());
        employeeRepository.save(lastManager);
        lastManager.writeunlock();
        Employee new_manager = employeeRepository.findEmployeeByUsername(new_manager_username);
        new_manager.writelock();
        new_manager.getEmployees().add(employee_username);
        employeeRepository.save(new_manager);
        new_manager.writeunlock();
        for(String emp : employee.getEmployees()) {
            clearEmpPoints(emp);
        }

    }

    public void updatePassword(String username, String password) {
        employeeRepository.updateEmployeePassword(username, password);
    }

    private void clearEmpPoints(String emp) {
        Employee employee = employeeRepository.findEmployeeByUsername(emp);
        employee.writelock();
        employee.setManagerPoints(0);
        employee.setWeeklyPoints(0);
        for (Bid bid : employee.getBids()) {
            bid.clearPoints();
        }
        employeeRepository.save(employee);
        for ( String direct_emp : employee.getEmployees()) {
            clearEmpPoints(direct_emp);
        }
        employee.writeunlock();
    }

}

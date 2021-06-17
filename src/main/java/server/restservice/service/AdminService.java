package server.restservice.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
        Bid[] bids = emp.get_bids();
        for ( int i = 0; i < 5; i++) {
            bids[i] = new Bid(emp.get_username(), i+1);
        }
        Employee manager = employeeRepository.findEmployeeByUsername(employee.get("manager"));
        manager.writelock();
        manager.get_employees().add(emp.get_username());
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
        Employee manager = employeeRepository.findEmployeeByUsername(employee.get_manager());
        manager.writelock();
        manager.set_weekly_added_points(manager.get_weekly_added_points() + employee.get_weekly_added_points());
        manager.set_manager_points(manager.get_managerPoints() + employee.get_managerPoints());
        manager.get_employees().removeIf(emp->emp.equals(username));
        manager.get_employees().addAll(employee.get_employees());
        employeeRepository.save(manager);
        manager.writeunlock();
        for (String emp_name : employee.get_employees()) {
            Employee emp = employeeRepository.findEmployeeByUsername(emp_name);
            emp.writelock();
            emp.set_manager(manager.get_username());
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
        String last_manager_username = employee.get_manager();
        employee.set_manager(new_manager_username);
        employee.set_manager_points(0);
        employee.set_weekly_added_points(0);
        for(Bid bid : employee.get_bids()) {
            bid.clearPoints();
        }
        employeeRepository.save(employee);
        employee.writeunlock();
        Employee lastManager = employeeRepository.findEmployeeByUsername(last_manager_username);
        lastManager.writelock();
        lastManager.get_employees().removeIf(emp->emp.equals(employee_username));
        lastManager.set_manager_points(lastManager.get_managerPoints() + employee.get_managerPoints());
        lastManager.set_weekly_added_points(lastManager.get_weekly_added_points() + employee.get_managerPoints());
        employeeRepository.save(lastManager);
        lastManager.writeunlock();
        Employee new_manager = employeeRepository.findEmployeeByUsername(new_manager_username);
        new_manager.writelock();
        new_manager.get_employees().add(employee_username);
        employeeRepository.save(new_manager);
        new_manager.writeunlock();
        for(String emp : employee.get_employees()) {
            clearEmpPoints(emp);
        }

    }

    public void updatePassword(String username, String password) {
        employeeRepository.updateEmployeePassword(username, password);
    }

    private void clearEmpPoints(String emp) {
        Employee employee = employeeRepository.findEmployeeByUsername(emp);
        employee.writelock();
        employee.set_manager_points(0);
        employee.set_weekly_added_points(0);
        for (Bid bid : employee.get_bids()) {
            bid.clearPoints();
        }
        employeeRepository.save(employee);
        for ( String direct_emp : employee.get_employees()) {
            clearEmpPoints(direct_emp);
        }
        employee.writeunlock();
    }

}

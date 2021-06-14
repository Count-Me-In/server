package server.restservice.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    public List<Employee> getEmployees() {
        return employeeRepository.getAllEmployees();
    }

    public void addEmployee(Map<String, String> employee) {
        Employee emp = new Employee(UUID.randomUUID(), employee.get("username"), employee.get("name"), employee.get("manager"), 0, 0, 0);
        employeeRepository.addEmployee(emp, employee.get("password"));
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

}

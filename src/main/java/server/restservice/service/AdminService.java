package server.restservice.service;

import java.util.List;
import java.util.Map;

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
        // employeeRepository.addEmployee();
    }

    public void deleteEmployee(String username) {
        // employeeRepository.deleteEmployee(username);
    }

    public Integer[] getDays() {
        return null;
    }

    public void editDays(Integer[] days) {
    }

}

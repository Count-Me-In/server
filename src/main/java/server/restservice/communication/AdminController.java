package server.restservice.communication;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import server.restservice.models.Employee;
import server.restservice.service.AdminService;

@RestController
@CrossOrigin()
@RequestMapping(path = "admin")
@AllArgsConstructor
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping(path = "getEmployees")
    public List<Employee> getEmployees(Authentication authentication) {
        return adminService.getEmployees();
    }

    @PostMapping(path = "addEmployee")
    public void addEmployee(Authentication authentication, @RequestBody Map<String, String> employee) {
        adminService.addEmployee(employee);
    }

    @DeleteMapping(path = "deleteEmployee")
    public void deleteEmployee(Authentication authentication, @RequestParam String username) {
        adminService.deleteEmployee(username);
    }

    @GetMapping(path = "getDays")
    public Integer[] getDays(Authentication authentication) {
        return adminService.getDays();
    }

    @PutMapping(path = "editDays")
    public void editDays(Authentication authentication, @RequestBody Map<String, Integer[]> days) {
        adminService.editDays(days.get("days"));
    }

}

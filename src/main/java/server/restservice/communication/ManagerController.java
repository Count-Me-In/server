package server.restservice.communication;


import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import server.restservice.models.Restriction;
import server.restservice.service.ManagerService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "managers")
@CrossOrigin
@AllArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping(path = "assignedEmployees}")
    public List<String> getAssignedEmployeesPerDay(Authentication authentication, @RequestBody Date date) {
        return managerService.getAssignedEmployees(authentication.getName(), date);
    }

    @PostMapping(path = "addRestriction")
    public void addRestriction(Authentication authentication, @RequestBody Restriction restriction, @RequestBody String employee_username) {
        managerService.addRestriction(authentication.getName(), restriction, employee_username);
    }

    @GetMapping(path = "getEmployees")
    public List<String> getEmployees(Authentication authentication) {
        return managerService.getEmployees(authentication.getName());
    }

    @PutMapping(path = "setEmployeePoints")
    public void setEmployeePoints(Authentication authentication, @RequestBody String employee_username, @RequestBody Integer points) {
        managerService.setEmployeePoints(authentication.getName(), employee_username, points);
    }

    //TODO: day or date
    @PostMapping(path = "planArrival")
    public void planArrival(Authentication authentication, @RequestBody Integer day) {
        managerService.planArrival(authentication.getName(), day);
    }
}

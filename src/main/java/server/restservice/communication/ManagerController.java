package server.restservice.communication;


import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import server.restservice.models.Assignings;
import server.restservice.models.Restriction;
import server.restservice.service.ManagerService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "managers")
@CrossOrigin
@AllArgsConstructor
public class ManagerController {

    private final ManagerService managerService = new ManagerService();

    @GetMapping(path = "getEmployees")
    public List<String> getEmployees(Authentication authentication) {
        return managerService.getEmployees(authentication.getName());
    }

    @GetMapping(path = "getEmployeesPoints")
    public Map<String, Integer> getEmployeesPoints(Authentication authentication) throws ParseException {
        return managerService.getEmployeePoints(authentication.getName());
    }

    @GetMapping(path = "getTotalPoints")
    public int getTotalPoints(Authentication authentication) throws ParseException {
        return managerService.getTotalPoints(authentication.getName());
    }

    @PostMapping(path = "setEmployeePoints")
    public Boolean setEmployeePoints(Authentication authentication, @RequestParam String employeename, @RequestParam String points) {
        managerService.setEmployeePoints(authentication.getName(), employeename, 1);
        return true;
    }

    @GetMapping(path = "getEmployeesRestrictions")
    public Map<String, Restriction> getEmployeesRestrictions(Authentication authentication) {
        return managerService.getEmployeeRestrictions(authentication.getName());
    }

    @PostMapping(path = "setRestrictions")
    public void addRestriction(Authentication authentication, @RequestParam Restriction restriction, @RequestParam String employee_username) {
        managerService.addRestriction(authentication.getName(), restriction, employee_username);
    }

    @GetMapping(path = "getEmployeeAssigning")
    public Assignings getEmployeeAssigning(Authentication authentication, @RequestParam String employeename) throws ParseException {
        return managerService.getEmployeeAssignings(authentication.getName(), employeename);
    }

}

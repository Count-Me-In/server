package server.restservice.communication;

import lombok.AllArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import server.restservice.models.Assignings;
import server.restservice.models.Restriction;
import server.restservice.service.ManagerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "managers")
@CrossOrigin
@AllArgsConstructor
public class ManagerController {

    private ManagerService managerService;

    @GetMapping(path = "getEmployees")
    public List<String> getEmployees(Authentication authentication) {
        return managerService.getEmployees(authentication.getName());
    }

    @GetMapping(path = "getEmployeesPoints")
    public Map<String, Integer> getEmployeesPoints(Authentication authentication) {
        return managerService.getEmployeePoints(authentication.getName());
    }

    @GetMapping(path = "getTotalPoints")
    public int getTotalPoints(Authentication authentication) {
        return managerService.getTotalPoints(authentication.getName());
    }

    @PostMapping(path = "setEmployeePoints")
    public Boolean setEmployeePoints(Authentication authentication, @RequestParam Map<String, Integer> employeesPoints) {
        for(Map.Entry<String,Integer> entry : (employeesPoints.entrySet())) {
            managerService.setEmployeePoints(authentication.getName(), entry.getKey(), entry.getValue());
        }
        return true;
    }

    @GetMapping(path = "getEmployeesRestrictions")
    public Map<String, Restriction> getEmployeesRestrictions(Authentication authentication) {
        return managerService.getEmployeeRestrictions(authentication.getName());
    }

    @PostMapping(path = "setRestrictions")
    public void addRestriction(Authentication authentication, @RequestBody Restriction restriction,
            @RequestParam String employee_username) {
        managerService.addRestriction(authentication.getName(), restriction, employee_username);
    }

    @GetMapping(path = "getEmployeeAssigning")
    public Assignings getEmployeeAssigning(Authentication authentication, @RequestParam String employeename) {
        return managerService.getEmployeeAssignings(authentication.getName(), employeename);
    }

}

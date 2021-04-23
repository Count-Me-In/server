package server.restservice.communication;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.service.EmployeeService;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping(path = "employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping()
    public String test(Authentication authentication) {
        return "test - " + authentication.getName();
    }

    @GetMapping(path = "bids_collection")
    public Bid[] getBidsCollection(Authentication authentication) {
        return employeeService.getBids(authentication.getName());
    }

    @PutMapping(path = "updateBids")
    public void updateBids(Authentication authentication, @RequestBody Bid[] bids) {
        employeeService.updateBids(authentication.getName(), bids);
    }

    @GetMapping(path = "employeePoints")
    public int getEmployeesPoints(Authentication authentication) {
        return employeeService.getEmployeesPoints(authentication.getName());
    }

    @GetMapping(path = "getEmployeeAssigning")
    public List<Assignings> getEmployeeAssigning(Authentication authentication) {
        return employeeService.getEmployeeAssignings(authentication.getName());
    }

}

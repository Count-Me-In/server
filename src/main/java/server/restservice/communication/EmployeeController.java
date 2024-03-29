package server.restservice.communication;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.EmployeeDetails;
import server.restservice.models.Restriction;
import server.restservice.service.EmployeeService;
import server.restservice.service.MailService;

@RestController
@CrossOrigin()
@RequestMapping(path = "employees")
@AllArgsConstructor
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private MailService mailService;

    @GetMapping(path = "all")
    public EmployeeDetails[] getEmployees(Authentication authentication) {
        return employeeService.getEmployees(authentication.getName());
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

    @GetMapping(path = "getEmployeesRestriction")
    public Restriction getEmployeesRestriction(Authentication authentication) {
        return employeeService.getEmployeesRestriction(authentication.getName());
    }

    @GetMapping(path = "getEmployeeAssigning")
    public Assignings getEmployeeAssigning(Authentication authentication) {
        return employeeService.getEmployeeAssignings(authentication.getName());
    }

    @PostMapping(path = "invites")
    public void sendMails(Authentication authentication, @RequestBody String[][] mails) {
        for (int i = 0; i < mails.length; i++) {
            if (mails[i].length > 0) {
                mailService.sendEmail(authentication.getName(), i, mails[i]);
            }
        }
    }

    @GetMapping(path = "auction")
    public void auction(Authentication authentication) {
        employeeService.auction();
    }

}

package server.restservice.communication;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.Employee;
import server.restservice.service.EmployeeService;
import server.restservice.service.MailService;


@RestController
@CrossOrigin()
@RequestMapping(path = "employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    private MailService mailService;

    @GetMapping(path = "all")
    public Employee[] getEmployees(Authentication authentication) {
        System.out.println("inside getEmployees");
        return employeeService.getEmployees(authentication.getName());
    }

    @GetMapping(path = "bids_collection")
    public Bid[] getBidsCollection(Authentication authentication) {
        System.out.println("inside bids_collection");
        return employeeService.getBids(authentication.getName());
    }

    @PutMapping(path = "updateBids")
    public void updateBids(Authentication authentication, @RequestParam Bid[] bids) {
        System.out.println("inside update bids");
        employeeService.updateBids(authentication.getName(), bids);
    }

    @GetMapping(path = "employeePoints")
    public int getEmployeesPoints(Authentication authentication) {
        return employeeService.getEmployeesPoints(authentication.getName());
    }

    @GetMapping(path = "getEmployeeAssigning")
    public Assignings getEmployeeAssigning(Authentication authentication) {
        return employeeService.getEmployeeAssignings(authentication.getName());
    }

    @PostMapping(path = "sendMails")
    public void sendMails(Authentication authentication, @RequestParam String[][] mails) {
        for(int i = 0; i < mails.length; i++){
            mailService.sendEmail(authentication.getName(),i, mails[i]);
        }

    }


}

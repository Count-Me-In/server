package server.restservice.communication;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.Employee;
import server.restservice.service.EmployeeService;
import server.restservice.service.MailService;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping(path = "employees")
@AllArgsConstructor
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService = new EmployeeService();

    @Autowired
    private final MailService mailService = new MailService();

    @GetMapping()
    public String test(Authentication authentication) {
        System.out.println("inside test");
        return "test - " + authentication.getName();
    }

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
    public Assignings getEmployeeAssigning(Authentication authentication) throws ParseException {
        return employeeService.getEmployeeAssignings(authentication.getName());
    }

    @PostMapping(path = "sendMails")
    public void sendMails(Authentication authentication, @RequestParam String[][] mails) throws ParseException {
        for(int i = 0; i < mails.length; i++){
            mailService.sendEmail(authentication.getName(),i, mails[i]);
        }

    }


}

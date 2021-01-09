package service;


import business_logic.SystemHandler;
import business_logic.models.Bid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/managers")
public class ManagerController {

    @GetMapping(value = "/assignedEmployees/{date}")
    public List<String> getAssignedEmployeesPerDay(@PathVariable Date date) {
        return SystemHandler.getAssignedEmployees(date);
    }

}

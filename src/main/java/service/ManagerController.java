package service;


import business_logic.SystemHandler;
import business_logic.models.Employee;
import business_logic.models.Restriction;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/managers")
public class ManagerController {

    @GetMapping(value = "/{manager_id}/assignedEmployees")
    public List<String> getAssignedEmployeesPerDay(@PathVariable UUID manager_is, @RequestBody Date date) {
        return SystemHandler.getAssignedEmployees(date);
    }

    //TODO: how to send manager id??
    @GetMapping(value = "/{employee_id}/addRestriction")
    public void addRestriction(@PathVariable UUID employee_id, @RequestBody Restriction restriction){
        SystemHandler.addRestriction(employee_id, restriction);
    }

    //TODO: return list of employees or just employees name+id??
    @GetMapping(value = "{manager_id}/getEmployees")
    public List<Employee> getEmployees(@PathVariable UUID manager_id){
        return SystemHandler.getEmployees(manager_id);
    }

    @GetMapping(value = "/{employee_id}/setEmployeePoints")
    public void setEmployeePoints(@PathVariable UUID employee_id, @RequestBody Integer points){
        SystemHandler.setEmployeePoints(employee_id, points);
    }

    //TODO: day or date????
    @GetMapping(value = "/{employee_id}/planArrival")
    public void planArrival(@PathVariable UUID employee_id, @RequestBody Integer day){
        SystemHandler.planArrival(employee_id, day);
    }
}

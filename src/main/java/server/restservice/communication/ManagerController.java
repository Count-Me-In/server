package server.restservice.communication;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.restservice.models.Employee;
import server.restservice.models.Restriction;
import server.restservice.service.ManagerService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/managers")
@AllArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping(value = "/{manager_id}/assignedEmployees")
    public List<String> getAssignedEmployeesPerDay(@PathVariable UUID manager_id, @RequestBody Date date) {
        return managerService.getAssignedEmployees(date);
    }

    //TODO: how to send manager id??
    @PostMapping(value = "/{employee_id}/addRestriction")
    public void addRestriction(@PathVariable UUID employee_id, @RequestBody Restriction restriction) {
        managerService.addRestriction(employee_id, restriction);
    }

    //TODO: return list of employees or just employees name+id??
    @GetMapping(value = "{manager_id}/getEmployees")
    public List<Employee> getEmployees(@PathVariable UUID manager_id) {
        return managerService.getEmployees(manager_id);
    }

    @PutMapping(value = "/{employee_id}/setEmployeePoints")
    public void setEmployeePoints(@PathVariable UUID employee_id, @RequestBody Integer points) {
        managerService.setEmployeePoints(employee_id, points);
    }

    //TODO: day or date????
    @PostMapping(value = "/{employee_id}/planArrival")
    public void planArrival(@PathVariable UUID employee_id, @RequestBody Integer day) {
        managerService.planArrival(employee_id, day);
    }
}

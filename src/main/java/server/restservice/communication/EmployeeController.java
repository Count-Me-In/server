package server.restservice.communication;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.service.EmployeeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping()
    public String test() {
        return "test";
    }

    @GetMapping(path = "{employee_id}/bids_collection")
    public Bid[] getBidsCollection(@PathVariable UUID employee_id) {
        return employeeService.getBids(employee_id);
    }

    @PutMapping(path = "{employee_id}/updateBids")
    public void updateBids(@PathVariable UUID employee_id, @RequestBody Bid[] bids) {
        employeeService.updateBids(employee_id, bids);
    }

    @GetMapping(path = "{employee_id}/employeePoints")
    public int getEmployeesPoints(@PathVariable UUID employee_id) {
        return employeeService.getEmployeesPoints(employee_id);
    }

    @GetMapping(path = "getEmployeeAssigning/{employee_id}")
    public List<Assignings> getEmployeeAssigning(@PathVariable UUID employee_id) {
        return employeeService.getEmployeeAssignings(employee_id);
    }

}

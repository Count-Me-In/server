package service;

import business_logic.SystemHandler;
import business_logic.models.Assignings;
import business_logic.models.Bid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @GetMapping(value = "/")
    public String test() {
        return "test";
    }

    @GetMapping(value = "/{employee_id}/bids_collection")
    public Bid[] getBidsCollection(@PathVariable UUID employee_id) {
        return SystemHandler.getBids(employee_id);
    }

    @GetMapping(value = "/{employee_id}/updateBids")
    public void updateBids(@PathVariable UUID employee_id, @RequestBody Bid[] bids){
        SystemHandler.updateBids(employee_id, bids);
    }

    @GetMapping(value = "/{employee_id}/employeePoints")
    public int getEmployeesPoints(@PathVariable UUID employee_id){
        return SystemHandler.getEmployeesPoints(employee_id);
    }

    @GetMapping(value = "/getEmployeeAssignings/{employee_id}")
    public List<Assignings> getEmployeeAssignings(@PathVariable UUID employee_id){
        return SystemHandler.getEmployeeAssignings(employee_id);
    }






}

package service;

import business_logic.SystemHandler;
import business_logic.models.Bid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Bid> getBidsCollection(@PathVariable UUID employee_id) {
        return SystemHandler.getBids(employee_id);
    }

}

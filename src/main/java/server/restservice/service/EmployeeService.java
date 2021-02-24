package server.restservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeService {

    public Bid[] getBids(UUID employee_id) {
        //TODO: get employee id from session
        return new Bid[5];
    }


    public void updateBids(UUID employee_id, Bid[] bids) {
        //TODO: update employee bids field and save employee to db
    }


    public int getEmployeesPoints(UUID employee_id) {
        //TODO: call to lambda that retrieves employee's points
        return 0;
    }


    public List<Assignings> getEmployeeAssignings(UUID employee_id) {
        //TODO: call engine
        return new ArrayList<>();
    }
}

package server.restservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    public Bid[] getBids(String username) {
        //TODO: get employee bids from repo
        return new Bid[5];
    }


    public void updateBids(String username, Bid[] bids) {
        //TODO: update employee bids field and save employee to repo
    }


    public int getEmployeesPoints(String username) {
        //TODO: get employee points from repo
        return 0;
    }


    public List<Assignings> getEmployeeAssignings(String username) {
        //TODO: get employee's assignings from repo
        return new ArrayList<>();
    }
}

package server.restservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.Employee;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;  

import java.text.SimpleDateFormat;  

@Service
@AllArgsConstructor
public class EmployeeService {

    public Bid[] getBids(String username) {
        //TODO: get employee bids from repo
        Bid[] bids = {new Bid(username, 0), new Bid(username, 1), new Bid(username, 2), new Bid(username, 3), new Bid(username, 4)};
        return bids;
    }


    public void updateBids(String username, Bid[] bids) {
        //TODO: update employee bids field and save employee to repo
    }


    public int getEmployeesPoints(String username) {
        //TODO: get employee points from repo
        return 0;
    }

    public Assignings getEmployeeAssignings(String username) throws ParseException {
        //TODO: get employee's assignings from repo
        Assignings as = new Assignings(username);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH");
        List<Date> lst = Arrays.asList( sdf.parse("2021-04-12T09:45"),
                                        sdf.parse("2021-04-19T09:45"),
                                        sdf.parse("2021-04-27T09:45"),
                                        sdf.parse("2021-04-28T09:45"),
                                        sdf.parse("2021-04-29T09:45"));
        as.addAssinedDays(lst);
        return as;
    }

    public Employee[] getEmployees(String username) {
        Employee[] emp= {new Employee("nuf@gmail.com", "Nufar Carmel","Itay Haizler", 60, false),
                new Employee("navi@gmail.com", "Shenhav Carmel","Moshik Shin", 25, false),
                new Employee("noych@gmail.com", "Noy Ezra","Maor Rozen", 95, false)
        };

        return emp;
    }
}

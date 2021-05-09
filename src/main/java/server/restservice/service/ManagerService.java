package server.restservice.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import server.restservice.models.Assignings;
import server.restservice.models.Restriction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class ManagerService {

    public List<String> getAssignedEmployees(String username, Date date) {
        //ToDo: Check if user is manager --> fetch all employee's names from engine
        return new ArrayList<>();
    }

    public void addRestriction(String username, Restriction restriction, String employee_username) {
        //TODO: check if the user is the manager of this employee, set employee restriction
    }

    public List<String> getEmployees(String username) {
        //TODO: return a list of the manager's employees
        //return new ArrayList<>();
        List<String> employees = Arrays.asList("Shenhav", "Noy", "Nufar", "Shauli");
        return employees;
    }

    public void setEmployeePoints(String username, String employee_username, Integer points) {
        //TODO
        System.out.println("inside setEmployeePoints");
        System.out.println("employee_username = " +  employee_username);
        System.out.println("points = " + points);
    }

    public void planArrival(String username, Integer day) {
        //TODO
    }


    public Assignings getEmployeeAssignings(String username, String employeename) throws ParseException {
        //TODO: check if manager of the employee??
        //TODO: we have an identical function on employee, call it?
        Assignings as = new Assignings(username);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH");
        if(employeename.equals("Noy")) {

            List<Date> lst = Arrays.asList(sdf.parse("2021-04-12T09:45"),
                    sdf.parse("2021-04-10T09:45"),
                    sdf.parse("2021-04-11T09:45"),
                    sdf.parse("2021-04-22T09:45"),
                    sdf.parse("2021-04-02T09:45"));
            as.addAssinedDays(lst);
        } else if (employeename.equals("Nufar")) {
            List<Date> lst = Arrays.asList(sdf.parse("2021-04-12T09:45"),
                    sdf.parse("2021-04-07T09:45"),
                    sdf.parse("2021-04-14T09:45"),
                    sdf.parse("2021-04-21T09:45"),
                    sdf.parse("2021-04-30T09:45"));
            as.addAssinedDays(lst);
        }
        else if(employeename.equals("Shenhav")){
            List<Date> lst = Arrays.asList(sdf.parse("2021-04-12T09:45"),
                    sdf.parse("2021-04-04T09:45"),
                    sdf.parse("2021-04-10T09:45"),
                    sdf.parse("2021-04-17T09:45"),
                    sdf.parse("2021-04-25T09:45"));
            as.addAssinedDays(lst);
        }
        else if(employeename.equals("Shauli")){
            List<Date> lst = Arrays.asList(sdf.parse("2021-04-12T09:45"),
                    sdf.parse("2021-04-11T09:45"),
                    sdf.parse("2021-04-22T09:45"),
                    sdf.parse("2021-04-01T09:45"),
                    sdf.parse("2021-04-09T09:45"));
            as.addAssinedDays(lst);
        }
        return as;
    }

    public int getTotalPoints(String name) {
        return 1000;
    }

    public Map<String, Integer> getEmployeePoints(String username) {
        //TODO: check if manager of the employee??
        //TODO: we have an identical function on employee, call it? return as map?
        HashMap<String, Integer> pointsMap = new HashMap<>();
        pointsMap.put("Noy", 100);
        pointsMap.put("Nufar", 200);
        pointsMap.put("Shenhav", 300);
        pointsMap.put("Shauli", 400);
        return pointsMap;
    }

    public Map<String, Restriction> getEmployeeRestrictions(String username) {
        HashMap<String, Restriction> restrictionsMap = new HashMap<>();
        Restriction r = new Restriction();
        r.add_allowed_day(1);
        r.add_allowed_day(4);
        restrictionsMap.put("Noy", r);

        r = new Restriction();
        r.add_allowed_day(2);
        r.add_allowed_day(3);
        restrictionsMap.put("Nufar", r);

        r = new Restriction();
        r.add_allowed_day(1);
        r.add_allowed_day(2);
        restrictionsMap.put("Shenhav", r);

        r = new Restriction();
        r.add_allowed_day(2);
        r.add_allowed_day(4);
        restrictionsMap.put("Shauli", r);

        return restrictionsMap;
    }
}

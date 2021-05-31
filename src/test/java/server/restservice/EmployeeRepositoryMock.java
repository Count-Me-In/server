package server.restservice;

import org.springframework.beans.factory.annotation.Value;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.Employee;
import server.restservice.repository.EmployeeRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeRepositoryMock implements EmployeeRepository {


    private List<Employee> employees;

    public EmployeeRepositoryMock(){
        employees = new ArrayList<Employee>();
        Employee admin = new Employee("admin", "admin", null, 100, 100, 300);
        admin.getEmployees().addAll(Arrays.asList("shauli", "nufar", "shenhav", "noy", "a"));
        String username = "admin";
        Bid[] bids = {new Bid(username, 0), new Bid(username, 1), new Bid(username, 2), new Bid(username, 3), new Bid(username, 4)};
        admin.setBids(bids);

        try {
            Assignings as = new Assignings("admin");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH");
            List<Date> lst = Arrays.asList(sdf.parse("2021-05-12T09:45"),
                    sdf.parse("2021-05-19T09:45"),
                    sdf.parse("2021-05-27T09:45"),
                    sdf.parse("2021-05-28T09:45"),
                    sdf.parse("2021-05-29T09:45"));
            as.addAssinedDays(lst);
            admin.setAssignings(as);
        }
        catch(Exception e){
            e.printStackTrace();
        }


        Employee shauli = new Employee("shauli", "shauli", "admin", 0, 0, 100);
        username = "shauli";
        Bid[] bids1 = {new Bid(username, 0), new Bid(username, 1), new Bid(username, 2), new Bid(username, 3), new Bid(username, 4)};
        admin.setBids(bids1);
        Assignings as = new Assignings("shauli");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH");
        List<Date> lst = null;
        try {
            lst = Arrays.asList(sdf.parse("2021-05-12T09:45"),
                    sdf.parse("2021-05-11T09:45"),
                    sdf.parse("2021-05-22T09:45"),
                    sdf.parse("2021-05-01T09:45"),
                    sdf.parse("2021-05-09T09:45"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        as.addAssinedDays(lst);
        shauli.setAssignings(as);
        this.employees.add(admin);
        this.employees.add(shauli);
    }

    public Employee findEmployeeByUsername(String username) {
        for(Employee emp: this.employees){
            if(emp.getUsername().equals(username)){
                return emp;
            }
        }
        return null;


    }

    public void save(Employee emp) {
        synchronized (emp) {
            // TODO: save employee in lambdas
        }
    }

    public Employee[] getAllEmployeeNames() {
        // TODO: get from lambdas
        Employee[] employeeArr = this.employees.toArray(new Employee[this.employees.size()]);
        return employeeArr;
    }

    public String getUsernamePass(String username) {
        // TODO: get from lambdas
        if (Arrays.asList("admin", "shauli", "nufar", "shenhav", "noy", "a").contains(username)) {
            return "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";
        } else {
            return null;
        }
    }

    public void cleanCache() {
        synchronized (this) {
        }
    }

}

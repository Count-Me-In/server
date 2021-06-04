package server.restservice;

import org.springframework.beans.factory.annotation.Value;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.Employee;
import server.restservice.models.Restriction;
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

        Employee admin = generateEmployee("admin");
        this.employees.add(admin);
        Employee shauli = generateEmployee("shauli");
        this.employees.add(shauli);
        Employee noy = generateEmployee("noy");
        this.employees.add(noy);
        Employee shenhave = generateEmployee("shenhave");
        this.employees.add(shenhave);
        Employee nufar = generateEmployee("nufar");
        this.employees.add(nufar);

    }


    private Employee generateEmployee(String username){
        Employee user = new Employee("admin", "admin", null, 100, 100, 300);
        user.getEmployees().addAll(Arrays.asList("shauli", "nufar", "shenhav", "noy", "a"));
        Bid[] bids = {new Bid(username, 0), new Bid(username, 1), new Bid(username, 2), new Bid(username, 3), new Bid(username, 4)};
        user.setBids(bids);

        try {
            Assignings as = new Assignings(username);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH");
            List<Date> lst = Arrays.asList(sdf.parse("2021-05-12T09:45"),
                    sdf.parse("2021-05-19T09:45"),
                    sdf.parse("2021-05-27T09:45"),
                    sdf.parse("2021-05-28T09:45"),
                    sdf.parse("2021-05-29T09:45"));
            as.addAssinedDays(lst);
            user.setAssignings(as);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Restriction rst = new Restriction();
        List<Integer> allowed = Arrays.asList(0, 1, 2, 3, 4);
        rst.set_allowed_days(allowed);

        user.setRestrictions(rst);
        return user;
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

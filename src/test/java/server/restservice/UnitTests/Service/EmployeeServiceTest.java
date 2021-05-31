package server.restservice.UnitTests.Service;

import org.junit.jupiter.api.Test;
import server.restservice.EmployeeRepositoryMock;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {


    EmployeeService employeeService = new EmployeeService(new EmployeeRepositoryMock());

    @org.junit.Test
    void getBidsTestExistingUser(){

        String username = "admin";
        Bid[] ans = employeeService.getBids(username);
        Bid[] expected = {new Bid(username, 0), new Bid(username, 1), new Bid(username, 2), new Bid(username, 3), new Bid(username, 4)};
        assertEquals(ans[0].getPercentage(), expected[0].getPercentage());
        assertEquals(ans[1].getPercentage(), expected[1].getPercentage());
        assertEquals(ans[2].getPercentage(), expected[2].getPercentage());
        assertEquals(ans[3].getPercentage(), expected[3].getPercentage());
        assertEquals(ans[4].getPercentage(), expected[4].getPercentage());
    }

    @org.junit.Test
    void getBidsTestNonExistingUser(){

        try {
            Bid[] ans = employeeService.getBids("toya");
            throw new AssertionError();
        }
        catch(Exception e){

        }
    }

    @Test
    void getBids() {
        getBidsTestExistingUser();
        getBidsTestNonExistingUser();
    }

    @org.junit.Test
    void updateBidsExistingUser() {
        String username = "admin";
        Bid bid0 = new Bid(username, 0);
        bid0.setPercentage(20);
        bid0.setPoints(20);
        Bid bid1 = new Bid(username, 1);
        bid1.setPercentage(20);
        bid1.setPoints(20);
        Bid bid2 = new Bid(username, 2);
        bid2.setPercentage(20);
        bid2.setPoints(20);
        Bid bid3 = new Bid(username, 3);
        bid3.setPercentage(20);
        bid3.setPoints(20);
        Bid bid4 = new Bid(username, 4);
        bid4.setPercentage(20);
        bid4.setPoints(20);
        Bid[] newBids = {bid0, bid1, bid2, bid3, bid4};
        employeeService.updateBids(username, newBids);

        Bid[] result = employeeService.getBids(username);
        assertEquals(newBids[0].getPercentage(), result[0].getPercentage());
        assertEquals(newBids[1].getPercentage(), result[1].getPercentage());
        assertEquals(newBids[2].getPercentage(), result[2].getPercentage());
        assertEquals(newBids[3].getPercentage(), result[3].getPercentage());
        assertEquals(newBids[4].getPercentage(), result[4].getPercentage());
    }

    @org.junit.Test
    void updateBidsNonExistingUser() {
        try {
            employeeService.updateBids("toya", new Bid[5]);
            throw new AssertionError();
        }
        catch(Exception e){

        }
    }

    @Test
    void updateBids() {
        updateBidsExistingUser();
        updateBidsNonExistingUser();
    }


    @org.junit.Test
    void getEmployeesPointsExistingUser() {
        int points = employeeService.getEmployeesPoints("admin");
        assertEquals(points, 100);
    }

    @org.junit.Test
    void getEmployeesPointsNonExistingUser() {
        try {
            int points = employeeService.getEmployeesPoints("toya");
            throw new AssertionError();
        }
        catch(Exception e){

        }
    }

    @Test
    void getEmployeesPoints() {
        getEmployeesPointsExistingUser();
        getEmployeesPointsNonExistingUser();

    }


    @org.junit.Test
    void getEmployeeAssigningsExistingUser() {
        String username = "admin";
        Assignings ans = employeeService.getEmployeeAssignings(username);


        assertEquals(ans.getAssignedDays(), createAssignings().getAssignedDays());

    }

    @org.junit.Test
    void getEmployeeAssigningsNonExistingUser() {
        try {
            String username = "toya";
            Assignings ans = employeeService.getEmployeeAssignings(username);
            throw new AssertionError();
        }
        catch(Exception e){

        }
    }

    @Test
    void getEmployeeAssignings() {
        getEmployeeAssigningsExistingUser();
        getEmployeeAssigningsNonExistingUser();
    }

    @Test
    void getEmployees() {
        Employee[] ans = employeeService.getEmployees("admin");
        Employee[] expected = mockEmployees();
        if (ans.length != expected.length)
            throw new AssertionError();

        List<String> ansNames = new ArrayList<>();
        for(Employee emp: ans){
            ansNames.add(emp.getUsername());
        }

        List<String> expectedNames = new ArrayList<>();
        for(Employee emp: expected){
            expectedNames.add(emp.getUsername());
        }

        assertEquals(ansNames, expectedNames);
    }





    private Employee[] mockEmployees(){
        List<Employee> employees = new ArrayList<Employee>();
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
        employees.add(admin);
        employees.add(shauli);

        Employee[] ans = employees.toArray(new Employee[employees.size()]);
        return ans;
    }

    private Assignings createAssignings(){
        try {
            Assignings as = new Assignings("admin");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH");
            List<Date> lst = Arrays.asList(sdf.parse("2021-05-12T09:45"),
                    sdf.parse("2021-05-19T09:45"),
                    sdf.parse("2021-05-27T09:45"),
                    sdf.parse("2021-05-28T09:45"),
                    sdf.parse("2021-05-29T09:45"));
            as.addAssinedDays(lst);
            return as;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
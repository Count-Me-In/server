package server.restservice.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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




class EmployeeServiceTest {


    EmployeeService employeeService = new EmployeeService(new EmployeeRepositoryMock());

    @Test
    void getBidsTestExistingUser(){

        String username = "admin";
        Bid[] ans = employeeService.getBids(username);
        Bid[] expected = {new Bid(username, 0), new Bid(username, 1), new Bid(username, 2), new Bid(username, 3), new Bid(username, 4)};
        assertArrayEquals(ans, expected);
    }

    @Test
    void getBidsTestNonExistingUser(){

        try {
            Bid[] ans = employeeService.getBids("toya");
            throw new AssertionError();
        }
        catch(Exception e){
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }

    @Test
    void getBids() {
        getBidsTestExistingUser();
        getBidsTestNonExistingUser();
    }

    //--------------------------------------------------------------------------------------------------

    @Test
    void updateBidsExistingUser() {
        String username = "admin";
        Bid bid0 = new Bid(username, 0);
        bid0.setPercentage(20);
        Bid bid1 = new Bid(username, 1);
        bid1.setPercentage(20);
        Bid bid2 = new Bid(username, 2);
        bid2.setPercentage(20);
        Bid bid3 = new Bid(username, 3);
        bid3.setPercentage(20);
        Bid bid4 = new Bid(username, 4);
        bid4.setPercentage(20);
        Bid[] newBids = {bid0, bid1, bid2, bid3, bid4};
        employeeService.updateBids(username, newBids);

        Bid[] result = employeeService.getBids(username);
        assertArrayEquals(newBids, result);
    }

    @Test
    void updateBidsInvalidBids() {
        String username = "admin";
        Bid bid0 = new Bid(username, 0);
        bid0.setPercentage(0);
        Bid bid1 = new Bid(username, 1);
        bid1.setPercentage(0);
        Bid bid2 = new Bid(username, 2);
        bid2.setPercentage(0);
        Bid bid3 = new Bid(username, 3);
        bid3.setPercentage(0);
        Bid bid4 = new Bid(username, 4);
        bid4.setPercentage(0);
        Bid[] newBids = {bid0, bid1, bid2, bid3, bid4};
        employeeService.updateBids(username, newBids);

        try {
            Bid[] result = employeeService.getBids(username);
        }
        catch (Exception e){
            assertEquals(e.getMessage(), "Bids aren't valid");
        }
    }

    @Test
    void updateBidsNonExistingUser() {
        try {
            employeeService.updateBids("toya", new Bid[5]);
            throw new AssertionError();
        }
        catch(Exception e){
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }

    @Test
    void updateBids() {
        updateBidsExistingUser();
        updateBidsInvalidBids();
        updateBidsNonExistingUser();
    }

    //--------------------------------------------------------------------------------------------------

    @Test
    void getEmployeesPointsExistingUser() {
        int points = employeeService.getEmployeesPoints("admin");
        assertEquals(points, 100);
    }

    @Test
    void getEmployeesPointsNonExistingUser() {
        try {
            int points = employeeService.getEmployeesPoints("toya");
            throw new AssertionError();
        }
        catch(Exception e){
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }

    @Test
    void getEmployeesPoints() {
        getEmployeesPointsExistingUser();
        getEmployeesPointsNonExistingUser();

    }

    //--------------------------------------------------------------------------------------------------

    @Test
    void getEmployeeAssigningsExistingUser() {
        String username = "admin";
        Assignings ans = employeeService.getEmployeeAssignings(username);


        assertEquals(ans.getAssignedDays(), createAssignings().getAssignedDays());

    }

    @Test
    void getEmployeeAssigningsNonExistingUser() {
        try {
            String username = "toya";
            Assignings ans = employeeService.getEmployeeAssignings(username);
            throw new AssertionError();
        }
        catch(Exception e){
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }

    @Test
    void getEmployeeAssignings() {
        getEmployeeAssigningsExistingUser();
        getEmployeeAssigningsNonExistingUser();
    }

    //--------------------------------------------------------------------------------------------------

    @Test
    void getEmployees() {

        List<String> expected = Arrays.asList("shauli", "nufar", "shenhav", "noy", "a", "toya");
        List<Employee> result = Arrays.asList(employeeService.getEmployees("admin"));

        List<String> resultNames = new ArrayList<>();
        for(Employee emp: result){
            resultNames.add(emp.getUsername());
        }

        assertEquals(resultNames, expected);
    }

    //--------------------------------------------------------------------------------------------------

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
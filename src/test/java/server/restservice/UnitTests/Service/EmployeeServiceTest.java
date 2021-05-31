
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import server.restservice.EmployeeRepositoryMock;
import server.restservice.models.Assignings;
import server.restservice.models.Bid;
import server.restservice.models.Employee;
import server.restservice.repository.EmployeeRepository;
import server.restservice.service.EmployeeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

        Bid[] ans = employeeService.getBids("toya");
        assertArrayEquals(ans, null);
    }


    @Test
    void updateBids() {
    }

    @Test
    void getEmployeesPoints() {
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
    @Test
    void getEmployeeAssigningsExistingUser() {
        String username = "admin";
        Assignings ans = employeeService.getEmployeeAssignings(username);

        assertEquals(ans, createAssignings());

    }

    @Test
    void getEmployeeAssigningsNonExistingUser() {
        String username = "toya";
        Assignings ans = employeeService.getEmployeeAssignings(username);
        assertEquals(ans, null);

    }



    @Test
    void getEmployees() {

        Employee[] ans = employeeService.getEmployees("admin");
        assertArrayEquals(ans, mockEmployees());
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

}
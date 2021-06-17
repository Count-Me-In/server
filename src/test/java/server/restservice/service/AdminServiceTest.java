package server.restservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import server.restservice.EmployeeRepositoryMock;
import server.restservice.models.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    AdminService adminService = new AdminService(new EmployeeRepositoryMock(), new BCryptPasswordEncoder());

    @Test
    void getEmployees() {
        List<String> expected = Arrays.asList("admin", "shauli", "noy", "shenhav", "nufar", "a", "toya");
        List<Employee> result = adminService.getEmployees();

        List<String> resultNames = new ArrayList<>();
        for (Employee emp : result) {
            resultNames.add(emp.get_username());
        }

        assertEquals(expected, resultNames);
    }

    @Test
    void addEmployee() {
        HashMap<String, String> empMap = new HashMap<String, String>();
        empMap.put("name", "John");
        empMap.put("username", "John@gmail.com");
        empMap.put("manager", "nufar");
        empMap.put("password", "password");

        adminService.addEmployee(empMap);
        List<Employee> empLst = adminService.getEmployees();
        List<String> resultNames = new ArrayList<>();
        for (Employee emp : empLst) {
            resultNames.add(emp.get_username());
        }

        assertTrue(resultNames.contains("John@gmail.com"));

        adminService.deleteEmployee("John@gmail.com");
    }


    @Test
    void deleteEmployee() {
        HashMap<String, String> empMap = new HashMap<String, String>();
        empMap.put("name", "John");
        empMap.put("username", "John@gmail.com");
        empMap.put("manager", "nufar");
        empMap.put("password", "password");

        adminService.addEmployee(empMap);

        adminService.deleteEmployee("John@gmail.com");

        List<Employee> empLst = adminService.getEmployees();
        List<String> resultNames = new ArrayList<>();
        for (Employee emp : empLst) {
            resultNames.add(emp.get_username());
        }

        assertTrue(!resultNames.contains("John@gmail.com"));
    }

    @Test
    void getDays() {
        Integer[] expectedDays = { 4, 3, 4, 4, 3 };
        Integer[] resultDays = adminService.getDays();

        assertArrayEquals(expectedDays, resultDays);
    }

    @Test
    void editDays() {
        Integer[] expectedDays = { 10, 10, 10, 10, 10 };

        adminService.editDays(expectedDays);
        Integer[] resultDays = adminService.getDays();

        assertArrayEquals(expectedDays, resultDays);

        Integer[] prevDays = { 10, 20, 30, 20, 10 };
        adminService.editDays(prevDays);

    }
}
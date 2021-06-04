package server.restservice.service;

import org.junit.jupiter.api.Test;
import server.restservice.EmployeeRepositoryMock;
import server.restservice.models.Restriction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerServiceTest {


    ManagerService managerService = new ManagerService(new EmployeeRepositoryMock());

    @Test
    void addRestriction() {
        Restriction rst = new Restriction();
        List<Integer> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        rst.set_allowed_days(lst);
        managerService.addRestriction("admin", rst, "shauli");
        //?????


    }

    @Test
    void getEmployees() {

    }




    @Test
    void setEmployeePoints() {
        managerService.setEmployeePoints("admin", "shauli", 200);
        double result = managerService.getEmployeePoints("admin").getOrDefault("shauli", -1);
        assertEquals(result, 200);

        managerService.setEmployeePoints("admin", "toya", 200);
        result = managerService.getEmployeePoints("admin").getOrDefault("toya", -1);
        assertEquals(result, -1);
    }

    @Test
    void getEmployeeAssignings() {

    }

    @Test
    void getTotalPoints() {

    }

    @Test
    void getEmployeePoints() {

    }

    @Test
    void getEmployeeRestrictions() {

    }
}
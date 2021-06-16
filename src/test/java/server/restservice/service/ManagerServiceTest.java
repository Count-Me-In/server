package server.restservice.service;

import org.junit.jupiter.api.Test;
import server.restservice.EmployeeRepositoryMock;
import server.restservice.models.Assignings;
import server.restservice.models.EmployeeDetails;
import server.restservice.models.Restriction;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ManagerServiceTest {

    ManagerService managerService = new ManagerService(new EmployeeRepositoryMock());

    @Test
    void addRestrictionExistingUser() {
        Restriction rst = new Restriction();
        List<Integer> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        rst.set_allowed_days(lst);
        managerService.addRestriction("admin", rst, "shauli");
        Restriction result = managerService.getRestriction("admin", "shauli");

        assertEquals(rst.get_allowed_days(), result.get_allowed_days());
    }

    @Test
    void addRestrictionExistingNonEmployeeUser() {
        try {
            managerService.addRestriction("admin", new Restriction(), "toya");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals("Can't update employee", e.getMessage());
        }
    }

    @Test
    void addRestrictionNonExistingUser() {
        try {
            managerService.addRestriction("admin", new Restriction(), "phistuk");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals("Employee username doesn't exists", e.getMessage());
        }
    }

    // --------------------------------------------------------------------------------------------------

    @Test
    void getRestrictionExistingUser() {
        List<Integer> allowed = Arrays.asList(1, 2, 3);
        Restriction result = managerService.getRestriction("admin", "noy");

        assertEquals(allowed, result.get_allowed_days());
    }

    @Test
    void getRestrictionExistingNonEmployeeUser() {
        try {
            managerService.getRestriction("admin", "toya");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals("Can't access employee", e.getMessage());
        }
    }

    @Test
    void getRestrictionNonExistingUser() {
        try {
            managerService.getRestriction("admin", "Phistuk");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals("Employee username doesn't exists", e.getMessage());
        }
    }

    // --------------------------------------------------------------------------------------------------

    @Test
    void getEmployeesExistingUser() {
        List<String> expected = Arrays.asList("shauli", "nufar", "shenhav", "noy");
        List<EmployeeDetails> result = managerService.getEmployees("admin");
        List<String> actual = new ArrayList<String>();
        for (EmployeeDetails employee : result) {
            actual.add(employee.get_username());
        }
        Collections.sort(expected);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getEmployeesNonExistingUser() {
        try {
            managerService.getEmployees("phistuk");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals("Employee username doesn't exists", e.getMessage());
        }
    }

    // --------------------------------------------------------------------------------------------------

    @Test
    void setEmployeePointsExistingUser() {
        managerService.setEmployeePoints("admin", "shauli", 200);
        for (EmployeeDetails result : managerService.getEmployeePoints("admin")) {
            if (result.get_username().equals("shauli"))
                assertEquals(200, result.get_points());
        }
    }

    @Test
    void setEmployeePointsExistingNonEmployeeUser() {
        try {
            managerService.setEmployeePoints("admin", "toya", 200);
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals("Can't update employee", e.getMessage());
        }
    }

    @Test
    void setEmployeePointsNonExistingUser() {
        try {
            managerService.setEmployeePoints("admin", "Phistuk", 200);
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals("Can't update employee", e.getMessage());
        }
    }

    // --------------------------------------------------------------------------------------------------

    @Test
    void getEmployeePointsExistingUser() {
        for (EmployeeDetails result : managerService.getEmployeePoints("admin")) {
            if (result.get_username().equals("nufar"))
                assertEquals(100, result.get_points());
        }
    }

    @Test
    void getEmployeePointsNonExistingUser() {
        try {
            managerService.setEmployeePoints("admin", "Phistuk", 200);
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals("Can't update employee", e.getMessage());
        }
    }

    // --------------------------------------------------------------------------------------------------

    @Test
    void getEmployeeAssigningsExistingUser() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH");
        List<Long> lst = new ArrayList<>();
        try {
            lst = Arrays.asList(sdf.parse("2021-05-12T09:45").toInstant().getEpochSecond(),
                    sdf.parse("2021-05-19T09:45").toInstant().getEpochSecond(),
                    sdf.parse("2021-05-27T09:45").toInstant().getEpochSecond(),
                    sdf.parse("2021-05-28T09:45").toInstant().getEpochSecond(),
                    sdf.parse("2021-05-29T09:45").toInstant().getEpochSecond());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assignings result = managerService.getEmployeeAssignings("admin", "shenhav");
        assertEquals(lst, result.getAssignedDays());
    }

    @Test
    void getEmployeeAssigningsExistingNonEmployeeUser() {
        try {
            managerService.getEmployeeAssignings("admin", "toya");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals("Can't access employee", e.getMessage());
        }
    }

    @Test
    void getEmployeeAssigningsNonExistingUser() {
        try {
            managerService.getEmployeeAssignings("admin", "phistuk");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals("Employee username doesn't exists", e.getMessage());
        }
    }

    // --------------------------------------------------------------------------------------------------

    @Test
    void getTotalPointsExistingUser() {
        double result = managerService.getTotalPoints("admin");
        assertEquals(300.0, result);
    }

    @Test
    void getTotalPointsNonExistingUser() {
        try {
            managerService.getTotalPoints("phistuk");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }

    // --------------------------------------------------------------------------------------------------

    @Test
    void getEmployeeRestrictionsExistingUser() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);

        List<EmployeeDetails> result = managerService.getEmployeeRestrictions("shauli");

        for (EmployeeDetails entry : result) {
            assertEquals(expected, entry.get_restriction().get_allowed_days());
        }
    }

    @Test
    void getEmployeeRestrictionsNonExistingUser() {
        try {
            managerService.getEmployeeRestrictions("phistuk");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals("Employee username doesn't exists", e.getMessage());
        }
    }

}
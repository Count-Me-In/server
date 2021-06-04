package server.restservice.service;

import org.junit.jupiter.api.Test;
import server.restservice.EmployeeRepositoryMock;
import server.restservice.models.Assignings;
import server.restservice.models.Restriction;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class ManagerServiceTest {


    ManagerService managerService = new ManagerService(new EmployeeRepositoryMock());



    void addRestrictionExistingUser(){
        Restriction rst = new Restriction();
        List<Integer> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        rst.set_allowed_days(lst);
        managerService.addRestriction("admin", rst, "shauli");
        Restriction result = managerService.getRestriction("admin", "shauli");

        assertEquals(result.get_allowed_days(), rst.get_allowed_days());
    }


    void addRestrictionExistingNonEmployeeUser() {
        try {
            managerService.addRestriction("admin", new Restriction(), "Toya");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Can't update employee");
        }
    }

    void addRestrictionNonExistingUser() {
        try {
            managerService.addRestriction("admin", new Restriction(), "Phistuk");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }
    @Test
    void addRestriction() {
        addRestrictionExistingUser();
        addRestrictionExistingNonEmployeeUser();
        addRestrictionNonExistingUser();
    }

    //--------------------------------------------------------------------------------------------------
    void getRestrictionExistingUser(){
        List<Integer> allowed = Arrays.asList(0, 1, 2, 3, 4);
        Restriction result = managerService.getRestriction("admin", "noy");

        assertEquals(result.get_allowed_days(), allowed);
    }


    void getRestrictionExistingNonEmployeeUser() {
        try {
            managerService.getRestriction("admin", "Toya");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Can't update employee");
        }
    }

    void getRestrictionNonExistingUser() {
        try {
            managerService.getRestriction("admin", "Phistuk");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }

    @Test
    void getRestriction() {
        getRestrictionExistingUser();
        getRestrictionExistingNonEmployeeUser();
        getRestrictionNonExistingUser();
    }

    //--------------------------------------------------------------------------------------------------

    void getEmployeesExistingUser(){
        List<String> expected = Arrays.asList("shauli", "nufar", "shenhav", "noy", "a");
        List<String> result = managerService.getEmployees("admin");

        assertEquals(result, expected);
    }

    void getEmployeesNonExistingUser(){
        try {
            managerService.getEmployees("phistuk");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }

    @Test
    void getEmployees() {
        getEmployeesExistingUser();
        getEmployeesNonExistingUser();
    }

    //--------------------------------------------------------------------------------------------------

    void setEmployeePointsExistingUser(){
        managerService.setEmployeePoints("admin", "shauli", 200);
        double result = managerService.getEmployeePoints("admin").getOrDefault("shauli", -1);
        assertEquals(result, 200.0);
    }


    void setEmployeePointsExistingNonEmployeeUser() {
        try {
            managerService.setEmployeePoints("admin", "toya", 200);
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Can't update employee");
        }
    }

    void setEmployeePointsNonExistingUser() {
        try {
            managerService.setEmployeePoints("admin", "Phistuk", 200);
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }

    @Test
    void setEmployeePoints() {
        setEmployeePointsExistingUser();
        setEmployeePointsExistingNonEmployeeUser();
        setEmployeePointsNonExistingUser();
    }

    //--------------------------------------------------------------------------------------------------

    void getEmployeePointsExistingUser(){
        double result = managerService.getEmployeePoints("admin").getOrDefault("nufar", -1);
        assertEquals(result, 100.0);
    }


    void getEmployeePointsNonExistingUser() {
        try {
            managerService.setEmployeePoints("admin", "Phistuk", 200);
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }

    @Test
    void getEmployeePoints() {
        getEmployeePointsExistingUser();
        getEmployeePointsNonExistingUser();
    }

    //--------------------------------------------------------------------------------------------------

    void getEmployeeAssigningsExistingUser(){
        List<String> expected = Arrays.asList("shauli", "nufar", "shenhav", "noy", "a");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH");
        List<Date> lst = new ArrayList<>();
        try {
            lst = Arrays.asList(sdf.parse("2021-05-12T09:45"),
                    sdf.parse("2021-05-19T09:45"),
                    sdf.parse("2021-05-27T09:45"),
                    sdf.parse("2021-05-28T09:45"),
                    sdf.parse("2021-05-29T09:45"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Assignings result = managerService.getEmployeeAssignings("admin", "shenhav");
        assertEquals(result.getAssignedDays(), lst);
    }

    void getEmployeeAssigningsExistingNonEmployeeUser(){
        try {
            managerService.getEmployeeAssignings("admin", "toya");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Can't access employee");
        }
    }

    void getEmployeeAssigningsNonExistingUser(){
        try {
            managerService.getEmployeeAssignings("admin", "phistuk");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }

    @Test
    void getEmployeeAssignings() {
        getEmployeeAssigningsExistingUser();
        getEmployeeAssigningsExistingNonEmployeeUser();
        getEmployeeAssigningsNonExistingUser();
    }

    //--------------------------------------------------------------------------------------------------

    void getTotalPointsExistingUser(){
        double result = managerService.getTotalPoints("admin");
        assertEquals(result, 100.0);
    }

    void getTotalPointsNonExistingUser(){
        try {
            managerService.getTotalPoints("phistuk");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }

    @Test
    void getTotalPoints() {
        getTotalPointsExistingUser();
        getTotalPointsNonExistingUser();
    }

    //--------------------------------------------------------------------------------------------------

    void getTotalRestrictionsExistingUser(){
        List<Integer> expected = Arrays.asList(0, 1, 2, 3, 4);

        Map<String, Restriction> result = managerService.getEmployeeRestrictions("admin");

        assertEquals(result.getOrDefault("shenhav", new Restriction()).get_allowed_days(), expected);
    }

    void getTotalRestrictionsNonExistingUser(){
        try {
            managerService.getEmployeeRestrictions("phistuk");
            throw new AssertionError();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Employee username doesn't exists");
        }
    }

    @Test
    void getEmployeeRestrictions() {
        getTotalRestrictionsExistingUser();
        getTotalRestrictionsNonExistingUser();
    }


}
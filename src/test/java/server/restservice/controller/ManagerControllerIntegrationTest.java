package server.restservice.controller;
import com.google.gson.Gson;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import server.restservice.TestConfig;
import server.restservice.models.*;
import server.restservice.repository.EmployeeRepository;
import server.restservice.security.model.JwtRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = { TestConfig.class })
//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ManagerControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    private String header;

    private Gson gson = new Gson();

    private String asJsonString(final Object obj) {
        try {
            return this.gson.toJson(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() throws Exception {

        JwtRequest requestBody = new JwtRequest("admin", "password");
        MvcResult result = mockMvc.perform(post("/authenticate").
                contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestBody))).andReturn();
        String resultStr = result.getResponse().getContentAsString();
        JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
        JSONObject json = (JSONObject) parser.parse(resultStr);
        String auth = (String) json.getOrDefault("token", "");
        this.header = "Bearer " + auth;
    }



    @Test
    public void test_getEmployees() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/managers/get_employees").
                header("Authorization", this.header)).
                andExpect(status().isOk()).
                andReturn();

        String employees = mvcResult.
                getResponse().
                getContentAsString();


        EmployeeDetails[] resultEmployees = this.gson.fromJson(employees, EmployeeDetails[].class);

        List<String> empLst  = this.employeeRepository.findEmployeeByUsername("admin").get_employees();
        EmployeeDetails[] expectedEmployees = new EmployeeDetails[empLst.size()];
        for (int i = 0; i < empLst.size(); i++) {
            Employee emp = this.employeeRepository.findEmployeeByUsername(empLst.get(i));
            if(emp != null)
                expectedEmployees[i] = new EmployeeDetails(emp.get_username(), emp.get_name());
                expectedEmployees[i].set_points(0);
        }


        assertArrayEquals(expectedEmployees, resultEmployees);
    }


    @Test
    public void test_getEmployeesPoints() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/managers/getEmployeesPoints").
                header("Authorization", this.header)).
                andExpect(status().isOk()).
                andReturn();

        String employees = mvcResult.
                getResponse().
                getContentAsString();


        EmployeeDetails[] resultEmployees = this.gson.fromJson(employees, EmployeeDetails[].class);

        List<String> empLst  = this.employeeRepository.findEmployeeByUsername("admin").get_employees();
        EmployeeDetails[] expectedEmployees = new EmployeeDetails[empLst.size()];
        for (int i = 0; i < empLst.size(); i++) {
            Employee emp = this.employeeRepository.findEmployeeByUsername(empLst.get(i));
            if(emp != null)
                expectedEmployees[i] = new EmployeeDetails(emp);
        }


        assertArrayEquals(expectedEmployees, resultEmployees);


    }

    @Test
    public void test_getTotalPoints() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/managers/get_total_points").
                header("Authorization", this.header)).
                andExpect(status().isOk()).
                andReturn();
        int points = Integer.parseInt( mvcResult.
                getResponse().
                getContentAsString());

        assertEquals(300, points);

    }




    @Test
    public void test_getEmployeesRestrictions() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/managers/getEmployeesRestrictions").
                header("Authorization", this.header)).
                andExpect(status().isOk()).
                andReturn();

        String employees = mvcResult.
                getResponse().
                getContentAsString();


        EmployeeDetails[] resultEmployees = this.gson.fromJson(employees, EmployeeDetails[].class);

        List<String> empLst  = this.employeeRepository.findEmployeeByUsername("admin").get_employees();
        EmployeeDetails[] expectedEmployees = new EmployeeDetails[empLst.size()];
        for (int i = 0; i < empLst.size(); i++) {
            Employee emp = this.employeeRepository.findEmployeeByUsername(empLst.get(i));
            if(emp != null)
                expectedEmployees[i] = new EmployeeDetails(emp);
        }


        assertArrayEquals(expectedEmployees, resultEmployees);
    }


    @Test
    public void test_setRestrictionsExistingUser() throws Exception {

        Restriction expectedRst = new Restriction();
        expectedRst.set_allowed_days(Arrays.asList(2, 3, 4));

        mockMvc.perform(post("/managers/set_restrictions").
                header("Authorization", this.header).
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(expectedRst)).
                param("employee_username", "shenhav")).
                andExpect(status().isOk()).
                andReturn();



        Restriction resultRst = this.employeeRepository.findEmployeeByUsername("shenhav").get_restriction();


        assertEquals(expectedRst.get_allowed_days(), resultRst.get_allowed_days());

    }


    @Test
    public void test_setRestrictionsExistingNonEmpUser() throws Exception {

        Restriction expectedRst = new Restriction();
        expectedRst.set_allowed_days(Arrays.asList(2, 3, 4));

        mockMvc.perform(post("/managers/set_restrictions").
                header("Authorization", this.header).
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(expectedRst)).
                param("employee_username", "toya")).

                andExpect(status().isPreconditionFailed()).
                andExpect(result -> assertEquals("Can't update employee", result.getResolvedException().
                        getMessage()));

    }

    @Test
    public void test_setRestrictionsNonExistingUser() throws Exception {

        Restriction expectedRst = new Restriction();
        expectedRst.set_allowed_days(Arrays.asList(2, 3, 4));

        mockMvc.perform(post("/managers/set_restrictions").
                header("Authorization", this.header).
                contentType(MediaType.APPLICATION_JSON).
                param("employee_username", "phistuk").
                content(asJsonString(expectedRst))).
                andExpect(status().isPreconditionFailed()).
                andExpect(result -> assertEquals("Employee username doesn't exists", result.getResolvedException().
                        getMessage()));

    }

    @Test
    public void test_getEmployeeAssigningsExistingUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/managers/getEmployeeAssigning").
                header("Authorization", this.header).param("employeename", "shauli")).
                andExpect(status().isOk()).
                andReturn();

        String assigning = mvcResult.
                getResponse().
                getContentAsString();


        Assignings resultAss = this.gson.fromJson(assigning, Assignings.class);
        Assignings expectedAss = this.employeeRepository.findEmployeeByUsername("shauli").get_assignings();

        assertEquals(expectedAss.getAssignedDays(), resultAss.getAssignedDays());
    }

    @Test
    public void test_getEmployeeAssigningsExistingNonEmpUser() throws Exception {
        mockMvc.perform(get("/managers/getEmployeeAssigning").
        header("Authorization", this.header).param("employeename", "toya")).
        andExpect(status().isPreconditionFailed()).
        andExpect(result -> assertEquals("Can't access employee", result.getResolvedException().
        getMessage()));

    }

    @Test
    public void test_getEmployeeAssigningsNonExistingUser() throws Exception {
        mockMvc.perform(get("/managers/getEmployeeAssigning").
                header("Authorization", this.header).param("employeename", "phistuk")).
                andExpect(status().isPreconditionFailed()).
                andExpect(result -> assertEquals("Employee username doesn't exists", result.getResolvedException().
                        getMessage()));
    }


}

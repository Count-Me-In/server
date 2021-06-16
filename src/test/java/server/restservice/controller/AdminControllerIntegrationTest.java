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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import server.restservice.TestConfig;
import org.springframework.test.web.servlet.MvcResult;
import server.restservice.models.*;
import server.restservice.repository.EmployeeRepository;
import server.restservice.security.model.JwtRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@SpringBootTest(classes = { TestConfig.class }, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;


    private String header;

    private Gson gson = new Gson();

    private String asJsonString(final Object obj) {
        try {
            //return new ObjectMapper().writeValueAsString(obj);
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
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(resultStr);
        String auth = (String)json.getOrDefault("token", "");
        this.header = "Bearer " + auth;
    }


    @Test
    public void test_getEmployees() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/admin/getEmployees").
                header("Authorization", this.header)).
                andReturn();

        String employees = mvcResult.
                getResponse().
                getContentAsString();

        EmployeeDetails[] resultEmployees = this.gson.fromJson(employees, EmployeeDetails[].class);

        Employee[] empLst  = this.employeeRepository.getAllEmployeeNames();
        EmployeeDetails[] expectedEmployees = new EmployeeDetails[empLst.length];

        for (int i = 0; i < empLst.length; i++) {
            expectedEmployees[i] = new EmployeeDetails(empLst[i]);
        }

        assertArrayEquals(expectedEmployees, resultEmployees);
    }

    @Test
    public void test_addEmployee() throws Exception {

        HashMap<String, String> empMap = new HashMap();
        empMap.put("name", "John");
        empMap.put("username", "John@gmail.com");
        empMap.put("manager", "nufar");
        empMap.put("password", "password");

        MvcResult mvcResult = mockMvc.perform(get("/admin/addEmployee").
                contentType(MediaType.APPLICATION_JSON).
                header("Authorization", this.header).
                content(asJsonString(empMap))).
                andExpect(status().isOk()).
                andReturn();

        assertNotNull(employeeRepository.findEmployeeByUsername("John@gmail.com"));

        employeeRepository.deleteEmployee("John@gmail.com");
    }


    @Test
    public void test_deleteEmployee() throws Exception {


        Employee emp = new Employee(UUID.randomUUID(), "John@gmail.com", "John", "nufar", 0, 0, 0);
        employeeRepository.addEmployee(emp, "password");

        MvcResult mvcResult = mockMvc.perform(get("/admin/deleteEmployee").
                header("Authorization", this.header).
                param("username", "John@gmail.com")).
                andExpect(status().isOk()).
                andReturn();

        assertNull(employeeRepository.findEmployeeByUsername("John@gmail.com"));

    }

    @Test
    public void test_getDays() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/admin/getDays").
                header("Authorization", this.header)).
                andReturn();

        String days = mvcResult.
                getResponse().
                getContentAsString();

        Integer[] resultDays = this.gson.fromJson(days, Integer[].class);
        Integer[] expectedDays = {10, 20, 30, 20, 10};


        assertArrayEquals(expectedDays, resultDays);
    }

    @Test
    public void test_editDays() throws Exception {

        Integer[] expectedDays = {10, 10, 10, 10, 10};

        HashMap<String, Integer[]> map = new HashMap<>();
        map.put("days", expectedDays);

        MvcResult mvcResult = mockMvc.perform(get("/admin/editDays").
                contentType(MediaType.APPLICATION_JSON).
                header("Authorization", this.header).
                content(asJsonString(expectedDays))).
                andReturn();


        Integer[] resultDays = employeeRepository.getDays();

        assertArrayEquals(expectedDays, resultDays);

        Integer[] prevDays = {10, 20, 30, 20, 10};
        employeeRepository.editDays(prevDays);

    }
}

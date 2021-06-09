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


@SpringBootTest(classes = { TestConfig.class }, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTest {

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
    public void test_all() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employees/all").
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
    public void test_bids_collection() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employees/bids_collection").
                header("Authorization", this.header)).
                andExpect(status().isOk()).
                andReturn();

        String bids = mvcResult.
                getResponse().
                getContentAsString();

        Bid[] resultBids = this.gson.fromJson(bids, Bid[].class);
        Bid[] expectedBids = this.employeeRepository.findEmployeeByUsername("admin").get_bids();

        assertArrayEquals(expectedBids, resultBids);
    }



    @Test
    public void test_updateBids_success() throws Exception {

        Bid[] expectedBids = this.employeeRepository.findEmployeeByUsername("admin").get_bids();
        expectedBids[0].set_percentage(10);
        expectedBids[0].set_percentage(20);
        expectedBids[0].set_percentage(30);
        expectedBids[0].set_percentage(40);
        expectedBids[0].set_percentage(0);

        MvcResult mvcResult = mockMvc.perform(put("/employees/updateBids").
                header("Authorization", this.header).
                content(asJsonString(expectedBids))).
                andExpect(status().isOk()).
                andReturn();

        Bid[] resultBids = this.employeeRepository.findEmployeeByUsername("admin").get_bids();

        assertArrayEquals(expectedBids, resultBids);
    }

    @Test
    public void test_updateBidsShortArray() throws Exception {

        String username = "admin";
        Bid[] newBids = { new Bid(username, 1), new Bid(username, 2), new Bid(username, 3), new Bid(username, 4)};

        MvcResult mvcResult = mockMvc.perform(put("/employees/updateBids").
                header("Authorization", this.header).
                content(asJsonString(newBids))).
                andExpect(status().isOk()).
                andReturn();


        assertEquals("Bids aren't valid", mvcResult.getResponse().getErrorMessage());
    }


    @Test
    public void test_updateBids_LimitRestriction() throws Exception {

        String username = "noy";
        Bid[] newBids = { new Bid(username, 1), new Bid(username, 2), new Bid(username, 3), new Bid(username, 4)};

        MvcResult mvcResult = mockMvc.perform(put("/employees/updateBids").
                header("Authorization", this.header).
                content(asJsonString(newBids))).
                andExpect(status().isOk()).
                andReturn();


        assertEquals("Bids aren't valid", mvcResult.getResponse().getErrorMessage());
    }

    @Test
    public void test_updateBids_sum100() throws Exception {

        String username = "noy";
        Bid[] newBids = { new Bid(username, 1), new Bid(username, 2), new Bid(username, 3), new Bid(username, 4), new Bid(username, 5)};
        newBids[0].set_percentage(70);
        newBids[1].set_percentage(10);
        newBids[2].set_percentage(10);
        newBids[3].set_percentage(10);
        newBids[4].set_percentage(10);

        MvcResult mvcResult = mockMvc.perform(put("/employees/updateBids").
                header("Authorization", this.header).
                content(asJsonString(newBids))).
                andExpect(status().isOk()).
                andReturn();


        assertEquals("Bids aren't valid", mvcResult.getResponse().getErrorMessage());
    }




    @Test
    public void test_employeePoints() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employees/employeePoints").
                header("Authorization", this.header)).
                andExpect(status().isOk()).
                andReturn();
        int points = Integer.parseInt( mvcResult.
                getResponse().
                getContentAsString());

        assertEquals(100, points);

    }


    @Test
    public void test_getEmployeesRestriction() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employees/getEmployeesRestriction").
                header("Authorization", this.header)).
                andExpect(status().isOk()).
                andReturn();

        String restStr = mvcResult.
                getResponse().
                getContentAsString();

        Restriction resultRest = this.gson.fromJson(restStr, Restriction.class);
        Restriction expectedRest = this.employeeRepository.findEmployeeByUsername("admin").get_restrictions();

        assertEquals(expectedRest.get_allowed_days(), resultRest.get_allowed_days());

    }


    @Test
    public void test_getEmployeeAssigning() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employees/getEmployeeAssigning").
                header("Authorization", this.header)).
                andExpect(status().isOk()).
                andReturn();

        String AssStr = mvcResult.
                getResponse().
                getContentAsString();

        Gson g = new Gson();

        Assignings resultAss = g.fromJson(AssStr, Assignings.class);
//        Assignings resultAss = this.objectMapper.readValue(AssStr, Assignings.class);
        Assignings expectedAss = this.employeeRepository.findEmployeeByUsername("admin").get_assignings();

        assertEquals(resultAss.getAssignedDays(), expectedAss.getAssignedDays());

    }
}

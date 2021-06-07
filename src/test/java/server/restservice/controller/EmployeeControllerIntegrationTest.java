package server.restservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import server.restservice.EmployeeRepositoryMock;
import server.restservice.repository.EmployeeRepository;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTest {


    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public EmployeeRepository employeeRepository() {
            return new EmployeeRepositoryMock();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        System.out.print("SHalog");
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200() throws Exception {
        // MvcResult mvcResult =
        mockMvc.perform(get("/employee/getBids")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].day").value(1)).andExpect(jsonPath("$[0].percentage").value(0));
        // .andReturn();
        // assertEquals("application/json;charset=UTF-8",
        // mvcResult.getResponse().getContentType());
    }
}

package server.restservice;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = { TestConfig.class }, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class RestserviceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    
    @Test
    public void login() throws Exception {
        mockMvc.perform(post("/authenticate").contentType(APPLICATION_JSON).content("{\"username\": \"shauli\",\"password\": \"password\"}")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void contextLoads() {
    }

}
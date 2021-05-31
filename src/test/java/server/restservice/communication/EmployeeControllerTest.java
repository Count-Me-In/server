package server.restservice.communication;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private EmployeeService service;// = new EmployeeService();
//
//    @Test
//    void getEmployees() throws Exception {
//        String[] emp = {"Nufar Carmel", "Shenhav Carmel", "Noych Ezra"};
//        RequestBuilder requset = MockMvcRequestBuilders.get("/employees");
//        System.out.println(mvc.perform(requset));
////        given()
//        mvc.perform(requset)
//                .andExpect(jsonPath("$", Matchers.hasSize(1)))
//                .andExpect(jsonPath("$[0]", Is.is("Nufar Carmel")));
//    }
}
package server.restservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import server.restservice.repository.EmployeeRepository;

@TestConfiguration
public class TestConfig {

    @Bean
    public EmployeeRepository employeeRepository() {
        return new EmployeeRepositoryMock();
    }
}
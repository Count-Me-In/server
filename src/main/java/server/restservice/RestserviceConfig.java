package server.restservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import server.restservice.repository.EmployeeRepository;
import server.restservice.repository.EmployeeRepositoryImpl;

@Configuration
public class RestserviceConfig {

    @Bean
    public EmployeeRepository getEmployeeRepository() {
        return new EmployeeRepositoryImpl();
    }
}
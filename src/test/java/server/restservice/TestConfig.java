package server.restservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import server.restservice.repository.EngineAPI.api.engineAPIInterface;
import server.restservice.repository.EngineAPI.api.apiMock;

@TestConfiguration
public class TestConfig {

    @Bean
    public engineAPIInterface getTestEngineAPI() {
        return new apiMock();
    }
}
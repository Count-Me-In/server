package server.restservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import server.restservice.repository.EngineAPI.api.apiMock;
import server.restservice.repository.EngineAPI.api.engineAPIInterface;
import server.restservice.repository.EngineAPI.api.engineRestApiClient;

@Configuration
public class RestserviceConfig {

    @Bean
    public engineAPIInterface getengineAPIInterface() {
        return new apiMock();
        // return new engineRestApiClient();
    }
}
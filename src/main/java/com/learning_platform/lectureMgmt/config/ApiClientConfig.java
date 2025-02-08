package com.learning_platform.lectureMgmt.config;


import com.learning_platform.lectureMgmt.Dtos.ServiceConfigDto;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
@ConfigurationProperties(prefix = "api")
@Getter
@Setter
public class ApiClientConfig {
    @Autowired
    WebClient webClient;



    private Map<String, ServiceConfigDto> services = new LinkedHashMap<>();

    public <T> Mono<T> callService(String serviceName,
                                    String endpointToCall,
                                    Map<String,String> params,
                                    Object payload,
                                    String token,
                                    Class<T> responseType){
        try{

        ServiceConfigDto configDto = services.get(serviceName);
        ServiceConfigDto.EndpointConfig endpointConfig = configDto.getEndpoints().get(endpointToCall);
        HttpMethod method = endpointConfig.getMethodType();

        String endpoint = endpointConfig.getEndPointPath();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            endpoint = endpoint.replace("{" + entry.getKey() + "}", entry.getValue());
        }


        WebClient.RequestBodySpec requestBodySpec = webClient
                .method(method)
                .uri(configDto.getBaseUrl() + endpoint )
                .headers(headers -> headers.setBearerAuth(token));

        if(payload != null){
            requestBodySpec.bodyValue(payload);
        }
        return requestBodySpec.retrieve().bodyToMono(responseType);


        }catch (RuntimeException e){
            e.printStackTrace();
            throw  new RuntimeException("ERROR IN CREATING THE CONFIG FOR API CALL" );
        }

    }

}

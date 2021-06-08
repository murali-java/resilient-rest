package com.resilientrest.resilientrest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BackendService {

    RestTemplate restTemplate = new RestTemplate();

    public String hitBackendService(){
        ResponseEntity<String> resp =
                restTemplate.exchange("http://localhost:9090/core/hitApi", HttpMethod.GET,
                        new HttpEntity<>(null), String.class);
        return resp.getBody();
    }
}

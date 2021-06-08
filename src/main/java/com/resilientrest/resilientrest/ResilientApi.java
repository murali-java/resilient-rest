package com.resilientrest.resilientrest;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
public class ResilientApi {

    @Autowired
    private CircuitBreaker apiCircuitBreaker;

    @Autowired
    private BackendService backendService;

    @GetMapping("resilientApi")
    public String hitResilientApi(){
        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(apiCircuitBreaker, backendService::hitBackendService);
        String result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery").get();
        System.out.println("Circuit Breaker Status: "+apiCircuitBreaker.getState());
        return result;
    }
}

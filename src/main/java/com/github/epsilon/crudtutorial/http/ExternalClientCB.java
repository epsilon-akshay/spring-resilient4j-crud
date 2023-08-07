package com.github.epsilon.crudtutorial.http;

import com.github.epsilon.crudtutorial.exception.BadServerException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalClientCB {
    private final RestTemplate restTemplate2;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    public ExternalClientCB(RestTemplate restTemplate, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.restTemplate2 = restTemplate;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }


    public String callExternalApiFoo(int aggregator) {
        String circuitBreakerName = String.format("externalServiceFoo%d", aggregator);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(circuitBreakerName, circuitBreakerName);
        return circuitBreaker.executeSupplier(() -> restTemplate2.getForObject(String.format("http://localhost:9090?id=%d",aggregator), String.class));
    }

    public String fallback(CallNotPermittedException e)  throws BadServerException {
        System.out.println("hihii");
        //update redis state
        throw new BadServerException("bad service");
    }
}

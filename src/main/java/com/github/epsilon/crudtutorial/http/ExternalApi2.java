package com.github.epsilon.crudtutorial.http;


import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalApi2 {
    private final RestTemplate restTemplate2;

    public ExternalApi2(RestTemplate restTemplate2) {
        this.restTemplate2 = restTemplate2;
    }


    @CircuitBreaker(name = "externalServiceFoo2", fallbackMethod = "fallback")
    public String callExternalApiFoo() {
        return restTemplate2.getForObject("http://localhost:9090", String.class);
    }

    public String fallback(CallNotPermittedException e) {
        System.out.println("hihii");
        return "hi";
    }
}


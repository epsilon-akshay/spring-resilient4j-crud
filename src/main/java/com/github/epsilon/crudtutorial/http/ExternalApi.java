package com.github.epsilon.crudtutorial.http;

import com.github.epsilon.crudtutorial.exception.BadServerException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalApi {
    private final RestTemplate restTemplate2;

    public ExternalApi(RestTemplate restTemplate) {
        this.restTemplate2 = restTemplate;
    }


    @CircuitBreaker(name = "externalServiceFoo", fallbackMethod = "fallback")
    public String callExternalApiFoo(int id) {
        return restTemplate2.getForObject("http://localhost:9090", String.class);
    }

    public String fallback(CallNotPermittedException e)  throws BadServerException {
        System.out.println("hihii");
        //update redis state
        throw new BadServerException("bad service");
    }
}


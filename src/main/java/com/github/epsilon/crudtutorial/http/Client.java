package com.github.epsilon.crudtutorial.http;

import com.github.epsilon.crudtutorial.exception.BadServerException;
import com.github.epsilon.crudtutorial.model.JSONRes;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestTemplate;


import java.net.URISyntaxException;


public class Client {
    private final RestTemplate restTemplate;

    private final int id;

    @Autowired
    public Client(RestTemplate restTemplate, int id) {
        this.restTemplate = restTemplate;
        this.id = id;
    }

    public <T> T get(String url) throws URISyntaxException {
        System.out.println("incoming request for client 1");
        String va = String.format("http://localhost:9090/external-foo/%d", id);
        JSONRes res = restTemplate.getForEntity("http://localhost:9090/" , JSONRes.class).getBody();
        System.out.println(res);
        T res1 = (T) res;
        return res1;
    }

    @CircuitBreaker(name = "client1", fallbackMethod = "fallback")
    public int getCode() throws URISyntaxException, BadServerException {
        try {
            System.out.println("incoming request for client 1");
            String va = String.format("http://localhost:9090/external-foo/%d", id);
            int res = restTemplate.getForEntity("http://localhost:9090/", JSONRes.class).getStatusCode().value();
            System.out.println("asdasdasd");
            System.out.println(res);
            return res;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            throw new BadServerException("asd");
        }
    }

    public int fallback(Exception ex) {
        System.out.println(ex.getMessage());
        return 200;
    }



}

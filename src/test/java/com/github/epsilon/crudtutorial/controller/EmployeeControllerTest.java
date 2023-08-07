package com.github.epsilon.crudtutorial.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EmployeeControllerTest {
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testFoo() throws Exception {

        for (int i = 0; i < 5; i++) {
            int response = restTemplate.getForEntity("http://127.0.0.1:8080/api/v1/foo?id=1", String.class).getStatusCode().value();
            assertThat(response).isEqualTo(500);
        }
        for (int i = 0; i < 5; i++) {
            int response = restTemplate.getForEntity("http://127.0.0.1:8080/api/v1/foo?id=1", String.class).getStatusCode().value();
            assertThat(response).isEqualTo(200);
        }
    }
}
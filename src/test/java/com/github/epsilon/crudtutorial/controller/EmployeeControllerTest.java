package com.github.epsilon.crudtutorial.controller;

import com.github.epsilon.crudtutorial.exception.BadServerException;
import com.github.epsilon.crudtutorial.http.ExternalApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmployeeControllerTest {
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private EmployeeController controller;
    @Mock
    private ExternalApi externalApi;

//    @Test
//    public void testFoo() throws Exception {
//
//        for (int i = 0; i < 5; i++) {
//            int response = restTemplate.getForEntity("http://127.0.0.1:8080/api/v1/foo?id=1", String.class).getStatusCode().value();
//            assertThat(response).isEqualTo(500);
//        }
//        for (int i = 0; i < 5; i++) {
//            int response = restTemplate.getForEntity("http://127.0.0.1:8080/api/v1/foo?id=1", String.class).getStatusCode().value();
//            assertThat(response).isEqualTo(200);
//        }
//    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
         controller = new EmployeeController(null,null,null,externalApi, null,null);
    }

    @Test
    public void testFooControllerReturnsException() throws BadServerException {
        when(externalApi.callExternalApiFoo(1)).thenReturn("hi");
        String res = controller.foo(1, 31);
        assertEquals("hi", res);
    }
}
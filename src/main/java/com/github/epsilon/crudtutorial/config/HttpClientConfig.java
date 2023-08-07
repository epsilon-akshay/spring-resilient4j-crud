package com.github.epsilon.crudtutorial.config;

import com.github.epsilon.crudtutorial.http.Client;
import com.github.epsilon.crudtutorial.http.Client2;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.COUNT_BASED;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Configuration
public class HttpClientConfig {
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder
//                .setConnectTimeout(Duration.ofMillis(3000))
//                .setReadTimeout(Duration.ofMillis(3000))
//                .build();
//    }

    //    @Bean
//    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {}
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//
//        return builder.build();
//    }

    @Bean
    public Client clientID1(RestTemplate restTemplate) {
        return new Client(restTemplate, 1);
    }

    @Bean
    public Client2 clientID2(RestTemplate restTemplate) {
        return new Client2(restTemplate, 3);
    }

    @Bean
    public CircuitBreakerConfigCustomizer externalServiceClient1() {
        return CircuitBreakerConfigCustomizer
                .of("client1",
                        builder -> builder.slidingWindowSize(10)
                                .slidingWindowType(COUNT_BASED)
                                .waitDurationInOpenState(Duration.ofSeconds(20))
                                .minimumNumberOfCalls(5)
                                .failureRateThreshold(10.0f));
    }
    @Bean
    public CircuitBreakerConfigCustomizer externalServiceClient2() {
        return CircuitBreakerConfigCustomizer
                .of("client2",
                        builder -> builder.slidingWindowSize(5)
                                .slidingWindowType(COUNT_BASED)
                                .minimumNumberOfCalls(1)
                                .failureRateThreshold(1.0f));
    }

    @Bean
    public RestTemplate restTemplate2() {
        return new RestTemplateBuilder().build();
    }

    @Bean
    public CircuitBreakerConfigCustomizer externalServiceFooCircuitBreakerConfig() {
        return CircuitBreakerConfigCustomizer
                .of("externalServiceFoo",
                        builder -> builder.slidingWindowSize(10)
                                .slidingWindowType(COUNT_BASED)
                                .waitDurationInOpenState(Duration.ofSeconds(20))
                                .minimumNumberOfCalls(5)
                                .failureRateThreshold(50.0f));
    }

    @Bean
    public CircuitBreakerConfigCustomizer externalServiceFooCircuitBreakerConfig2() {
        return CircuitBreakerConfigCustomizer
                .of("externalServiceFoo2",
                        builder -> builder.slidingWindowSize(10)
                                .slidingWindowType(COUNT_BASED)
                                .waitDurationInOpenState(Duration.ofSeconds(20))
                                .minimumNumberOfCalls(5)
                                .failureRateThreshold(50.0f));
    }
}

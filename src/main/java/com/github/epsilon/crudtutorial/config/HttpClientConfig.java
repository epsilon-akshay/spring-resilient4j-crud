package com.github.epsilon.crudtutorial.config;

import com.github.epsilon.crudtutorial.http.Client;
import com.github.epsilon.crudtutorial.http.Client2;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
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

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(@Value("${client.apiList}") String[] apiCurls) {
        CircuitBreakerConfig externalServiceFooConfig = CircuitBreakerConfig.custom()
                .slidingWindowSize(10)
                .slidingWindowType(COUNT_BASED)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .minimumNumberOfCalls(5)
                .failureRateThreshold(50.0f)
                .build();
        System.out.println("lololo");
        System.out.println(apiCurls.length);
        System.out.println(apiCurls[0]);
        Map<String, CircuitBreakerConfig> map = new HashMap();

        for(var i:apiCurls) {
            System.out.println(i);
            map.put(i,externalServiceFooConfig);
        }
        return CircuitBreakerRegistry.of(
                map
        );
    }
}

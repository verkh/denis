package org.example.spring.beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.UUID;

@SpringBootApplication
public class BeanScopesExample {

    /**
     *
     * Уникальные и постоянные
     * Уникальные, но непостоянные
     * неуникальные и постоянные
     *
     * SCOPES:
     * - Singleton   - объект создается только 1 раз на весь контейнер.
     * - Prototype   - создает прототип объекта, который на каждый autowiring новый
     * - Request     - похож на прототип, но создает объект на время выполнения веб запроса (например GET /api/users)
     * - Session     - то же самое, только бин живет все времяя, пока открыта сессия браузера-бекенда
     * - Websocket   - он тоже похож на Request/Session, но только в рамках открытого сокета.
     * - Application - то же самое, что и Singleton, но есть момент. Singleton across multiple containers.
     */

    @Autowired
    @Qualifier("Default")
    private SingletonService singletonService;

    @Autowired
    @Qualifier("SingletonServiceWithConfigurations")
    private SingletonService singletonService2;

    @Getter
    public static class RequestName {
        private final String name = UUID.randomUUID().toString();
    }
    public static class Statistics {
        private int callNumber = 0;

        void incrementCallNumber() {
            callNumber++;
        }
    }

    @Service
    public static class AnotherService {
        @Autowired
        private Statistics statistics;

        @PostConstruct
        public void print() {
            System.out.println("Statistics for the AnotherService: " + statistics);
        }
    }

    @Service
    @Qualifier("Default")
    public static class SingletonService {
        @Autowired
        private Statistics statistics;

//        @Resource(name = "requestName")
        private RequestName requestName;

        public SingletonService() {
            System.out.println("Constructing SingletonService without arguments");
        }

        public SingletonService(String str) {
            System.out.println("Constructing SingletonService with argument: " + str);
        }

        @PostConstruct
        public void print() {
            statistics.incrementCallNumber();
            System.out.println("Statistics for the bean: " + statistics);
            System.out.println("Request name: " + requestName);
        }
    }

    @Configuration
    public static class Config {
        @Bean
        @Qualifier("SingletonServiceWithConfigurations")
        @Scope("singleton")
        public SingletonService singletonServiceWithMyExtraConfigurations() {
            return new SingletonService("asdasd");
        }

        @Bean
        @Scope("prototype")
        public Statistics statistics() {
            System.out.println("Creating statistics class for SingletonService");
            return new Statistics();
        }

        @Bean
        @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
        public RequestName requestName() {
            System.out.println("Creating request class for SingletonService");
            return new RequestName();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BeanScopesExample.class, args);
    }
}

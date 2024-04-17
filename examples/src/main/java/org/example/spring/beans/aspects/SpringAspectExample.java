package org.example.spring.beans.aspects;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.spring.beans.BeanScopesExample;
import org.example.spring.beans.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

@SpringBootApplication(scanBasePackages =  "org.example.spring.beans.aspects")
@EnableAspectJAutoProxy
public class SpringAspectExample {

    @Service
    @RequiredArgsConstructor
    public static class Container {
        private final ProfiledClass profiledClass;

        @PostConstruct
        void init() throws InterruptedException {
            profiledClass.wrapperDoSomething("My Message");
        }
    }

    @Service
    public static class ProfiledClass {
        @Profiler
        public void doSomething(String message, int x) throws InterruptedException {
            System.out.println(message + x);
            Thread.sleep(1000);
        }

        @Profiler
        public void wrapperDoSomething(String message) throws InterruptedException {
            doSomething(message, 10);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BeanScopesExample.class, args);
    }
}

package org.example.spring.beans.logging;

import jakarta.annotation.PostConstruct;
import org.example.spring.beans.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LoggingExample {
    private Logger logger = LoggerFactory.getLogger(LoggingExample.class);
    private List<String> stringTasks = new ArrayList<>();

    @PostConstruct
    void init() {
        simpleOldSchoolLogExample();
    }

    /*
        OUT - обычные вывод сообщений, когда все ок
        ERR - вывод сообщений, когда все не ок
     */
    void simpleOldSchoolLogExample() {
        System.out.println("[12.04.2024-12:00:00.12312Z] INFO: user Vandi@gmail.com not found");

        logger.info("User %s logged in".formatted("username"));
        logger.debug("all debug infogrmation which could help to fix something");
    }

    void simpleOldSchoolErrorLogExample() {
        System.err.println("ERROR: doing somethings");

        logger.error("User %s logged in".formatted("username"));

        // ...
        logger.warn("not critical, but it's needed to be checked");
        logger.debug("all debug infogrmation which could help to fix something");
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    void myScheduledFunction() {
        logger.debug("Running scheduled function. Checking strings...");

        if(!stringTasks.isEmpty()) {
            stringTasks.forEach(string -> logger.debug(string));
        } else {
            logger.debug("No strings were provided. Nothing to do....");
        }
    }

    // Шрединг Баг -> отсылка к коту Шредингера

    public static void main(String[] args) {
        SpringApplication.run(SpringBean.class, args);
    }
}

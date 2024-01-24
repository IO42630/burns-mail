package com.olexyn.burnsmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication

@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BurnsMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(BurnsMailApplication.class, args);
    }

}

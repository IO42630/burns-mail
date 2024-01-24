package com.olexyn.burnsmail;

import com.olexyn.burnsmail.mail.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication

@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BurnsMailApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context  = SpringApplication.run(BurnsMailApplication.class, args);

        Thread.sleep(10000L);
        // Retrieve the EmailService bean
        EmailService emailService = context.getBean(EmailService.class);

        // Call the readEmails method
        try {
            emailService.readEmails();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }

        // Close the application context
        context.close();

    }

}

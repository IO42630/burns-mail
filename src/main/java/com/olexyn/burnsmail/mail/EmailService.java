package com.olexyn.burnsmail.mail;

import org.springframework.stereotype.Service;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Properties;



@Service
public class EmailService {


    public void readEmails() throws MessagingException, IOException {
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(properties, null);
        Store store = session.getStore("imaps");
        store.connect(
            System.getenv("spring.mail.host"),
            System.getenv("spring.mail.username"),
            System.getenv("spring.mail.password")
        );

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        // Fetch unread messages
        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

        for (Message message : messages) {
            // Process each email message
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Body: " + message.getContent());
        }

        // Close resources
        inbox.close(false);
        store.close();
    }
}
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


    private Store connect() throws MessagingException {
        var properties = new Properties();
        properties.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(properties, null);
        Store store = session.getStore("imaps");
        store.connect(
            System.getenv("spring.mail.host"),
            System.getenv("spring.mail.username"),
            System.getenv("spring.mail.password")
        );
        return store;
    }


    public void readEmails() throws MessagingException, IOException {
        Store store = connect();

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        // Fetch unread messages
        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

        for (Message message : messages) {
          //TODO: do something with the message
        }

        // Close resources
        inbox.close(false);
        store.close();
    }

    public void expunge() throws MessagingException {
        Store store = connect();
        Folder trash = store.getFolder("INBOX").getFolder("Trash");
        trash.open(Folder.READ_WRITE);
        for (Message message : trash.getMessages()) {
            message.setFlag(Flags.Flag.DELETED, true);
        }
        trash.close(true); // expunge
        store.close();
    }

}
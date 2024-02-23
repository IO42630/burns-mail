package com.olexyn.burnsmail.mail;

import com.olexyn.burnsmail.flow.Flow;
import com.olexyn.burnsmail.flow.action.CopyToDst;
import com.olexyn.burnsmail.flow.cleanup.FlushFolders;
import com.olexyn.burnsmail.flow.filter.AnyFromContains;
import com.olexyn.burnsmail.flow.map.FullToAMailMapper;
import com.olexyn.burnsmail.flow.map.LazyToAMailMapper;
import com.olexyn.burnsmail.flow.search.FetchAll;
import com.olexyn.burnsmail.flow.search.SearchAnySubject;
import com.olexyn.burnsmail.flow.transform.SetOpenAiScore;
import com.olexyn.burnsmail.model.AMail;
import com.olexyn.min.log.LogU;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



@Service
public class EmailService {

    private static final String INBOX = "INBOX";

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

    private void process(Flow flow) {
        try (Store store = connect()) {
            // SEARCH
            var messages = flow.getSearchFolder().search(store);
            // MAP TO AMail
            List<AMail> mappeds = new ArrayList<>();
            for (var message : messages) {
                mappeds.add(flow.getToAMailMapper().map(message));
            }
            // FILTER & TRANSFORM & ACTION
            mappeds.stream()
                .filter(flow.getFilterAMail()::doKeep)
                .map(m -> flow.getTransformAMail().transform(m))
                .forEach(m -> flow.getAction().execute(m, store));

        } catch (MessagingException | NullPointerException e) {
            LogU.warnPlain("Could not process." + e.getMessage());
        }
    }



    public void doSbbTriage() {
        var flow = new Flow(
            new SearchAnySubject(INBOX, "Your online purchase from SBB", "Your online purchase from SBB"),
            new LazyToAMailMapper(),
            new AnyFromContains("sbbclient@sbb.ch", "sbbclient@order.info.sbb.ch"),
            (AMail a) -> a,
            new CopyToDst(INBOX, "INBOX/TRIAGE/SBB"),
            new FlushFolders(INBOX)
        );
        process(flow);
    }

    public void classifyAsSpam() {
        var flow = new Flow(
            new FetchAll(INBOX),
            new FullToAMailMapper(),
            (AMail a) -> true,
            new SetOpenAiScore(),
            new CopyToDst(INBOX, "INBOX/TRIAGE/SPAM"),
            new FlushFolders(INBOX)
        );
        process(flow);
    }





}
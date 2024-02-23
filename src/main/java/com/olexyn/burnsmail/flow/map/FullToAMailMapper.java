package com.olexyn.burnsmail.flow.map;

import com.olexyn.burnsmail.model.AMail;
import com.olexyn.min.log.LogU;
import org.jsoup.Jsoup;

import javax.mail.Message;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FullToAMailMapper implements ToAMailMapper {

    @Override
    public AMail map(Message message) {
        var aMail = new LazyToAMailMapper().map(message);
        if (aMail == null) {
            return null;
        }
        var imapMessage = aMail.getImapMessage();
        try {
            var imapContent = imapMessage.getContent();
            String htmlContent = null;
            if (imapContent instanceof String str) {
                htmlContent = str;
            } else if (imapContent instanceof MimeMultipart multi) {
                var out = new ByteArrayOutputStream();
                multi.writeTo(out);
                htmlContent = out.toString(UTF_8);
            }
            htmlContent = Optional.ofNullable(htmlContent).orElse("");
            aMail.setContent(Jsoup.parse(htmlContent).text());
        } catch (Exception e) {
            LogU.warnPlain("Could not parse." + e.getMessage());
            return null;
        }
        return aMail;
    }
}

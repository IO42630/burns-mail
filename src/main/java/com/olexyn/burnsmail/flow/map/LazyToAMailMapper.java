package com.olexyn.burnsmail.flow.map;

import com.olexyn.burnsmail.model.AMail;
import com.sun.mail.imap.IMAPMessage;

import javax.mail.Message;

import static com.olexyn.burnsmail.MiscU.extractEmail;

public class LazyToAMailMapper implements ToAMailMapper {

    @Override
    public AMail map(Message message) {
        if (!(message instanceof IMAPMessage imapMessage)) {
            return null;
        }
        var aMail = new AMail();
        aMail.setImapMessage(imapMessage);
        try {
            if (imapMessage.getFrom() != null && imapMessage.getFrom().length > 0) {
                var rawFrom = imapMessage.getFrom()[0].toString();
                aMail.setFrom(extractEmail(rawFrom));
            }
            if (imapMessage.getAllRecipients() != null && imapMessage.getAllRecipients().length > 0) {
                var rawTo = imapMessage.getAllRecipients()[0].toString();
                aMail.setTo(extractEmail(rawTo));
            }
            aMail.setSubject(imapMessage.getSubject());

        } catch (Exception e) {
            return null;
        }
        return aMail;
    }
}

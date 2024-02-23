package com.olexyn.burnsmail.flow.action;

import com.olexyn.burnsmail.MiscU;
import com.olexyn.burnsmail.model.AMail;
import com.olexyn.min.log.LogU;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

public class CopyToDst implements Action {

    private final String src;
    private final String dst;
    private Folder srcF;
    private Folder dstF;

    public CopyToDst(String src, String dst) {
        this.src = src;
        this.dst = dst;
    }

    @Override
    public void execute(AMail aMail, Store store) {
        try {
            if (srcF == null) {
                srcF = MiscU.open(store, src);
            }
            if (dstF == null) {
                dstF = MiscU.open(store, dst);
            }
            srcF.copyMessages(new Message[]{aMail.getImapMessage()}, dstF);
            aMail.getImapMessage().setFlag(Flags.Flag.DELETED, true);
        } catch (MessagingException e) {
            LogU.warnPlain("Could not copy mail." + e.getMessage());
        }
    }
}

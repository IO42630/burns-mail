package com.olexyn.burnsmail.flow.search;

import com.olexyn.burnsmail.MiscU;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;

public class FetchAll implements SearchFolder {


    private final String src;
    private Folder srcF;

    public FetchAll(String src) {
        this.src = src;
    }

    @Override
    public Message[] search(Store store) {
        try {
            if (srcF == null) {
                srcF = MiscU.open(store, src);
            }
            return srcF.getMessages();
        } catch (Exception e) {
            return new Message[0];
        }

    }
}

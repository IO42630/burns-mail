package com.olexyn.burnsmail.model;

import com.sun.mail.imap.IMAPMessage;
import lombok.Data;

@Data
public class AMail {


    private String from;
    private String to;
    private String subject;
    private String content;
    private IMAPMessage imapMessage;
    private Double spamScore;

}

package com.olexyn.burnsmail.flow.search;

import javax.mail.Message;
import javax.mail.Store;

/**
 * The first step in the flow.
 */
public interface SearchFolder {


    Message[] search(Store store);
}


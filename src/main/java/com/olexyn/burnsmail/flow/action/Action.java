package com.olexyn.burnsmail.flow.action;

import com.olexyn.burnsmail.model.AMail;

import javax.mail.Store;

/**
 * The first step in the flow.
 */
public interface Action {


    void execute(AMail aMail, Store store);
}


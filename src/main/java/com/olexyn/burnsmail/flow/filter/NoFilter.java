package com.olexyn.burnsmail.flow.filter;

import com.olexyn.burnsmail.model.AMail;


public class NoFilter implements FilterAMail {





    @Override
    public boolean doKeep(AMail aMail) {
        return true;
    }
}

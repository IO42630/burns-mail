package com.olexyn.burnsmail.flow.filter;

import com.olexyn.burnsmail.model.AMail;

public interface FilterAMail {


    boolean doKeep(AMail aMail);


}


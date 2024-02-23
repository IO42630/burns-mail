package com.olexyn.burnsmail.flow.cleanup;

import javax.mail.Store;

public interface Cleanup {

    void cleanUp(Store store);
}

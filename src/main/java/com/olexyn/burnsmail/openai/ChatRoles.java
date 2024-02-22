package com.olexyn.burnsmail.openai;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatRoles {

    public static final String SPAM_CLASSIFIER = "You are a spam classifier. " +
        "On any input String you can give any score between 0.00 and 0.99, " +
        "where 0.99 means the input is definitely spam and 0.00 means it is definitely not spam. " +
        "Your response shall only ever be a String representation of a double. ";

}

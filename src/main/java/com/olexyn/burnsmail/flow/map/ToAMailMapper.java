package com.olexyn.burnsmail.flow.map;

import com.olexyn.burnsmail.model.AMail;
import org.jetbrains.annotations.Nullable;

import javax.mail.Message;

@FunctionalInterface
public interface ToAMailMapper {

    @Nullable
    AMail map(Message message);


}


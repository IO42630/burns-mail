package com.olexyn.burnsmail.flow.filter;

import com.olexyn.burnsmail.model.AMail;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AnySubjectContains implements FilterAMail {

    private final List<String> subjects;

    public AnySubjectContains(String... subjects) {
        this.subjects = List.of(subjects);
    }

    @Override
    public boolean doKeep(AMail aMail) {
        if (aMail != null && aMail.getSubject() != null) {
            return subjects.contains(aMail.getSubject());
        }
        return false;
    }

}

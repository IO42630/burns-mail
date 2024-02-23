package com.olexyn.burnsmail.flow.filter;

import com.olexyn.burnsmail.model.AMail;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AnyFromContains implements FilterAMail {

    private final List<String> froms;

    public AnyFromContains(String... froms) {
        this.froms = List.of(froms);
    }

    @Override
    public boolean doKeep(AMail aMail) {
        if (aMail != null && aMail.getSubject() != null) {
            return froms.contains(aMail.getFrom());
        }
        return false;
    }

}

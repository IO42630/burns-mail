package com.olexyn.burnsmail.flow.search;

import com.olexyn.burnsmail.MiscU;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import java.util.Arrays;

public class SearchAnySubject implements SearchFolder {

    private final String src;
    private Folder srcF;
    private final SearchTerm searchTerm;

    public SearchAnySubject(String src, String... subjects) {
        this.src = src;
        var subjectTerms = Arrays.stream(subjects)
            .map(SubjectTerm::new)
            .toArray(SubjectTerm[]::new);
        this.searchTerm = new OrTerm(subjectTerms);
    }

    @Override
    public Message[] search(Store store) {

        try {
            if (srcF == null) {
                srcF = MiscU.open(store, src);
            }
            return srcF.search(searchTerm);
        } catch (Exception e) {
            return new Message[0];
        }

    }
}

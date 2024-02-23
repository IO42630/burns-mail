package com.olexyn.burnsmail.flow.transform;

import com.olexyn.burnsmail.model.AMail;
import com.olexyn.burnsmail.openai.OpenAiAMailClassifier;

import static com.olexyn.burnsmail.model.ClassifyCategory.SPAM;

public class SetOpenAiScore implements TransformAMail {

    private final OpenAiAMailClassifier openAiAMailClassifier;

    public SetOpenAiScore(OpenAiAMailClassifier openAiAMailClassifier) {
        this.openAiAMailClassifier = openAiAMailClassifier;
    }



    @Override
    public AMail transform(AMail aMail) {
        aMail.setSpamScore(openAiAMailClassifier.classifyAMail(aMail, SPAM));
        return aMail;
    }
}

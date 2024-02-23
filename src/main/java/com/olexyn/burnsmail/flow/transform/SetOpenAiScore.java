package com.olexyn.burnsmail.flow.transform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.olexyn.burnsmail.model.AMail;
import com.olexyn.burnsmail.model.ClassifyCategory;
import com.olexyn.burnsmail.openai.RequestDispatcher;
import com.olexyn.min.log.LogU;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import static com.olexyn.burnsmail.MiscU.deserializeJson;
import static com.olexyn.burnsmail.model.ClassifyCategory.SPAM;
import static com.olexyn.burnsmail.openai.ChatModels.GPT_35_TURBO;
import static com.olexyn.burnsmail.openai.ChatRoles.SPAM_CLASSIFIER;

public class SetOpenAiScore implements TransformAMail {



    @Override
    public AMail transform(AMail aMail) {
        aMail.setSpamScore(classifyAMail(aMail, SPAM));
        return aMail;
    }



    public Double classifyAMail(AMail mail, ClassifyCategory category) {
        switch (category) {
            case SPAM:
                return classifyAsSpam(mail);
            default:
                return 0.0;
        }
    }

    private Double classifyAsSpam(AMail aMail) {
        String userContent = aMail.getContent();
        try {
            var reply = RequestDispatcher.dispatch(GPT_35_TURBO, SPAM_CLASSIFIER, userContent);
            var json = deserializeJson(reply);
            var choicesArray = (JSONArray) json.get("choices");
            var firstChoice = (JSONObject) choicesArray.get(0);
            var messageObject = (JSONObject) firstChoice.get("message");
            var contentValue = (String) messageObject.get("content");
            return Double.parseDouble(contentValue);
        } catch (JsonProcessingException | ParseException e) {
            LogU.warnPlain("Could not classify as spam." + e.getMessage());
            return 0.0;
        }

    }
}

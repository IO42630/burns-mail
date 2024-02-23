package com.olexyn.burnsmail;

import lombok.experimental.UtilityClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.mail.Folder.READ_WRITE;

@UtilityClass
public class MiscU {

    private static final String EMAIL_REGEX = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,6}\\b";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    public static String extractEmail(String rawFrom) {
        Matcher matcher = PATTERN.matcher(rawFrom);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }


    private static Folder expand(Store store, Folder folder, List<String> folderPath) throws MessagingException {
        if (folderPath.isEmpty()) {
            return folder;
        }
        if (folder == null) {
            folder = store.getFolder(folderPath.remove(0));
        } else {
            folder = folder.getFolder(folderPath.remove(0));
        }
        return expand(store, folder, folderPath);

    }

    public static Folder open(Store store, String folderPath) throws MessagingException {
        var arrList = new ArrayList<>(List.of(folderPath.split("/")));
        Folder targetFolder = expand(store, null, arrList);
        targetFolder.open(READ_WRITE);
        return targetFolder;
    }

    public static JSONObject deserializeJson(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(json);

    }
}

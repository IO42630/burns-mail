package com.olexyn.burnsmail.flow.cleanup;

import com.olexyn.burnsmail.MiscU;
import com.olexyn.min.log.LogU;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;
import java.util.HashMap;
import java.util.Map;

public class FlushFolders implements Cleanup {


    private Map<String, Folder> folders = new HashMap<>();

    public FlushFolders(String... folders) {
        for (String folder : folders) {
            this.folders.put(folder, null);
        }
    }


    @Override
    public void cleanUp(Store store) {
        for (var entry : folders.entrySet()) {
            String src = entry.getKey();
            Folder srcF = entry.getValue();
            try {
                if (srcF == null) {
                    srcF = MiscU.open(store, src);
                }
                srcF.exists();
                srcF.close(false);
            } catch (MessagingException e) {
                LogU.warnPlain("Could not flush folder " + src + " " + e.getMessage());
            }
        }

    }
}

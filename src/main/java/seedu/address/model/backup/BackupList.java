//@@author Limminghong
package seedu.address.model.backup;

import seedu.address.logic.parser.exceptions.ParseException;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a list of all the snapshots of backups.
 */
public class BackupList {
    public static final String MESSAGE_BACKUP_CONSTRAINTS = "There are no previous backups.";

    private List<String> fileNames = new ArrayList<>();

    public BackupList(File backupDir) throws IOException {
        for (File snapshots : backupDir.listFiles()) {
            String millis = snapshots.getName();
            millis = millis.substring(0, millis.length() - 4);
            String fileName = millisToDateAndTime(millis);
            fileNames.add(fileName);
        }
        if (fileNames.size() == 0) {
            throw new IOException(MESSAGE_BACKUP_CONSTRAINTS);
        } else {
            Collections.reverse(fileNames);
        }
    }

    public List<String> getFileNames() {
        return this.fileNames;
    }

    public String millisToDateAndTime(String millis) {
        long timestamp = Long.parseLong(millis);
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault()
        );
        return dateTime.format(DateTimeFormatter.ofPattern("d MMM uuuu HH:mm:ss"));
    }
}

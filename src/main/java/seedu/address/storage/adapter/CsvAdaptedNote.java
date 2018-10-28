package seedu.address.storage.adapter;

import seedu.address.model.note.Note;


/**
 * CSV-friendly version of Note.
 */
public class CsvAdaptedNote {

    private static final String COMMA_DELIM = ",";
    private static final String DOUBLE_QUOTE = "\"";

    private String moduleCode;
    private String title;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String location;
    private String noteText;

    public CsvAdaptedNote(Note note) {
        this.moduleCode = note.getModuleCode();
        this.title = note.getTitle();
        this.startDate = note.getStartDate();
        this.startTime = note.getStartTime();
        this.endDate = note.getEndDate();
        this.endTime = note.getEndTime();
        this.location = note.getLocation();
        this.noteText = adaptToCsv(note.getNoteText());
    }

    /**
     * Escapes special characters used for HTML codes and
     * replaces string reserved characters to their HTML counterparts.
     */
    private String adaptToCsv(String text) {
        return text
                .replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\t", "&emsp;&emsp;")
                .replaceAll("(\r\n|\n)", "<br />");
    }

    /**
     * Creates a CSV-friendly string of Note data.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (!moduleCode.isEmpty()) {
            sb.append(DOUBLE_QUOTE);
            sb.append(moduleCode + ": " + title);
            sb.append(DOUBLE_QUOTE);
        } else {
            sb.append(DOUBLE_QUOTE);
            sb.append(title);
            sb.append(DOUBLE_QUOTE);
        }
        sb.append(COMMA_DELIM);
        sb.append(startDate);
        sb.append(COMMA_DELIM);
        sb.append(startTime);
        sb.append(COMMA_DELIM);
        sb.append(endDate);
        sb.append(COMMA_DELIM);
        sb.append(endTime);
        sb.append(COMMA_DELIM);
        sb.append(DOUBLE_QUOTE);
        sb.append(noteText);
        sb.append(DOUBLE_QUOTE);
        sb.append(COMMA_DELIM);
        sb.append(DOUBLE_QUOTE);
        sb.append(location);
        sb.append(DOUBLE_QUOTE);

        return sb.toString();
    }
}

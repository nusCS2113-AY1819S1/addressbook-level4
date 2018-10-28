package seedu.address.testutil;

import seedu.address.model.note.Note;

/**
 * Creates dummy notes for testing.
 */
public class NoteBuilder {
    public static final String DEFAULT_MODULE_CODE = "CS2113";
    public static final String DEFAULT_TITLE = "My note";
    public static final String DEFAULT_START_DATE = "21/12/2113";
    public static final String DEFAULT_START_TIME = "10:00 AM";
    public static final String DEFAULT_END_DATE = "22/12/2113";
    public static final String DEFAULT_END_TIME = "10:00 AM";
    public static final String DEFAULT_LOCATION = "National University of Singapore";
    public static final String DEFAULT_NOTE_TEXT = "CS2113 Rocks!";

    private String moduleCode;
    private String title;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String location;
    private String noteText;

    /**
     * Empty constructor for NoteBuilder that initializes the
     * note with default values.
     */
    public NoteBuilder() {
        this.moduleCode = DEFAULT_MODULE_CODE;
        this.title = DEFAULT_TITLE;
        this.startDate = DEFAULT_START_DATE;
        this.startTime = DEFAULT_START_TIME;
        this.endDate = DEFAULT_END_DATE;
        this.endTime = DEFAULT_END_TIME;
        this.location = DEFAULT_LOCATION;
        this.noteText = DEFAULT_NOTE_TEXT;
    }

    /**
     * Initializes the note with given parameters.
     */
    public NoteBuilder(String moduleCode,
                       String title,
                       String startDate,
                       String startTime,
                       String endDate,
                       String endTime,
                       String location,
                       String noteText) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.location = location;
        this.noteText = noteText;
    }

    /**
     * Initializes the note with the data from {@code noteToCopy}.
     */
    public NoteBuilder(Note noteToCopy) {
        this.moduleCode = noteToCopy.getModuleCode();
        this.title = noteToCopy.getTitle();
        this.startDate = noteToCopy.getStartDate();
        this.startTime = noteToCopy.getStartTime();
        this.endDate = noteToCopy.getEndDate();
        this.endTime = noteToCopy.getEndTime();
        this.location = noteToCopy.getLocation();
        this.noteText = noteToCopy.getNoteText();
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNoteText() {
        return this.noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    /**
     * Creates a note from the data in the NoteBuilder object.
     *
     * @return Note object
     */
    public Note build() {
        return new Note(
                moduleCode,
                title,
                startDate,
                startTime,
                endDate,
                endTime,
                location,
                noteText
        );
    }
}

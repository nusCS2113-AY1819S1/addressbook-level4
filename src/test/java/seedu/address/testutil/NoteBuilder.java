package seedu.address.testutil;

import seedu.address.model.note.Note;

public class NoteBuilder {
    public static final String DEFAULT_MODULE_CODE = "CS2113";
    public static final String DEFAULT_DATE = "21/12/2113";
    public static final String DEFAULT_TEXT = "CS2113 Rocks!";

    private String moduleCode;
    private String date;
    private String noteText;


    public NoteBuilder() {
        moduleCode = DEFAULT_MODULE_CODE;
        date = DEFAULT_DATE;
        noteText = DEFAULT_TEXT;
    }

    /**
     * Initializes the NoteBuilder with the data of {@code noteToCopy}.
     */
    public NoteBuilder(Note noteToCopy) {
        moduleCode = noteToCopy.getModuleCode();
        date = noteToCopy.getDate();
        noteText = noteToCopy.getNoteText();
    }

    /**
     * Initializes the NoteBuilder with given parameters.
     */
    public NoteBuilder(String moduleCode, String date, String noteText) {
        this.moduleCode = moduleCode;
        this.date = date;
        this.noteText = noteText;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Note build() {
        return new Note(moduleCode, date, noteText);
    }

    public Note buildWithoutNoteText() {
        return new Note(moduleCode, date);
    }
}

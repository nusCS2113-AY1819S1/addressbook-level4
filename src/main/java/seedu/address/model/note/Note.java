package seedu.address.model.note;

/**
 * Represents a note in Trajectory.
 */
public class Note {

    private String moduleCode;
    private String date;
    private String noteText;

    public Note(String moduleCode, String date) {
        this.moduleCode = moduleCode;
        this.date = date;
        this.noteText = "";
    }

    public Note(String moduleCode, String date, String text) {
        this.moduleCode = moduleCode;
        this.date = date;
        this.noteText = text;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNoteText() {
        return this.noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
}

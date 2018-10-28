package seedu.address.model.note;

/**
 * Represents a note in Trajectory.
 */
public class Note {

    private String moduleCode;
    private String title;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String location;
    private String noteText;


    public Note(String moduleCode,
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
}

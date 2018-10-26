package seedu.address.storage.adapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.note.Note;

/**
 * JAXB-friendly adapted version of the Note.
 */
@XmlRootElement(name = "note")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlAdaptedNote {

    @XmlElement(name = "moduleCode", required = true, nillable = true)
    private String moduleCode;

    @XmlElement(name = "title", required = true, nillable = true)
    private String title;

    @XmlElement(name = "startDate", required = true, nillable = true)
    private String startDate;

    @XmlElement(name = "startTime", required = true, nillable = true)
    private String startTime;

    @XmlElement(name = "endDate", required = true, nillable = true)
    private String endDate;

    @XmlElement(name = "endTime", required = true, nillable = true)
    private String endTime;

    @XmlElement(name = "location", required = true, nillable = true)
    private String location;

    @XmlElement(name = "noteText", required = true, nillable = true)
    private String noteText;

    /**
     * Constructs an XmlAdaptedNote.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedNote() { }

    /**
     * Constructs an {@code XmlAdaptedNote} with the given note details
     */
    public XmlAdaptedNote(
            String moduleCode,
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
     * Converts a Note into an {@code XmlAdaptedNote} for JAXB use
     */
    public XmlAdaptedNote(Note note) {
        this.moduleCode = note.getModuleCode();
        this.title = note.getTitle();
        this.startDate = note.getStartDate();
        this.startTime = note.getStartTime();
        this.endDate = note.getEndDate();
        this.endTime = note.getEndTime();
        this.location = note.getLocation();
        this.noteText = note.getNoteText();
    }

    /**
     * Converts this XmlAdaptedNote into the model's Note object
     */
    public Note toModelType() {
        return new Note(
                moduleCode,
                title,
                startDate,
                startTime,
                endDate,
                endTime,
                location,
                noteText);
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public String getTitle() {
        return this.title;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public String getLocation() {
        return this.location;
    }

    public String getNoteText() {
        return this.noteText;
    }
}

package seedu.address.storage.adapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.module.ModuleCode;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteDate;
import seedu.address.model.note.NoteLocation;
import seedu.address.model.note.NoteText;
import seedu.address.model.note.NoteTime;
import seedu.address.model.note.NoteTitle;

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
        this.moduleCode = (note.getModuleCode() == null) ? "" : note.getModuleCode().toString();
        this.title = note.getTitle().toString();
        this.startDate = (note.getStartDate() == null) ? "" : note.getStartDate().toString();
        this.startTime = note.getStartTime().toString();
        this.endDate = (note.getEndDate() == null) ? "" : note.getEndDate().toString();
        this.endTime = note.getEndTime().toString();
        this.location = note.getLocation().toString();
        this.noteText = note.getNoteText().toString();
    }

    /**
     * Converts this XmlAdaptedNote into the model's Note object
     *
     * If illegal values are found in the xml data, the Note object
     * will not be created.
     *
     * @return Note object, otherwise null
     */
    public Note toModelType() {
        if (this.moduleCode == null
            || this.title == null
            || this.startDate == null
            || this.startTime == null
            || this.endDate == null
            || this.endTime == null
            || this.location == null) {

            return null;
        }

        ModuleCode moduleCode = null;
        if (!this.moduleCode.trim().isEmpty()) {
            try {
                moduleCode = new ModuleCode(this.moduleCode);
            } catch (IllegalArgumentException | NullPointerException e) {
                return null;
            }
        }

        NoteTitle title;
        if (NoteTitle.isValidTitle(this.title)) {
            title = new NoteTitle(this.title);
        } else {
            return null;
        }

        NoteDate startDate = null;
        if (!this.startDate.trim().isEmpty()) {
            try {
                startDate = new NoteDate(this.startDate);
            } catch (IllegalArgumentException | NullPointerException e) {
                return null;
            }
        }

        NoteTime startTime = new NoteTime(NoteTime.DEFAULT_START_TIME.format(NoteTime.TIME_FORMAT));
        if (!this.startTime.trim().isEmpty()) {
            try {
                startTime = new NoteTime(this.startTime.trim());
            } catch (IllegalArgumentException | NullPointerException e) {
                return null;
            }
        }

        NoteDate endDate = null;
        if (startDate != null) {
            if (this.endDate.trim().isEmpty()) {
                endDate = new NoteDate(startDate.getDate().format(NoteDate.DATE_FORMAT));
            } else {
                try {
                    endDate = new NoteDate(this.endDate.trim());
                } catch (IllegalArgumentException | NullPointerException e) {
                    return null;
                }
            }
        }

        NoteTime endTime = new NoteTime(NoteTime.DEFAULT_END_TIME.format(NoteTime.TIME_FORMAT));
        if (!this.endTime.trim().isEmpty()) {
            try {
                endTime = new NoteTime(this.endTime.trim());
            } catch (IllegalArgumentException | NullPointerException e) {
                return null;
            }
        }

        NoteLocation location;
        if (NoteLocation.isValidLocation(this.location)) {
            location = new NoteLocation(this.title);
        } else {
            return null;
        }

        NoteText noteText;
        if (NoteText.isValidNoteText(this.noteText)) {
            noteText = new NoteText(this.noteText);
        } else {
            return null;
        }

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

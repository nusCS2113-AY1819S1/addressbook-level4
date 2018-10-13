package seedu.address.model.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This represents a note.
 */
@XmlRootElement(name = "note")
@XmlAccessorType(XmlAccessType.FIELD)
public class Note {

    public static final String MESSAGE_NOTE_CONSTRAINTS =
            "Notes can take any values, and it should not be blank";

    public static final String NOTE_VALIDATION_REGEX = "[^\\s].*";

    @XmlElement(name = "note", required = true, nillable = true)
    private String note;

    @XmlElement(name = "ModuleCode", required = true, nillable = true)
    private String moduleCode;

    public Note() {

    }


    public Note(String note, String moduleCode) {
        requireNonNull(note);

        // Check if Module Code exists in data file - TO BE IMPLEMENTED

        checkArgument(isValidNote(note), MESSAGE_NOTE_CONSTRAINTS);

        this.note = note;
        this.moduleCode = moduleCode;
    }

    public String getNote() {
        return note;
    }

    public void editNote(String note) {
        this.note = note;
    }

    public String getModuleCode() {
        return moduleCode; }

    public void editModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    private static boolean isValidNote(String test) {
        return test.matches(NOTE_VALIDATION_REGEX);
    }

}

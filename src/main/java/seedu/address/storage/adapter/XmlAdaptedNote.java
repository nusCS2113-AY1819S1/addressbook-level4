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

    @XmlElement(name = "date", required = true, nillable = true)
    private String date;

    @XmlElement(name = "text", required = true, nillable = true)
    private String text;

    /**
     * Constructs an XmlAdaptedNote.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedNote() { }

    /**
     * Constructs an {@code XmlAdaptedNote} with the given note details
     */
    public XmlAdaptedNote(String moduleCode, String date, String text) {
        this.moduleCode = moduleCode;
        this.date = date;
        this.text = text;
    }

    /**
     * Converts a Note into an {@code XmlAdaptedNote} for JAXB use
     */
    public XmlAdaptedNote(Note note) {
        this.moduleCode = note.getModuleCode();
        this.date = note.getDate();
        this.text = note.getNoteText();
    }

    /**
     * Converts this XmlAdaptedNote into the model's Note object
     */
    public Note toModelType() {
        return new Note(moduleCode, date, text);
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public String getDate() {
        return this.date;
    }

    public String getText() {
        return this.text;
    }
}

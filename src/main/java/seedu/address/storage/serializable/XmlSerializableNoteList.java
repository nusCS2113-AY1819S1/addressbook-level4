package seedu.address.storage.serializable;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.note.Note;
import seedu.address.storage.adapter.XmlAdaptedNote;


/**
 * Represents a list of notes that is serializable to XML format
 */
@XmlRootElement(namespace = "seedu.address.storage.serializable")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlSerializableNoteList {

    @XmlElementWrapper(name = "notes")
    @XmlElement(name = "note")
    private ArrayList<XmlAdaptedNote> notes;

    /**
     * Initializes an empty list of notes
     */
    public XmlSerializableNoteList() {
        notes = new ArrayList<>();
    }

    /**
     * Converts an array list of notes into this class' format
     */
    public XmlSerializableNoteList(ArrayList<Note> src) {
        this();
        notes.addAll(src.stream().map(XmlAdaptedNote::new).collect(Collectors.toList()));
    }

    public ArrayList<XmlAdaptedNote> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<XmlAdaptedNote> notes) {
        this.notes = notes;
    }
}

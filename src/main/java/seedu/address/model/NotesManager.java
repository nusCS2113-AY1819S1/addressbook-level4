package seedu.address.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.note.Note;



/**
 This notes manager stores notes for Trajectory.
 */
@XmlRootElement(namespace = "seedu.address.model")
@XmlAccessorType(XmlAccessType.FIELD)
public class NotesManager {
    @XmlElementWrapper(name = "notes")
    @XmlElement(name = "notes")

    private ArrayList<Note> noteList = new ArrayList<Note>();

    public ArrayList<Note> getList() {
        return noteList;
    }

    public void setNotesList(ArrayList<Note> noteList) {
        this.noteList = noteList;
    }
}

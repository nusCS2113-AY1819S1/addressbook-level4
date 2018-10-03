package seedu.address.model;
import seedu.address.model.note.Note;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
/**
 This notes manager stores notes for Trajectory.
 */
@XmlRootElement(namespace = "seedu.address.model")
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

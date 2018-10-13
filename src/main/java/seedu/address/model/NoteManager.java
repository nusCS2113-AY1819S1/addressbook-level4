package seedu.address.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.note.Note;
import seedu.address.storage.StorageHandler;

/**
 * Represents the in-memory model of the Note data.
 */
@XmlRootElement(name = "notes")
public class NoteManager {

    @XmlElement(name = "note")
    private static ArrayList<Note> notes = new ArrayList<Note>();

    private static StorageHandler storage = new StorageHandler();

    public NoteManager() {

    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotesList(ArrayList<Note> notes) {
        this.notes = notes;
    }

    /**
     * Write the current notes dataset to an XML file.
     */
    public void saveDataToFile() {
        storage.saveNoteData();
    }

    /**
     * Converts the notes XML file to the model representation for Note.
     * Loads the model object to NoteManager object
     */
    public void getDataFromFile() {
        storage.retrieveNoteData();
    }
}

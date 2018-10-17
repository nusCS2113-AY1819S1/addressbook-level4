package seedu.address.model.note;

import java.util.ArrayList;
import java.util.stream.Collectors;

import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedNote;

/**
 * Represents the in-memory model of the Note data.
 */
public class NoteManager {

    private ArrayList<Note> notes = new ArrayList<>();

    public NoteManager() {
        readNoteList();
    }

    /**
     * Adds the new note to the in-memory ArrayList.
     */
    public void addNote(Note note) {
        notes.add(note);
    }


    /**
     * Gets the note list from storage and converts it to a Notes array list.
     */
    private void readNoteList() {
        ArrayList<XmlAdaptedNote> xmlNoteList = StorageController.getNoteStorage();
        for (XmlAdaptedNote xmlNote : xmlNoteList) {
            notes.add(xmlNote.toModelType());
        }
    }

    /**
     * Converts the Note array list and invokes the StorageController to save the current note list to file.
     */
    public void saveNoteList() {
        ArrayList<XmlAdaptedNote> xmlAdaptedNotes =
                notes.stream().map(XmlAdaptedNote::new).collect(Collectors.toCollection(ArrayList::new));
        StorageController.setNoteStorage(xmlAdaptedNotes);
        StorageController.storeData();
    }

    public ArrayList<Note> getNotes() {
        return this.notes;
    }

    public void setModules(ArrayList<Note> notes) {
        this.notes = notes;
    }
}

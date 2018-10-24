package seedu.address.model.note;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedNote;

/**
 * Represents the in-memory model of the Note data.
 */
public class NoteManager {

    private static NoteManager noteManager = null;

    private ArrayList<Note> notes = new ArrayList<>();
    private List<Note> filteredNotes;

    private String currentFilter = "";

    private NoteManager() {
        readNoteList();
        filteredNotes = notes;
    }

    public static NoteManager getInstance() {
        if (noteManager == null) {
            noteManager = new NoteManager();
        }
        return noteManager;
    }

    /**
     * Adds the new note to the in-memory ArrayList.
     */
    public void addNote(Note note) {
        notes.add(note);
        setFilteredNotes(currentFilter);
    }

    /**
     * Deletes the specified note from the in-memory ArrayList.
     * It also updates the filteredNotes list.
     */
    public void deleteNote(int index) {
        notes.remove(getNoteAt(index));
        setFilteredNotes(currentFilter);
    }

    /**
     * Retrieves the Note object at the specified {@code index}.
     *
     * @param index index of the element to retrieve from the filteredNotes
     * @return Note object at the specified index, or null if index is out of bounds.
     */
    public Note getNoteAt(int index) {
        Note noteToGet;

        try {
            noteToGet = filteredNotes.get(index);
            return noteToGet;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
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

    public void setFilteredNotes(String moduleCode) {
        if (!moduleCode.trim().isEmpty()) {
            filteredNotes = notes.stream()
                    .filter(p -> p.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());

            if (filteredNotes.size() > 0) {
                currentFilter = moduleCode;
            }
        } else {
            currentFilter = "";
            filteredNotes = notes;
        }
    }

    public List<Note> getFilteredNotes() {
        return filteredNotes;
    }

    public String getCurrentFilter() {
        return this.currentFilter;
    }

    /**
     * Removes all elements in notes list.
     */
    public void clearNotes() {
        notes.clear();
        filteredNotes.clear();
        currentFilter = "";
    }
}

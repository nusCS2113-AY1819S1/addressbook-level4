package seedu.address.model.note;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedNote;

/**
 * Represents the in-memory model of the Note data.
 */
public class NoteManager {

    private static ObservableList<Note> observableNotesList = FXCollections.observableArrayList();
    private static FilteredList<Note> filteredNotes = new FilteredList<>(observableNotesList);

    private ArrayList<Note> notes = new ArrayList<>();

    public NoteManager() {
        readNoteList();
        updateNoteObservableList(notes);
    }

    /**
     * Adds the new note to the in-memory ArrayList and the ObservableList.
     */
    public void addNote(Note note) {
        notes.add(note);
        observableNotesList.add(note);
    }

    public ObservableList<Note> getObservableNotesList() {
        return observableNotesList;
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
     * Syncs the ObservableList {@code observableNotesList} with the ArrayList{@code notes}.
     */
    private void updateNoteObservableList(ArrayList<Note> notes) {
        observableNotesList.setAll(notes);
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

    /**
     * Returns an unmodifiable view of the list of {@code Note} backed by the list of
     * {@code filteredNotes}.
     */
    public ObservableList<Note> getFilteredNotesList() {
        return FXCollections.unmodifiableObservableList(filteredNotes);
    }

    /**
     * Filters the note list with a predicate.
     * It supports filtering by moduleCode.
     *
     * A null predicate will list all note data.
     *
     * @param predicate
     */
    public void updateFilteredNotesList(Predicate<Note> predicate) {
        filteredNotes.setPredicate(predicate);
    }

    /**
     * Check the size of the filteredNotes.
     * It can be used to determine if the predicate
     * used results in an empty list.
     *
     * @return size of filteredNotes
     */
    public int getFilteredNotesListSize() {
        return filteredNotes.size();
    }
}

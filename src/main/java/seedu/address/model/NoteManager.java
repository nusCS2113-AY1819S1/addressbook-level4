package seedu.address.model;

import java.util.function.Predicate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.model.note.Note;
import seedu.address.storage.StorageHandler;

/**
 * Represents the in-memory model of the Note data.
 */
@XmlRootElement(name = "notes")
public class NoteManager {

    @XmlElement(name = "note")
    private static ObservableList<Note> observableNotesList = FXCollections.observableArrayList();;

    private static FilteredList<Note> filteredNotes = new FilteredList<>(observableNotesList);

    private static StorageHandler storage = new StorageHandler();

    public NoteManager() {

    }

    /**
     * Adds the new note data to the ObservableList.
     *
     * @param note
     */
    public void addNote(Note note) {
        observableNotesList.add(note);
    }

    public ObservableList<Note> getObservableNotesList() {
        return observableNotesList;
    }

    // -----------FilteredList-related methods-----------

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    public ObservableList<Note> getFilteredNotesList() {
        return FXCollections.unmodifiableObservableList(filteredNotes);
    }

    /**
     * Filters the notes list with a predicate.
     * It supports filtering by moduleCode.
     *
     * A null predicate will display all notes data in the ListView.
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


    // -----------Storage-related methods-----------

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

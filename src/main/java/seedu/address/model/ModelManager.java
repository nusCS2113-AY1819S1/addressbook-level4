package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.ClubBudgetElementsBookChangedEvent;
import seedu.address.commons.events.model.FinalBudgetsBookChangedEvent;
import seedu.address.commons.events.model.LoginBookChangedEvent;
import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.clubbudget.FinalClubBudget;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.person.Person;
import seedu.address.model.searchhistory.KeywordType;
import seedu.address.model.searchhistory.KeywordsRecord;
import seedu.address.model.searchhistory.ReadOnlyKeywordsRecord;
import seedu.address.model.searchhistory.SearchHistoryManager;
import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final SearchHistoryManager<Person> searchHistoryManager = new SearchHistoryManager<>();
    private final KeywordsRecord keywordsRecord = new KeywordsRecord();
    private final VersionedLoginBook versionedLoginBook;
    private final VersionedAddressBook versionedAddressBook;
    private final VersionedClubBudgetElementsBook versionedClubBudgetElementsBook;
    private final VersionedFinalBudgetsBook versionedFinalBudgetsBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<LoginDetails> filteredLoginDetails;
    private final FilteredList<ClubBudgetElements> filteredClubs;
    private final FilteredList<FinalClubBudget> filteredClubBudgets;

    /**
     * Initializes a ModelManager with the given addressBook, clubBudgetElementsBook, finalBudgetsBook and userPrefs.
     */
    public ModelManager(ReadOnlyLoginBook loginBook, ReadOnlyAddressBook addressBook,
                        ReadOnlyClubBudgetElementsBook clubBudgetElementsBook,
                        ReadOnlyFinalBudgetBook finalBudgetsBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(loginBook, addressBook, clubBudgetElementsBook, finalBudgetsBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + "club budget elements book: " + clubBudgetElementsBook
                + "final budgets book: " + finalBudgetsBook + " and user prefs " + userPrefs);

        versionedLoginBook = new VersionedLoginBook(loginBook);
        versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedClubBudgetElementsBook = new VersionedClubBudgetElementsBook(clubBudgetElementsBook);
        versionedFinalBudgetsBook = new VersionedFinalBudgetsBook(finalBudgetsBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredClubs = new FilteredList<>(versionedClubBudgetElementsBook.getClubsList());
        filteredLoginDetails = new FilteredList<>(versionedLoginBook.getLoginDetailsList());
        filteredClubBudgets = new FilteredList<>(versionedFinalBudgetsBook.getClubBudgetsList());
    }

    public ModelManager() {
        this(new LoginBook(), new AddressBook(), new ClubBudgetElementsBook(), new FinalBudgetsBook(),
                new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        resetSearchHistoryToInitialState();
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public void resetData(ReadOnlyClubBudgetElementsBook newData) {
        searchHistoryManager.clearSearchHistory();
        versionedClubBudgetElementsBook.resetData(newData);
        indicateClubBudgetElementsBookChanged();
    }

    @Override
    public void resetData(ReadOnlyFinalBudgetBook newData) {
        searchHistoryManager.clearSearchHistory();
        versionedFinalBudgetsBook.resetData(newData);
        indicateFinalBudgetsBookChanged();
    }

    @Override
    public ReadOnlyLoginBook getLoginBook() {
        return versionedLoginBook;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public ReadOnlyClubBudgetElementsBook getClubBudgetElementsBook() {
        return versionedClubBudgetElementsBook;
    }

    @Override
    public ReadOnlyFinalBudgetBook getFinalBudgetsBook() {
        return versionedFinalBudgetsBook;
    }


    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    /** Raises an event to indicate the model has changed */
    private void indicateClubBudgetElementsBookChanged() {
        raise(new ClubBudgetElementsBookChangedEvent(versionedClubBudgetElementsBook));
    }

    /** Raises an event to indicate the model has changed */
    private void indicateFinalBudgetsBookChanged() {
        raise(new FinalBudgetsBookChangedEvent(versionedFinalBudgetsBook));
    }

    private void indicateLoginBookChanged() {
        raise(new LoginBookChangedEvent(versionedLoginBook));
    }

    @Override
    public void createAccount(LoginDetails details) {
        versionedLoginBook.createAccount(details);
        updateFilteredLoginDetailsList(PREDICATE_SHOW_ALL_ACCOUNTS);
        indicateLoginBookChanged();
    }

    @Override
    public boolean hasAccount(LoginDetails details) {
        requireNonNull(details);
        return versionedLoginBook.hasAccount(details);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        resetSearchHistoryToInitialState();
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Submitting Budget Data =====================================================================

    @Override
    public boolean hasClub(ClubBudgetElements club) {
        requireNonNull(club);
        return versionedClubBudgetElementsBook.hasClub(club);
    }

    @Override
    public void addClub(ClubBudgetElements club) {
        versionedClubBudgetElementsBook.addClub(club);
        indicateClubBudgetElementsBookChanged();
    }

    //=========== Filtered Account List Accessors =============================================================

    @Override
    public ObservableList<LoginDetails> getFilteredLoginDetailsList() {
        return FXCollections.unmodifiableObservableList(filteredLoginDetails);
    }

    @Override
    public void updateFilteredLoginDetailsList(Predicate<LoginDetails> predicate) {
        requireNonNull(predicate);
        filteredLoginDetails.setPredicate(predicate);
    }

    //=========== Getting Final Budget =====================================================================

    @Override
    public boolean hasClubBudget(FinalClubBudget clubBudget) {
        requireNonNull(clubBudget);
        return versionedFinalBudgetsBook.hasClubBudget(clubBudget);
    }

    @Override
    public void addClubBudget(FinalClubBudget clubBudget) {
        versionedFinalBudgetsBook.addClubBudget(clubBudget);
        indicateFinalBudgetsBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Clubs List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ClubBudgetElements} backed by the internal list of
     * {@code versionedClubBudgetElementsBook}
     */
    @Override
    public ObservableList<ClubBudgetElements> getFilteredClubsList() {
        return FXCollections.unmodifiableObservableList(filteredClubs);
    }

    //=========== Filtered Club Budgets List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code FinalClubBudget} backed by the internal list of
     * {@code versionedFinalBudgetsBook}
     */
    @Override
    public ObservableList<FinalClubBudget> getFilteredClubBudgetsList() {
        return FXCollections.unmodifiableObservableList(filteredClubBudgets);
    }

    @Override
    public void updateFilteredClubBudgetsList(Predicate<FinalClubBudget> predicate) {
        requireNonNull(predicate);
        filteredClubBudgets.setPredicate(predicate);
    }

    //=========== Undo/Redo AddressBook ================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //=========== Undo/Redo ClubBudgetElementsBook =====================================================================

    @Override
    public boolean canUndoClubBudgetElementsBook() {
        return versionedClubBudgetElementsBook.canUndo();
    }

    @Override
    public boolean canRedoClubBudgetElementsBook() {
        return versionedClubBudgetElementsBook.canRedo();
    }

    @Override
    public void undoClubBudgetElementsBook() {
        versionedClubBudgetElementsBook.undo();
        indicateClubBudgetElementsBookChanged();
    }

    @Override
    public void redoClubBudgetElementsBook() {
        versionedClubBudgetElementsBook.redo();
        indicateClubBudgetElementsBookChanged();
    }

    @Override
    public void commitClubBudgetElementsBook() {
        versionedClubBudgetElementsBook.commit();
    }


    //=========== Undo/Redo FinalBudgetsBook =====================================================================

    @Override
    public boolean canUndoFinalBudgetsBook() {
        return versionedFinalBudgetsBook.canUndo();
    }

    @Override
    public boolean canRedoFinalBudgetsBook() {
        return versionedFinalBudgetsBook.canRedo();
    }

    @Override
    public void undoFinalBudgetsBook() {
        versionedFinalBudgetsBook.undo();
        indicateFinalBudgetsBookChanged();
    }

    @Override
    public void redoFinalBudgetsBook() {
        versionedFinalBudgetsBook.redo();
        indicateFinalBudgetsBookChanged();
    }

    @Override
    public void commitFinalBudgetsBook() {
        versionedFinalBudgetsBook.commit();
    }

    //=========== Persons Search Pruning ====================================================
    @Override
    public void revertLastSearch() throws EmptyHistoryException {
        Predicate<Person> predicate = searchHistoryManager.revertLastSearch();
        if (predicate != null) {
            filteredPersons.setPredicate(predicate);
        } else {
            filteredPersons.setPredicate(PREDICATE_SHOW_ALL_PERSONS);
        }
        keywordsRecord.undoKeywordsHistory();
    }

    @Override
    public void executeSearch(Predicate<Person> predicate) {
        requireNonNull(predicate);
        Predicate<Person> updatedPredicate = searchHistoryManager.executeNewSearch(predicate);
        filteredPersons.setPredicate(updatedPredicate);
    }

    @Override
    public void resetSearchHistoryToInitialState() {
        searchHistoryManager.clearSearchHistory();
        filteredPersons.setPredicate(PREDICATE_SHOW_ALL_PERSONS);
        keywordsRecord.clearKeywordsHistory();
    }

    @Override
    public void recordKeywords(KeywordType type, List<String> keywords) {
        requireAllNonNull(keywords);
        keywordsRecord.recordKeywords(type, keywords);
    }

    @Override
    public ReadOnlyKeywordsRecord getReadOnlyKeywordsRecord() {
        return keywordsRecord;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return (versionedAddressBook.equals(other.versionedAddressBook)
                || versionedFinalBudgetsBook.equals(other.versionedFinalBudgetsBook))
                && filteredPersons.equals(other.filteredPersons)
                && filteredClubs.equals(other.filteredClubs)
                && filteredClubBudgets.equals(other.filteredClubBudgets)
                && searchHistoryManager.equals(other.searchHistoryManager)
                && keywordsRecord.equals(other.keywordsRecord);
    }
}

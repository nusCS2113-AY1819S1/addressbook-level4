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
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<LoginDetails> filteredLoginDetails;
    private final FilteredList<ClubBudgetElements> filteredClubs;
    private final FilteredList<FinalClubBudget> filteredClubBudgets;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyLoginBook loginBook, ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(loginBook, addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedLoginBook = new VersionedLoginBook(loginBook);
        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredClubs = new FilteredList<>(versionedAddressBook.getClubsList());
        filteredLoginDetails = new FilteredList<>(versionedLoginBook.getLoginDetailsList());
        filteredClubBudgets = new FilteredList<>(versionedAddressBook.getClubBudgetsList());
    }

    public ModelManager() {
        this(new LoginBook(), new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        resetSearchHistoryToInitialState();
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyLoginBook getLoginBook() {
        return versionedLoginBook;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
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
        return versionedAddressBook.hasClub(club);
    }

    @Override
    public void addClub(ClubBudgetElements club) {
        versionedAddressBook.addClub(club);
        indicateAddressBookChanged();
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
        return versionedAddressBook.hasClubBudget(clubBudget);
    }

    @Override
    public void addClubBudget(FinalClubBudget clubBudget) {
        versionedAddressBook.addClubBudget(clubBudget);
        indicateAddressBookChanged();
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

    //=========== Filtered Clubs List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ClubBudgetElements} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<ClubBudgetElements> getFilteredClubsList() {
        return FXCollections.unmodifiableObservableList(filteredClubs);
    }

    //=========== Filtered Club Budgets List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code FinalClubBudget} backed by the internal list of
     * {@code versionedAddressBook}
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

    //=========== Undo/Redo =================================================================================

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
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons)
                && searchHistoryManager.equals(other.searchHistoryManager)
                && keywordsRecord.equals(other.keywordsRecord);
    }
}

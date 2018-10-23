package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.clubbudget.FinalClubBudget;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.person.Person;
import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    Predicate<LoginDetails> PREDICATE_SHOW_ALL_ACCOUNTS = unused -> true;

    Predicate<ClubBudgetElements> PREDICATE_SHOW_ALL_CLUBS = unused -> true;

    Predicate<FinalClubBudget> PREDICATE_SHOW_ALL_CLUB_BUDGETS = unused -> true;
    /**
     * Creates an account for address book.
     * The account must not already exist in the address book.
     */
    void createAccount(LoginDetails loginDetails);

    /**
     * Returns true if an account with the same user ID as {@code account} exists in the address book.
     */
    boolean hasAccount(LoginDetails credentials);

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the LoginBook */
    ReadOnlyLoginBook getLoginBook();

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered login details list */
    ObservableList<LoginDetails> getFilteredLoginDetailsList();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the filtered clubs list */
    ObservableList<ClubBudgetElements> getFilteredClubsList();

    /**
     * Returns an unmodifiable view of the filtered club budgets list */
    ObservableList<FinalClubBudget> getFilteredClubBudgetsList();

    /**
     * Updates the filter of the filtered login details list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLoginDetailsList(Predicate<LoginDetails> predicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered club budgets list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredClubBudgetsList(Predicate<FinalClubBudget> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Returns true if a club with the same identity as {@code club} exists in the address book.
     */
    boolean hasClub(ClubBudgetElements club);

    /**
     * Adds the given club.
     * @code club} must not already exist in the address book.
     */
    void addClub(ClubBudgetElements club);

    /**
     * Returns true if a club budget with the same identity as {@code clubBudget} exists in the address book.
     */
    boolean hasClubBudget(FinalClubBudget clubBudget);

    /**
     * Adds the given club budget.
     * @code clubBudget} must not already exist in the address book.
     */
    void addClubBudget(FinalClubBudget clubBudget);

    /**
     * Reverts model's filtered person list and search history to previous state.
     */
    void revertLastSearch() throws EmptyHistoryException;

    /**
     * Updates model's filtered person list and search history to the next state.
     */
    void executeSearch(Predicate<Person> predicate);

    /**
     * Resets all search history and returns filtered person list to initial state.
     */
    void resetSearchHistoryToInitialState();
}

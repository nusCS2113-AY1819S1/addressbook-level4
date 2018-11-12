package seedu.address.model;

import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.addressbook.ReadOnlyAddressBook;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ReadOnlyExpensesList;
import seedu.address.model.person.Person;
import seedu.address.model.recruitment.ReadOnlyRecruitmentList;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.schedule.ReadOnlyScheduleList;
import seedu.address.model.schedule.Schedule;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Expenses> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Schedule> PREDICATE_SHOW_ALL_SCHEDULES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Recruitment> PREDICATE_SHOW_ALL_RECRUITMENT = unused -> true;

    /** Checks if model can be redo or undo */
    boolean canRedoModel();
    boolean canUndoModel();

    /** Get the last or next commit storage type  */
    Set<ModelTypes> getNextCommitType();
    Set<ModelTypes> getLastCommitType();

    /** Clears existing backing model and replaces with the provided new data. */
    void resetAddressBookData(ReadOnlyAddressBook newData);
    void resetDataExpenses(ReadOnlyExpensesList newData);
    void resetScheduleListData(ReadOnlyScheduleList newData);
    void resetRecruitmentListData(ReadOnlyRecruitmentList newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();
    ReadOnlyExpensesList getExpensesList();
    ReadOnlyScheduleList getScheduleList();
    ReadOnlyRecruitmentList getRecruitmentList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasExpenses(Expenses expenses);
    boolean hasPerson(Person person);
    boolean hasPerson(Person person, Predicate<Person> predicate);
    boolean hasSchedule(Schedule schedule);
    boolean hasRecruitment(Recruitment recruitment);
    boolean hasEmployeeId(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteExpenses(Expenses target);
    void deletePerson(Person target);
    void deleteSchedule(Schedule target);
    void deleteRecruitmentPost(Recruitment target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addExpenses (Expenses expenses);
    void addPerson(Person person);
    void addSchedule(Schedule schedule);
    void addRecruitment(Recruitment recruitment);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updateExpenses(Expenses target, Expenses editedExpenses);
    void updatePerson(Person target, Person editedPerson);
    void updateSchedule(Schedule target, Schedule editedSchedule);
    void updateRecruitment(Recruitment target, Recruitment editedSchedule);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Expenses> getFilteredExpensesList();
    ObservableList<Person> getFilteredPersonList();
    ObservableList<Person> getFilteredPersonList(FilteredList<Person> dummyFilteredPersons);
    ObservableList<Schedule> getFilteredScheduleList();
    ObservableList<Recruitment> getFilteredRecruitmentList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpensesList(Predicate<Expenses> predicate);
    void updateFilteredPersonList(Predicate<Person> predicate);
    void updateFilteredPersonList(Predicate<Person> predicate, String sortOrder);
    void updateFilteredScheduleList(Predicate<Schedule> predicate);
    void updateFilteredRecruitmentList(Predicate<Recruitment> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();
    boolean canUndoExpensesList();
    boolean canUndoScheduleList();
    boolean canUndoRecruitmentList();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();
    boolean canRedoExpensesList();
    boolean canRedoScheduleList();
    boolean canRedoRecruitmentList();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();
    void undoExpensesList();
    void undoScheduleList();
    void undoRecruitmentList();
    void undoModelList();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();
    void redoExpensesList();
    void redoScheduleList();
    void redoRecruitmentList();
    void redoModelList();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();
    void commitExpensesList();
    void commitScheduleList();
    void commitRecruitmentPostList();
    void commitMultipleLists(Set<ModelTypes> stack);

}

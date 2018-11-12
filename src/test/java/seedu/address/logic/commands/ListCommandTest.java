package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
    }

    @Test
    public void equals() {
        String firstSortByParams = "dep";
        String secondSortByParams = "all people";
        String thirdSortByParams = "all event";
        DepartmentContainsKeywordsPredicate firstPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("Admin"));
        DepartmentContainsKeywordsPredicate secondPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("Finance"));
        List<String> showAll = new ArrayList<>();
        DepartmentContainsKeywordsPredicate thirdPredicate = new DepartmentContainsKeywordsPredicate(showAll);
        ListCommand listFirstCommand = new ListCommand(firstSortByParams, firstPredicate);
        ListCommand listSecondCommand = new ListCommand(firstSortByParams, secondPredicate);
        ListCommand listThirdCommand = new ListCommand(secondSortByParams, thirdPredicate);
        ListCommand listFourthCommand = new ListCommand(thirdSortByParams, thirdPredicate);

        // same object -> returns true
        assertTrue(listFirstCommand.equals(listFirstCommand));

        // different types -> returns false
        assertFalse(listFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(listFirstCommand.equals(listSecondCommand));

        // same object -> returns true
        assertTrue(listThirdCommand.equals(listThirdCommand));

        // same object -> returns true
        assertTrue(listFourthCommand.equals(listFourthCommand));

        // different types -> returns false
        assertFalse(listThirdCommand.equals(1));

        // null -> returns false
        assertFalse(listThirdCommand.equals(null));

        // same values -> returns true
        ListCommand listFirstCommandCopy = new ListCommand(firstSortByParams, firstPredicate);
        assertEquals(listFirstCommand, listFirstCommandCopy);

        // same values -> returns true
        ListCommand listSecondCommandCopy = new ListCommand(secondSortByParams, thirdPredicate);
        assertEquals(listThirdCommand, listSecondCommandCopy);

        // same values -> returns true
        ListCommand listThirdCommandCopy = new ListCommand(thirdSortByParams, thirdPredicate);
        assertEquals(listFourthCommand, listThirdCommandCopy);


    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 6);
        DepartmentContainsKeywordsPredicate predicate = preparePredicate("Publicity Programmes Admin");
        String sortByParams = "dep";

        ListCommand command = new ListCommand(sortByParams, predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_personListIsNotFiltered_showsSameList() {
        String sortByParams = "all people";
        List<String> showAll = new ArrayList<>();
        DepartmentContainsKeywordsPredicate predicate = new DepartmentContainsKeywordsPredicate(showAll);
        assertCommandSuccess(new ListCommand(sortByParams, predicate), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS_PEOPLE, expectedModel);
    }

    @Test
    public void execute_personListIsFiltered_showsEverything() {
        String sortByParams = "all people";
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        List<String> showAll = new ArrayList<>();
        DepartmentContainsKeywordsPredicate predicate = new DepartmentContainsKeywordsPredicate(showAll);
        assertCommandSuccess(new ListCommand(sortByParams, predicate), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS_PEOPLE, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code DepartmentContainsKeywordsPredicate}.
     */
    private DepartmentContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DepartmentContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

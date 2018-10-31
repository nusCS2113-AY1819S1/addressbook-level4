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
        ListCommand findFirstCommand = new ListCommand(firstSortByParams, firstPredicate);
        ListCommand findSecondCommand = new ListCommand(firstSortByParams, secondPredicate);
        ListCommand findThirdCommand = new ListCommand(secondSortByParams, thirdPredicate);
        ListCommand findFourthCommand = new ListCommand(thirdSortByParams, thirdPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // same object -> returns true
        assertTrue(findThirdCommand.equals(findThirdCommand));

        // same object -> returns true
        assertTrue(findFourthCommand.equals(findFourthCommand));

        // different types -> returns false
        assertFalse(findThirdCommand.equals(1));

        // null -> returns false
        assertFalse(findThirdCommand.equals(null));

        // same values -> returns true
        ListCommand findFirstCommandCopy = new ListCommand(firstSortByParams, firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // same values -> returns true
        ListCommand findSecondCommandCopy = new ListCommand(secondSortByParams, thirdPredicate);
        assertEquals(findThirdCommand, findSecondCommandCopy);

        // same values -> returns true
        ListCommand findSThirdCommandCopy = new ListCommand(thirdSortByParams, thirdPredicate);
        assertEquals(findFourthCommand, findSThirdCommandCopy);


    }

    /*@Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DepartmentContainsKeywordsPredicate predicate = preparePredicate(" ");
        String firstSortByParams = "dep";
        ListCommand firstCommand = new ListCommand(firstSortByParams, predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(firstCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

    }*/

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

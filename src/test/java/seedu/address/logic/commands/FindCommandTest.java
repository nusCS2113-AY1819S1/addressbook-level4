package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalLoginBook;
import static seedu.address.testutil.TypicalClubBudgetElements.getTypicalClubBudgetElementsBook;
import static seedu.address.testutil.TypicalFinalClubBudget.getTypicalFinalBudgetsBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.JOHN;
import static seedu.address.testutil.TypicalPersons.KHOR;
import static seedu.address.testutil.TypicalPersons.SEGWIT;
import static seedu.address.testutil.TypicalPersons.getTypicalTaggedAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.searchhistory.KeywordType;
import seedu.address.testutil.KeywordsOutputUtil;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalLoginBook(), getTypicalTaggedAddressBook(),
            getTypicalClubBudgetElementsBook(), getTypicalFinalBudgetsBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalLoginBook(),
            getTypicalTaggedAddressBook(), getTypicalClubBudgetElementsBook(), getTypicalFinalBudgetsBook(),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        TagContainsKeywordsPredicate thirdPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("third"));
        TagContainsKeywordsPredicate fourthPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("fourth"));

        FindCommand findFirstCommand = new IncludeNameFindCommand(firstPredicate);
        FindCommand findSecondCommand = new IncludeNameFindCommand(secondPredicate);
        FindCommand findThirdCommand = new IncludeTagFindCommand(thirdPredicate);
        FindCommand findFourthCommand = new IncludeTagFindCommand(fourthPredicate);
        FindCommand findFifthCommand = new ExcludeNameFindCommand(firstPredicate);
        FindCommand findSixthCommand = new ExcludeNameFindCommand(secondPredicate);
        FindCommand findSeventhCommand = new ExcludeTagFindCommand(thirdPredicate);
        FindCommand findEighthCommand = new ExcludeTagFindCommand(fourthPredicate);


        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));
        assertTrue(findThirdCommand.equals(findThirdCommand));
        assertTrue(findFifthCommand.equals(findFifthCommand));
        assertTrue(findSeventhCommand.equals(findSeventhCommand));

        // same type same predicate -> returns true
        FindCommand findFirstCommandCopy = new IncludeNameFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        FindCommand findThirdCommandCopy = new IncludeTagFindCommand(thirdPredicate);
        assertTrue(findThirdCommand.equals(findThirdCommandCopy));

        FindCommand findFifthCommandCopy = new ExcludeNameFindCommand(firstPredicate);
        assertTrue(findFifthCommand.equals(findFifthCommandCopy));

        FindCommand findSixthCommandCopy = new ExcludeTagFindCommand(thirdPredicate);
        assertTrue(findSeventhCommand.equals(findSixthCommandCopy));

        // different type same predicate -> returns false
        assertFalse(findFirstCommand.equals(findFifthCommand));
        assertFalse(findThirdCommand.equals(findSeventhCommand));
        assertFalse(findFifthCommand.equals(findFirstCommand));
        assertFalse(findSeventhCommand.equals(findThirdCommand));

        // same type different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
        assertFalse(findThirdCommand.equals(findFourthCommand));
        assertFalse(findFifthCommand.equals(findSixthCommand));
        assertFalse(findSeventhCommand.equals(findEighthCommand));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));
        assertFalse(findThirdCommand.equals(null));
        assertFalse(findFifthCommand.equals(null));
        assertFalse(findSeventhCommand.equals(null));
    }

    @Test
    public void execute_zeroIncludeNameKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW
                + KeywordsOutputUtil.getOutputString(null, null, null, null), 0);
        NameContainsKeywordsPredicate predicate = prepareNameContainsKeywordsPredicate(" ");
        FindCommand command = new IncludeNameFindCommand(predicate);
        expectedModel.executeSearch(predicate);
        expectedModel.recordKeywords(KeywordType.IncludeNames, new ArrayList<>());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroIncludeTagKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW
                + KeywordsOutputUtil.getOutputString(null, null, null, null), 0);
        TagContainsKeywordsPredicate predicate = prepareTagContainsKeywordsPredicate(" ");
        FindCommand command = new IncludeTagFindCommand(predicate);
        expectedModel.executeSearch(predicate);
        expectedModel.recordKeywords(KeywordType.IncludeTags, new ArrayList<>());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroExcludeNameKeywords_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW
                + KeywordsOutputUtil.getOutputString(null, null, null, null), 10);
        NameContainsKeywordsPredicate predicate = prepareNameContainsKeywordsPredicate(" ");
        FindCommand command = new ExcludeNameFindCommand(predicate);
        expectedModel.executeSearch(predicate.negate());
        expectedModel.recordKeywords(KeywordType.ExcludeNames, new ArrayList<>());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, JOHN, SEGWIT, KHOR),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroExcludeTagKeywords_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW
                + KeywordsOutputUtil.getOutputString(null, null, null, null), 10);
        TagContainsKeywordsPredicate predicate = prepareTagContainsKeywordsPredicate(" ");
        FindCommand command = new ExcludeTagFindCommand(predicate);
        expectedModel.executeSearch(predicate.negate());
        expectedModel.recordKeywords(KeywordType.ExcludeTags, new ArrayList<>());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, JOHN, SEGWIT, KHOR),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleIncludeNameKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW
                + KeywordsOutputUtil.getOutputString(null, Arrays.asList("kurz", "elle", "kunz"), null, null), 3);
        NameContainsKeywordsPredicate predicate = prepareNameContainsKeywordsPredicate("Kurz Elle Kunz");
        FindCommand command = new IncludeNameFindCommand(predicate);
        expectedModel.executeSearch(predicate);
        expectedModel.recordKeywords(KeywordType.IncludeNames, Arrays.asList("kurz", "elle", "kunz"));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleExcludeNameKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW
                + KeywordsOutputUtil.getOutputString(null, null, null, Arrays.asList("kurz", "elle", "kunz")), 7);
        NameContainsKeywordsPredicate predicate = prepareNameContainsKeywordsPredicate("Kurz Elle Kunz");
        FindCommand command = new ExcludeNameFindCommand(predicate);
        expectedModel.executeSearch(predicate.negate());
        expectedModel.recordKeywords(KeywordType.ExcludeNames, Arrays.asList("kurz", "elle", "kunz"));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, GEORGE, JOHN, SEGWIT, KHOR), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleIncludeTagKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW
                + KeywordsOutputUtil.getOutputString(Arrays.asList(VALID_TAG_FRIEND, VALID_TAG_HUSBAND),
                null, null, null), 2);
        TagContainsKeywordsPredicate predicate =
                prepareTagContainsKeywordsPredicate(VALID_TAG_FRIEND + " " + VALID_TAG_HUSBAND);
        FindCommand command = new IncludeTagFindCommand(predicate);
        expectedModel.executeSearch(predicate);
        expectedModel.recordKeywords(KeywordType.IncludeTags, Arrays.asList(VALID_TAG_FRIEND, VALID_TAG_HUSBAND));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(SEGWIT, KHOR), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleExcludeTagKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW
                + KeywordsOutputUtil.getOutputString(null, null,
                Arrays.asList(VALID_TAG_FRIEND, VALID_TAG_HUSBAND), null), 8);
        TagContainsKeywordsPredicate predicate =
                prepareTagContainsKeywordsPredicate(VALID_TAG_FRIEND + " " + VALID_TAG_HUSBAND);
        FindCommand command = new ExcludeTagFindCommand(predicate);
        expectedModel.executeSearch(predicate.negate());
        expectedModel.recordKeywords(KeywordType.ExcludeTags, Arrays.asList(VALID_TAG_FRIEND, VALID_TAG_HUSBAND));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, JOHN),
                model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNameContainsKeywordsPredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TagContainsKeywordsPredicate}.
     */
    private TagContainsKeywordsPredicate prepareTagContainsKeywordsPredicate(String userInput) {
        return new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

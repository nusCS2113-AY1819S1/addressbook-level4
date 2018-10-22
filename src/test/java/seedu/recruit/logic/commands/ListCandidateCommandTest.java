package seedu.recruit.logic.commands;

import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCandidateCommand.
 */
public class ListCandidateCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getCandidateBook(), new CompanyBook(), new UserPrefs());
    }

    @Test
    @Ignore
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCandidateCommand(), model, commandHistory,
                ListCandidateCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    @Ignore
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCandidateCommand(), model, commandHistory,
                ListCandidateCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

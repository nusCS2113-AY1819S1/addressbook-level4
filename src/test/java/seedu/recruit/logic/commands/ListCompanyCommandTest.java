package seedu.recruit.logic.commands;

import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.showCompanyAtIndex;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.getTypicalCompanyBook;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Before;
import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCandidateCommand.
 */
public class ListCompanyCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
        expectedModel = new ModelManager(new CandidateBook(), model.getCompanyBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCompanyCommand(), model, commandHistory,
                ListCompanyCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showCompanyAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCompanyCommand(), model, commandHistory,
                ListCompanyCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

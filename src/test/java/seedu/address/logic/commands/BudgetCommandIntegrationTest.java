package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalLoginBook;
import static seedu.address.testutil.TypicalClubBudgetElements.getTypicalClubBudgetElementsBook;
import static seedu.address.testutil.TypicalFinalClubBudget.getTypicalFinalBudgetsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.testutil.ClubBudgetElementsBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code BudgetCommand}.
 */
public class BudgetCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalLoginBook(), getTypicalAddressBook(),
                getTypicalClubBudgetElementsBook(), getTypicalFinalBudgetsBook(), new UserPrefs());
    }

    @Test
    public void execute_newClubBudgetElements_success() {
        ClubBudgetElements validClub = new ClubBudgetElementsBuilder().withClubName("Test Club")
                .withExpectedTurnout("2000").withNumberOfEvents("5").build();

        Model expectedModel = new ModelManager(model.getLoginBook(), model.getAddressBook(),
                model.getClubBudgetElementsBook(), model.getFinalBudgetsBook(), new UserPrefs());
        expectedModel.addClub(validClub);
        expectedModel.commitClubBudgetElementsBook();

        assertCommandSuccess(new BudgetCommand(validClub), model, commandHistory,
                String.format(BudgetCommand.MESSAGE_SUCCESS, validClub), expectedModel);
    }

    @Test
    public void execute_duplicateClubBudgetElements_throwsCommandException() {
        ClubBudgetElements clubInList = model.getClubBudgetElementsBook().getClubsList().get(0);
        assertCommandFailure(new BudgetCommand(clubInList), model, commandHistory,
                BudgetCommand.MESSAGE_DUPLICATE_CLUB);
    }

}

package seedu.recruit.logic.commands;

import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

public class UndoCandidateBookCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoCandidateBook();
        assertCommandSuccess(new UndoCandidateBookCommand(), model, commandHistory,
                UndoCandidateBookCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoCandidateBook();
        assertCommandSuccess(new UndoCandidateBookCommand(), model, commandHistory,
                UndoCandidateBookCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCandidateBookCommand(), model, commandHistory,
                UndoCandidateBookCommand.MESSAGE_FAILURE);
    }
}

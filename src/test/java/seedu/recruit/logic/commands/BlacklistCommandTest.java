package seedu.recruit.logic.commands;

import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.getTypicalCompanyBook;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for BlacklistCommand.
 */
public class BlacklistCommandTest {
    // need to check that bl candidates cannot be modified or shortlisted
    // need to check that cannot bl shortlisted ppl
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    public BlacklistCommandTest() throws CommandException {
    }

    @Test
    public void execute_blacklistUnblacklisted_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), new UserPrefs());

        Candidate originalCandidate = expectedModel.getFilteredCandidateList().get(INDEX_FIRST.getZeroBased());
        Candidate blacklistedCandidate = BlacklistCommand.insertBlacklistTag(originalCandidate);
        expectedModel.updateCandidate(originalCandidate, blacklistedCandidate);
        expectedModel.commitRecruitBook();

        String expectedMessage = String.format(BlacklistCommand.MESSAGE_BLACKLIST_SUCCESS, blacklistedCandidate);
        BlacklistCommand blacklistCommand = new BlacklistCommand(true, INDEX_FIRST);
        assertCommandSuccess(blacklistCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unblacklistBlacklisted_success() throws CommandException {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), new UserPrefs());

        Candidate originalCandidate = expectedModel.getFilteredCandidateList().get(INDEX_FIRST.getZeroBased());
        Candidate blacklistedCandidate = BlacklistCommand.insertBlacklistTag(originalCandidate);
        expectedModel.updateCandidate(originalCandidate, blacklistedCandidate);
        expectedModel.commitRecruitBook();

        Candidate unblacklistedCandidate = BlacklistCommand.removeBlacklistTag(blacklistedCandidate);
        expectedModel.updateCandidate(blacklistedCandidate, unblacklistedCandidate);
        expectedModel.commitRecruitBook();

        String expectedMessage = String.format(BlacklistCommand.MESSAGE_UNBLACKLIST_SUCCESS, unblacklistedCandidate);
        BlacklistCommand blacklistCommand = new BlacklistCommand(true, INDEX_FIRST);
        CommandResult result = blacklistCommand.execute(model, commandHistory, userPrefs);

        BlacklistCommand unblacklistCommand = new BlacklistCommand(false, INDEX_FIRST);
        assertCommandSuccess(unblacklistCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_blacklistOnBlacklisted_failure() throws CommandException {
        BlacklistCommand blacklistCommand = new BlacklistCommand(true, INDEX_FIRST);
        CommandResult result = blacklistCommand.execute(model, commandHistory, userPrefs);

        BlacklistCommand reBlacklistCommand = new BlacklistCommand(true, INDEX_FIRST);
        String expectedMessage = BlacklistCommand.MESSAGE_ALREADY_BLACKLISTED;
        assertCommandFailure(reBlacklistCommand, model, commandHistory, expectedMessage);

    }

    @Test
    public void execute_unblacklistOnNotBlacklisted_failure() throws CommandException {
        BlacklistCommand unBlacklistCommand = new BlacklistCommand(false, INDEX_FIRST);

        String expectedMessage = BlacklistCommand.MESSAGE_IS_NOT_BLACKLISTED;
        assertCommandFailure(unBlacklistCommand, model, commandHistory, expectedMessage);

    }

    @Test
    public void execute_invalidIndex_failure() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredCandidateList().size() + 1);

        BlacklistCommand blacklistCommand = new BlacklistCommand(true, invalidIndex);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(blacklistCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_blacklistOnShortlisted_failure() throws CommandException {
        SelectCompanyCommand selectCompanyCommand = new SelectCompanyCommand(INDEX_FIRST);
        CommandResult resultCompany = selectCompanyCommand.execute(model, commandHistory, userPrefs);
        SelectJobCommand selectJobCommand = new SelectJobCommand(INDEX_FIRST);
        CommandResult resultJob = selectJobCommand.execute(model, commandHistory, userPrefs);
        SelectCandidateCommand selectCandidateCommand = new SelectCandidateCommand(INDEX_SECOND);
        CommandResult resultCandidate = selectCandidateCommand.execute(model, commandHistory, userPrefs);
        ShortlistCandidateCommand shortlistCandidateCommand = new ShortlistCandidateCommand();
        CommandResult resultShortlist = shortlistCandidateCommand.execute(model, commandHistory, userPrefs);

        BlacklistCommand blacklistCommand = new BlacklistCommand(true, INDEX_SECOND);
        String expectedMessage = BlacklistCommand.MESSAGE_ALREADY_SHORTLISTED;
        assertCommandFailure(blacklistCommand, model, commandHistory, expectedMessage);

        // if blacklist rm is used, it will prompt the "not blacklisted" error message
        BlacklistCommand unBlacklistCommand = new BlacklistCommand(false, INDEX_SECOND);
        String expectedMessage2 = BlacklistCommand.MESSAGE_IS_NOT_BLACKLISTED;
        assertCommandFailure(unBlacklistCommand, model, commandHistory, expectedMessage2);
    }

    @Test
    public void executeUndoRedo_validIndex_success() throws CommandException {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), userPrefs);
        CommandHistory expectedCommandHistory = new CommandHistory();
        BlacklistCommand blacklistCommand = new BlacklistCommand(true, INDEX_FIRST);

        CommandResult result = blacklistCommand.execute(model, commandHistory, userPrefs);
        CommandResult resultExpected = blacklistCommand.execute(expectedModel, expectedCommandHistory, userPrefs);

        expectedModel.undoRecruitBook();
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(new UndoCommand(), model, commandHistory, expectedMessage, expectedModel);

        expectedModel.redoRecruitBook();
        String expectedMessage2 = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(new RedoCommand(), model, commandHistory, expectedMessage2, expectedModel);
    }

}

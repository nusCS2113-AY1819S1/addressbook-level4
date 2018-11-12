package seedu.recruit.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.DESC_ALFA_JOB;
import static seedu.recruit.logic.commands.CommandTestUtil.DESC_BMW_JOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.showJobOfferAtIndex;
import static seedu.recruit.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.getTypicalCompanyBook;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.EditJobDetailsCommand.EditJobOfferDescriptor;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.testutil.EditJobOfferDescriptorBuilder;
import seedu.recruit.testutil.JobOfferBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditJobDetailsCommand.
 */

public class EditJobDetailsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        JobOffer editedJobOffer = new JobOfferBuilder().build();
        EditJobOfferDescriptor descriptor = new EditJobOfferDescriptorBuilder(editedJobOffer).build();
        EditJobDetailsCommand editJobDetailsCommand = new EditJobDetailsCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(editJobDetailsCommand.MESSAGE_EDIT_JOB_OFFER_SUCCESS, editedJobOffer);

        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()),
                new CompanyBook(model.getCompanyBook()), new UserPrefs());
        expectedModel.updateJobOfferInCompanyBook(model.getFilteredCompanyJobList().get(0), editedJobOffer);
        expectedModel.commitRecruitBook();

        assertCommandSuccess(editJobDetailsCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastJobOffer = Index.fromOneBased(model.getFilteredCompanyJobList().size());
        JobOffer lastJobOffer = model.getFilteredCompanyJobList().get(indexLastJobOffer.getZeroBased());

        JobOfferBuilder jobOfferInList = new JobOfferBuilder(lastJobOffer);
        JobOffer editedJobOffer = jobOfferInList.withCompanyName("Bentley Motors Limited").build();

        EditJobOfferDescriptor descriptor = new EditJobOfferDescriptorBuilder()
                .withCompanyName("Bentley Motors Limited").build();
        EditJobDetailsCommand editJobDetailsCommand = new EditJobDetailsCommand(indexLastJobOffer, descriptor);

        String expectedMessage = String.format(editJobDetailsCommand.MESSAGE_EDIT_JOB_OFFER_SUCCESS, editedJobOffer);

        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), model.getCompanyBook(),
                new UserPrefs());
        expectedModel.updateJobOfferInCompanyBook(lastJobOffer, editedJobOffer);
        expectedModel.commitRecruitBook();

        assertCommandSuccess(editJobDetailsCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditJobDetailsCommand editJobDetailsCommand = new EditJobDetailsCommand(INDEX_FIRST,
                new EditJobOfferDescriptor());
        JobOffer editedJobOffer = model.getFilteredCompanyJobList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditJobDetailsCommand.MESSAGE_EDIT_JOB_OFFER_SUCCESS, editedJobOffer);

        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), model.getCompanyBook(),
                new UserPrefs());
        expectedModel.commitRecruitBook();

        assertCommandSuccess(editJobDetailsCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Ignore
    @Test
    public void execute_filteredList_success() throws ParseException {
        showJobOfferAtIndex(model, INDEX_FIRST);

        JobOffer jobOfferInFilteredList = model.getFilteredCompanyJobList().get(INDEX_FIRST.getZeroBased());
        JobOffer editedJobOffer = new JobOfferBuilder(jobOfferInFilteredList).withCompanyName(
                "Bentley Motors Limited").build();
        EditJobDetailsCommand editJobDetailsCommand = new EditJobDetailsCommand(INDEX_FIRST,
                new EditJobOfferDescriptorBuilder().withCompanyName("Bentley Motors Limited").build());

        String expectedMessage = String.format(editJobDetailsCommand.MESSAGE_EDIT_JOB_OFFER_SUCCESS, editedJobOffer);

        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()),
                new CompanyBook(model.getCompanyBook()), new UserPrefs());
        expectedModel.updateJobOfferInCompanyBook(model.getFilteredCompanyJobList().get(0), editedJobOffer);
        expectedModel.commitRecruitBook();

        assertCommandSuccess(editJobDetailsCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateJobOfferUnfilteredList_failure() {
        JobOffer firstJobOffer = model.getFilteredCompanyJobList().get(INDEX_FIRST.getZeroBased());
        EditJobOfferDescriptor descriptor = new EditJobOfferDescriptorBuilder(firstJobOffer).build();
        EditJobDetailsCommand editJobDetailsCommand = new EditJobDetailsCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editJobDetailsCommand, model, commandHistory,
                editJobDetailsCommand.MESSAGE_DUPLICATE_JOB_OFFER);
    }

    @Test
    public void execute_duplicateJobOfferFilteredList_failure() throws ParseException {
        showPersonAtIndex(model, INDEX_FIRST);

        // edit job offer in filtered list into a duplicate in recruit book
        JobOffer jobOfferInList = model.getCompanyBook().getCompanyJobList().get(INDEX_SECOND.getZeroBased());
        EditJobDetailsCommand editJobDetailsCommand = new EditJobDetailsCommand(INDEX_FIRST,
                new EditJobOfferDescriptorBuilder(jobOfferInList).build());

        assertCommandFailure(editJobDetailsCommand, model, commandHistory,
                editJobDetailsCommand.MESSAGE_DUPLICATE_JOB_OFFER);
    }

    @Test
    public void execute_invalidJobOfferIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyJobList().size() + 1);
        EditJobOfferDescriptor descriptor = new EditJobOfferDescriptorBuilder().withCompanyName(VALID_NAME_BMW).build();
        EditJobDetailsCommand editJobDetailsCommand = new EditJobDetailsCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editJobDetailsCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of recruit book
     */
    @Ignore
    @Test
    public void execute_invalidJobOfferIndexFilteredList_failure() throws ParseException {
        showJobOfferAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of recruit book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCompanyBook().getCompanyJobList().size());

        EditJobDetailsCommand editJobDetailsCommand = new EditJobDetailsCommand(outOfBoundIndex,
                new EditJobOfferDescriptorBuilder().withCompanyName(VALID_NAME_BMW).build());

        assertCommandFailure(editJobDetailsCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        JobOffer editedJobOffer = new JobOfferBuilder().build();
        JobOffer jobOfferToEdit = model.getFilteredCompanyJobList().get(INDEX_FIRST.getZeroBased());
        EditJobOfferDescriptor descriptor = new EditJobOfferDescriptorBuilder(editedJobOffer).build();
        EditJobDetailsCommand editJobDetailsCommand = new EditJobDetailsCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()),
                new CompanyBook(model.getCompanyBook()), new UserPrefs());
        expectedModel.updateJobOfferInCompanyBook(jobOfferToEdit, editedJobOffer);
        expectedModel.commitRecruitBook();

        // edit -> first job offer edited
        editJobDetailsCommand.execute(model, commandHistory, userPrefs);

        // undo -> reverts Recruitbook back to previous state and filtered company job list to show all job offers
        expectedModel.undoRecruitBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first job offer edited again
        expectedModel.redoRecruitBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyJobList().size() + 1);
        EditJobOfferDescriptor descriptor = new EditJobOfferDescriptorBuilder().withCompanyName(VALID_NAME_BMW).build();
        EditJobDetailsCommand editJobDetailsCommand = new EditJobDetailsCommand(outOfBoundIndex, descriptor);

        // execution failed -> recruit book state not added into model
        assertCommandFailure(editJobDetailsCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);

        // single recruit book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Candidate} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited job offer in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the job offer object regardless of
     * indexing.
     */
    @Ignore
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        JobOffer editedJobOffer = new JobOfferBuilder().build();
        EditJobOfferDescriptor descriptor = new EditJobOfferDescriptorBuilder(editedJobOffer).build();
        EditJobDetailsCommand editJobDetailsCommand = new EditJobDetailsCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()),
                new CompanyBook(model.getCompanyBook()), new UserPrefs());

        showJobOfferAtIndex(model, INDEX_SECOND);
        JobOffer jobOfferToEdit = model.getFilteredCompanyJobList().get(INDEX_FIRST.getZeroBased());
        expectedModel.updateJobOfferInCompanyBook(jobOfferToEdit, editedJobOffer);
        expectedModel.commitRecruitBook();

        // edit -> edits second job offer in unfiltered job offer list / first job offer in filtered job offer list
        editJobDetailsCommand.execute(model, commandHistory, userPrefs);

        // undo -> reverts recruitbook back to previous state and filtered job offer list to show all job offers
        expectedModel.undoRecruitBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredCompanyJobList().get(INDEX_FIRST.getZeroBased()), jobOfferToEdit);
        // redo -> edits same second job offer in unfiltered job offer list
        expectedModel.redoRecruitBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditJobDetailsCommand standardCommand = new EditJobDetailsCommand(INDEX_FIRST, DESC_ALFA_JOB);

        // same values -> returns true
        EditJobOfferDescriptor copyDescriptor = new EditJobOfferDescriptor(DESC_ALFA_JOB);
        EditJobDetailsCommand commandWithSameValues = new EditJobDetailsCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCandidateBookCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditJobDetailsCommand(INDEX_SECOND, DESC_ALFA_JOB)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditJobDetailsCommand(INDEX_FIRST, DESC_BMW_JOB)));
    }

}

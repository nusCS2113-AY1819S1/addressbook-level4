package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCandidateCommand.EditPersonDescriptor;
import seedu.address.model.CandidateBook;
import seedu.address.model.JobBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.candidate.Candidate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditCandidateCommand.
 */
@Ignore
public class EditCandidateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new JobBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Candidate editedCandidate = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedCandidate).build();
        EditCandidateCommand EditCandidateCommand = new EditCandidateCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCandidateCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedCandidate);

        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), model.getJobBook(),
                new UserPrefs());
        expectedModel.updateCandidate(model.getFilteredCandidateList().get(0), editedCandidate);
        expectedModel.commitCandidateBook();

        assertCommandSuccess(EditCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredCandidateList().size());
        Candidate lastCandidate = model.getFilteredCandidateList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastCandidate);
        Candidate editedCandidate = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCandidateCommand EditCandidateCommand = new EditCandidateCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCandidateCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedCandidate);

        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), model.getJobBook(),
                new UserPrefs());
        expectedModel.updateCandidate(lastCandidate, editedCandidate);
        expectedModel.commitCandidateBook();

        assertCommandSuccess(EditCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCandidateCommand EditCandidateCommand = new EditCandidateCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptor());
        Candidate editedCandidate = model.getFilteredCandidateList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCandidateCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedCandidate);

        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), model.getJobBook(),
                new UserPrefs());
        expectedModel.commitCandidateBook();

        assertCommandSuccess(EditCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Candidate candidateInFilteredList = model.getFilteredCandidateList().get(INDEX_FIRST_PERSON.getZeroBased());
        Candidate editedCandidate = new PersonBuilder(candidateInFilteredList).withName(VALID_NAME_BOB).build();
        EditCandidateCommand EditCandidateCommand = new EditCandidateCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCandidateCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedCandidate);

        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), model.getJobBook(),
                new UserPrefs());
        expectedModel.updateCandidate(model.getFilteredCandidateList().get(0), editedCandidate);
        expectedModel.commitCandidateBook();

        assertCommandSuccess(EditCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Candidate firstCandidate = model.getFilteredCandidateList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstCandidate).build();
        EditCandidateCommand EditCandidateCommand = new EditCandidateCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(EditCandidateCommand, model, commandHistory,
                EditCandidateCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit candidate in filtered list into a duplicate in address book
        Candidate candidateInList = model.getCandidateBook().getCandidatelist().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCandidateCommand EditCandidateCommand = new EditCandidateCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(candidateInList).build());

        assertCommandFailure(EditCandidateCommand, model, commandHistory,
                EditCandidateCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCandidateList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCandidateCommand EditCandidateCommand = new EditCandidateCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(EditCandidateCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCandidateBook().getCandidatelist().size());

        EditCandidateCommand EditCandidateCommand = new EditCandidateCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(EditCandidateCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Candidate editedCandidate = new PersonBuilder().build();
        Candidate candidateToEdit = model.getFilteredCandidateList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedCandidate).build();
        EditCandidateCommand EditCandidateCommand = new EditCandidateCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), new JobBook(),
                new UserPrefs());
        expectedModel.updateCandidate(candidateToEdit, editedCandidate);
        expectedModel.commitCandidateBook();

        // edit -> first candidate edited
        EditCandidateCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered candidate list to show all persons
        expectedModel.undoCandidateBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first candidate edited again
        expectedModel.redoCandidateBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCandidateList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCandidateCommand EditCandidateCommand = new EditCandidateCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(EditCandidateCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Candidate} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited candidate in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the candidate object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Candidate editedCandidate = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedCandidate).build();
        EditCandidateCommand EditCandidateCommand = new EditCandidateCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), new JobBook(),
                new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Candidate candidateToEdit = model.getFilteredCandidateList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.updateCandidate(candidateToEdit, editedCandidate);
        expectedModel.commitCandidateBook();

        // edit -> edits second candidate in unfiltered candidate list / first candidate in filtered candidate list
        EditCandidateCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered candidate list to show all persons
        expectedModel.undoCandidateBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredCandidateList().get(INDEX_FIRST_PERSON.getZeroBased()), candidateToEdit);
        // redo -> edits same second candidate in unfiltered candidate list
        expectedModel.redoCandidateBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCandidateCommand standardCommand = new EditCandidateCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCandidateCommand commandWithSameValues = new EditCandidateCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCandidateBookCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCandidateCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCandidateCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}

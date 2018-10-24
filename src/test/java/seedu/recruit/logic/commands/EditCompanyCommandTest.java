package seedu.recruit.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.DESC_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.DESC_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.EditCompanyCommand.EditCompanyDescriptor;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.company.Company;
import seedu.recruit.testutil.CompanyBuilder;
import seedu.recruit.testutil.EditCompanyDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCandidateBookCommand and RedoCandidateBookCommand)
 * and unit tests for EditCompanyCommand.
 */
@Ignore
public class EditCompanyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Company editedCompany = new CompanyBuilder().build();
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(editedCompany).build();
        EditCompanyCommand editCompanyCommand = new EditCompanyCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(editCompanyCommand.MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), model.getCompanyBook(),
                new UserPrefs());
        expectedModel.updateCompany(model.getFilteredCompanyList().get(0), editedCompany);
        expectedModel.commitCompanyBook();

        assertCommandSuccess(editCompanyCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredCompanyList().size());
        Company lastCompany = model.getFilteredCompanyList().get(indexLastPerson.getZeroBased());

        CompanyBuilder companyInList = new CompanyBuilder(lastCompany);
        Company editedCompany = companyInList.withCompanyName(VALID_NAME_ALFA).withPhone(VALID_PHONE_ALFA)
                .withEmail(VALID_EMAIL_ALFA).build();

        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder().withCompanyName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_ALFA).build();
        EditCompanyCommand editCompanyCommand = new EditCompanyCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(editCompanyCommand.MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), model.getCompanyBook(),
                new UserPrefs());
        expectedModel.updateCompany(lastCompany, editedCompany);
        expectedModel.commitCompanyBook();

        assertCommandSuccess(editCompanyCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCompanyCommand editCompanyCommand = new EditCompanyCommand(INDEX_FIRST,
                new EditCompanyDescriptor());
        Company editedCompany = model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(editCompanyCommand.MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), model.getCompanyBook(),
                new UserPrefs());
        expectedModel.commitCompanyBook();

        assertCommandSuccess(editCompanyCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Company companyInFilteredList = model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased());
        Company editedCompany = new CompanyBuilder(companyInFilteredList).withCompanyName(VALID_NAME_ALFA).build();
        EditCompanyCommand editCompanyCommand = new EditCompanyCommand(INDEX_FIRST,
                new EditCompanyDescriptorBuilder().withCompanyName(VALID_NAME_ALFA).build());

        String expectedMessage = String.format(editCompanyCommand.MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), model.getCompanyBook(),
                new UserPrefs());
        expectedModel.updateCompany(model.getFilteredCompanyList().get(0), editedCompany);
        expectedModel.commitCompanyBook();

        assertCommandSuccess(editCompanyCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCompanyUnfilteredList_failure() {
        Company firstCompany = model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased());
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(firstCompany).build();
        EditCompanyCommand editCompanyCommand = new EditCompanyCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCompanyCommand, model, commandHistory,
                editCompanyCommand.MESSAGE_DUPLICATE_COMPANY);
    }

    @Test
    public void execute_duplicateCompanyFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);

        // edit company in filtered list into a duplicate in company book
        Company companyInList = model.getCompanyBook().getCompanyList().get(INDEX_SECOND.getZeroBased());
        EditCompanyCommand editCompanyCommand = new EditCompanyCommand(INDEX_FIRST,
                new EditCompanyDescriptorBuilder(companyInList).build());

        assertCommandFailure(editCompanyCommand, model, commandHistory,
                editCompanyCommand.MESSAGE_DUPLICATE_COMPANY);
    }

    @Test
    public void execute_invalidCompanyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder().withCompanyName(VALID_NAME_ALFA).build();
        EditCompanyCommand editCompanyCommand = new EditCompanyCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCompanyCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of company book
     */
    @Test
    public void execute_invalidCompanyIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of company book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCompanyBook().getCompanyList().size());

        EditCompanyCommand editCompanyCommand = new EditCompanyCommand(outOfBoundIndex,
                new EditCompanyDescriptorBuilder().withCompanyName(VALID_NAME_ALFA).build());

        assertCommandFailure(editCompanyCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Company editedCompany = new CompanyBuilder().build();
        Company companyToEdit = model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased());
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(editedCompany).build();
        EditCompanyCommand editCompanyCommand = new EditCompanyCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), new CompanyBook(),
                new UserPrefs());
        expectedModel.updateCompany(companyToEdit, editedCompany);
        expectedModel.commitCompanyBook();

        // edit -> first company edited
        editCompanyCommand.execute(model, commandHistory);

        // undo -> reverts Companybook back to previous state and filtered company list to show all companies
        expectedModel.undoCompanyBook();
        assertCommandSuccess(new UndoCandidateBookCommand(), model, commandHistory,
                UndoCandidateBookCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first company edited again
        expectedModel.redoCompanyBook();
        assertCommandSuccess(new RedoCandidateBookCommand(), model, commandHistory,
                RedoCandidateBookCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder().withCompanyName(VALID_NAME_ALFA).build();
        EditCompanyCommand editCompanyCommand = new EditCompanyCommand(outOfBoundIndex, descriptor);

        // execution failed -> company book state not added into model
        assertCommandFailure(editCompanyCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);

        // single company book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCandidateBookCommand(), model, commandHistory,
                UndoCandidateBookCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCandidateBookCommand(), model, commandHistory,
                RedoCandidateBookCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Company} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited company in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCandidateBookCommand} edits the company object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameCompanyEdited() throws Exception {
        Company editedCompany = new CompanyBuilder().build();
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(editedCompany).build();
        EditCompanyCommand editCompanyCommand = new EditCompanyCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new CandidateBook(model.getCandidateBook()), new CompanyBook(),
                new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND);
        Company companyToEdit = model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased());
        expectedModel.updateCompany(companyToEdit, editedCompany);
        expectedModel.commitCompanyBook();

        // edit -> edits second company in unfiltered company list / first company in filtered company list
        editCompanyCommand.execute(model, commandHistory);

        // undo -> reverts companybook back to previous state and filtered company list to show all persons
        expectedModel.undoCompanyBook();
        assertCommandSuccess(new UndoCandidateBookCommand(), model, commandHistory,
                UndoCandidateBookCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased()), companyToEdit);
        // redo -> edits same second company in unfiltered company list
        expectedModel.redoCompanyBook();
        assertCommandSuccess(new RedoCandidateBookCommand(), model, commandHistory,
                RedoCandidateBookCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCompanyCommand standardCommand = new EditCompanyCommand(INDEX_FIRST, DESC_ALFA);

        // same values -> returns true
        EditCompanyDescriptor copyDescriptor = new EditCompanyDescriptor(DESC_ALFA);
        EditCompanyCommand commandWithSameValues = new EditCompanyCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCompanyBookCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCompanyCommand(INDEX_SECOND, DESC_ALFA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCompanyCommand(INDEX_FIRST, DESC_BMW)));
    }

}

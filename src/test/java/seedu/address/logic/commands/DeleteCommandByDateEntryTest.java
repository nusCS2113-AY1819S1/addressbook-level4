package seedu.address.logic.commands;

import org.junit.Assert;
import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.record.Date;
import seedu.address.model.record.Record;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalDates;
import seedu.address.testutil.TypicalIndexes;

import javax.lang.model.element.TypeParameterElement;
import seedu.planner.logic.commands.CommandTestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECORD;
import static seedu.address.testutil.TypicalRecords.getTypicalAddressBook;

public class DeleteCommandByDateEntryTest {
    private Model model = new ModelManager(getTypical, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validDateUnfilteredList_success() {
        List<Record> recordsToDelete = listAllRecordToDelete(model, TypicalDates.DATE_FIRST_INDEX_DATE);

        DeleteCommandByDateEntry deleteCommandByDateEntry = new DeleteCommandByDateEntry(TypicalDates.DATE_FIRST_INDEX_DATE);

        String expectedMessage = String.format(DeleteCommandByDateEntry.MESSAGE_DELETE_RECORD_SUCCESS, TypicalDates.DATE_FIRST_INDEX_DATE);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteListRecord(recordsToDelete);
        expectedModel.commitAddressBook();
        showNoRecord(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteCommandByDateEntry, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException(){
        List<Record> recordsToDelete = listAllRecordToDelete(model, TypicalDates.DATE_FIRST_INDEX_DATE);

        for (int i = 0; i < recordsToDelete.size() - 1; i++){
            //ensure that only one record exists accordingly to the index
            List<Record> records = filteredRecordList(model);

            //TODO: you have to create another method almost same as (CommandTestUtils.showRecordAtIndex)
            int targetIndexInt = records.indexOf(recordsToDelete.get(i));

            CommandTestUtil.showRecordAtIndex(model, targetIndexInt);

            Index outOfBoundIndex = TypicalIndexes.INDEX_LAST_RECORD;
            //ensure that outOfBoundIndex is still in bound of address book list
            assertTrue(outOfBoundIndex.getOneBased() < model.getAddressBook().getRecordList().size());

            DeleteCommandByDateEntry deleteCommandByDateEntry = new DeleteCommandByDateEntry
                                                                (records.get(outOfBoundIndex.getZeroBased()).getDate());

            CommandTestUtil.assertCommandFailure(
                    deleteCommandByDateEntry, model, commandHistory, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_DATE);
        }
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        List<Record> recordsToDelete = listAllRecordToDelete(model, TypicalDates.DATE_FIRST_INDEX_DATE);

        DeleteCommandByDateEntry deleteCommandByDateEntry = new DeleteCommandByDateEntry(TypicalDates.DATE_FIRST_INDEX_DATE);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteListRecord(recordsToDelete);
        expectedModel.commitAddressBook();

        //delete the Records have required date
        deleteCommandByDateEntry.execute(model, commandHistory);

        //undo -> reverts addressBook back to the prebious state and filtered record list to show all records
        expectedModel.undoAddressBook();
        CommandTestUtil.assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> same first record deleted again
        expectedModel.redoAddressBook();
        CommandTestUtil.assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure(){
        Date outOfBoundIndexDate = TypicalDates.DATE_LAST_INDEX_DATE;
        DeleteCommandByDateEntry deleteCommandByDateEntry = new DeleteCommandByDateEntry(outOfBoundIndexDate);

        //execution failed -> address book state not added into model
        CommandTestUtil.assertCommandFailure(deleteCommandByDateEntry, model, commandHistory, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_DATE);

        //single address book state in model -> undoCommand and redoCommand fail
        CommandTestUtil.assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        CommandTestUtil.assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Record} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted record in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the record object regardless of indexing.
     */

    @Test
    public void executeUndoRedo_validIndexFilteredList_sameRecordDeleted() throws Exception {
        DeleteCommandByDateEntry deleteCommandByDateEntry = new DeleteCommandByDateEntry(TypicalDates.DATE_FIRST_INDEX_DATE);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<Record> recordsToDelete = listAllRecordToDelete(model, TypicalDates.DATE_FIRST_INDEX_DATE);

        for (int i = 0; i < recordsToDelete.size() - 1; i++) {
            //ensure that only one record exists accordingly to the index
            List<Record> records = filteredRecordList(model);

            //TODO: you have to create another method almost same as (CommandTestUtils.showRecordAtIndex)
            int targetIndexInt = records.indexOf(recordsToDelete.get(i));

            CommandTestUtil.showRecordAtIndex(model, targetIndexInt);

            Record recordToDelete = records.get(targetIndexInt);

            expectedModel.deleteRecord(recordToDelete);
            expectedModel.commitAddressBook();
        }
            // delete -> deletes second record in unfiltered record list / first record in filtered record list
            deleteCommandByDateEntry.execute(model, commandHistory);

            // undo -> reverts address book back to previous state and filtered record list to show all records
            expectedModel.undoAddressBook();
            assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

            assertNotEquals(recordsToDelete, listAllRecordToDelete(model, TypicalDates.DATE_FIRST_INDEX_DATE));

            // redo -> deletes same second record in unfiltered record list
            expectedModel.redoAddressBook();
            assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommandByDateEntry deleteCommandByFirstDateEntry =
                new DeleteCommandByDateEntry(TypicalDates.DATE_FIRST_INDEX_DATE);
        DeleteCommandByDateEntry deleteCommandByLastDateEntry =
                new DeleteCommandByDateEntry(TypicalDates.DATE_LAST_INDEX_DATE);

        // same object -> returns true
        Assert.assertTrue(
                deleteCommandByFirstDateEntry.equals(deleteCommandByFirstDateEntry));

        // same values -> returns true
        DeleteCommandByDateEntry deleteCommandByFirstDateEntryCopy =
                new DeleteCommandByDateEntry(TypicalDates.DATE_FIRST_INDEX_DATE);

        Assert.assertTrue(
                deleteCommandByFirstDateEntry.equals(deleteCommandByFirstDateEntryCopy));

        // different types -> returns false
        assertFalse(
                deleteCommandByFirstDateEntry.equals(TypicalDates.DATE_FIRST_INDEX_DATE));

        // null -> returns false
        assertFalse(deleteCommandByFirstDateEntry.equals(null));

        // different record -> returns false
        assertFalse(deleteCommandByFirstDateEntry.equals(deleteCommandByLastDateEntry));
    }


    public List<Record> filteredRecordList(Model model){
        return model.getFilteredRecordList();
    }

    public List<Record> listAllRecordToDelete (Model model, Date dateToDelete){
        List<Record> records = filteredRecordList(model);
        List<Record> recordsToDelete = new ArrayList<>();
        for (Record recordToDelete : records){
            if (dateToDelete == recordToDelete.getDate()){
                recordsToDelete.add(recordToDelete);
            }
        }
        return recordsToDelete;
    }
    public void showNoRecord (Model model){
        model.updateFilteredRecordList(p -> false);
        assertTrue (model.getFilteredRecordList().isEmpty());
    }
}

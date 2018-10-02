package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.record.Date;
import seedu.address.model.record.Record;
import seedu.address.testutil.TypicalDates;
import seedu.address.testutil.TypicalIndexes;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static seedu.address.testutil.TypicalRecords.getTypicalAddressBook;

public class DeleteCommandByDateEntryTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validDateUnfilteredList_success() {
        List<Record> recordsToDelete = listAllDateRecord(model, TypicalDates.DATE_FIRST_INDEX_DATE);

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
        List<Record> recordsToDelete = listAllDateRecord(model, TypicalDates.DATE_FIRST_INDEX_DATE);
        for (int i = 0; i < recordsToDelete.size() - 1; i++){
            List<Record> records = filteredRecordList(model);
            //TODO: you have to create another method almost same as (CommandTestUtils.showRecordAtIndex)
            Index targetIndex = new Index(records.indexOf(recordsToDelete.get(i)));
            CommandTestUtil.showRecordAtIndex(model, targetIndex);

            Index outOfBoundIndex = TypicalIndexes.INDEX_LAST_RECORD;
            //ensure that outOfBoundIndex is still in bound of address book list
            assertTrue(outOfBoundIndex.getOneBased() < model.getAddressBook().getRecordList().size());

            DeleteCommandByDateEntry deleteCommandByDateEntry = new DeleteCommandByDateEntry
                                                                (records.get(outOfBoundIndex.getZeroBased()).getDate());
            CommandTestUtil.assertCommandFailure(deleteCommandByDateEntry, model, commandHistory, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_DATE);
        }
    }


    public List<Record> filteredRecordList(Model model){
        return model.getFilteredRecordList();
    }

    public List<Record> listAllDateRecord (Model model, Date dateToDelete){
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

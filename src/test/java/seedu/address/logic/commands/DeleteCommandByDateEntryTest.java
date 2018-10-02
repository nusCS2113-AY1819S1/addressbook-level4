package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.record.Date;
import seedu.address.model.record.Record;
import seedu.address.testutil.TypicalDates;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static seedu.address.testutil.TypicalRecords.getTypicalAddressBook;

public class DeleteCommandByDateEntryTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validDateUnfilteredList_success() {
        Date dateToDelete = TypicalDates.DATE_FIRST_INDEX_DATE;
        List<Record> records = model.getFilteredRecordList();

        List<Record> recordsToDelete = new ArrayList<>();
        for (Record recordToDelete : records){
            if (dateToDelete == recordToDelete.getDate()){
                recordsToDelete.add(recordToDelete);
                recordsToDelete.
            }
        }
        DeleteCommandByDateEntry deleteCommandByDateEntry = new DeleteCommandByDateEntry(dateToDelete);

        String expectedMessage = String.format(DeleteCommandByDateEntry.MESSAGE_DELETE_RECORD_SUCCESS, dateToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteListRecord(recordsToDelete);
        expectedModel.commitAddressBook();
        showNoRecord(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteCommandByDateEntry, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException(){
        CommandTestUtil.showRecordAtIndex();
    }

    public void showNoRecord (Model model){
        model.updateFilteredRecordList(p -> false);
        assertTrue (model.getFilteredRecordList().isEmpty());
    }
}

package seedu.address.export;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author jitwei98
public class CsvWriterTest {
    //  private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    //  model.getFilteredPersonList();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Path outputFilepath = Paths.get("data", "addressbook.csv");
    private CsvWriter csvWriter = new CsvWriter(model.getFilteredPersonList(), outputFilepath);

    private Path expectedPath = Paths.get("data" , "addressbook.csv");
    // AddressBookStub addressBookStub = new AddressBookStub();
    // CsvWriter csvWriter = new CsvWriter(new AddressBookStub.getPersonList());

    @Test
    public void getOutputFilepath_notNull() {
        assertNotNull(csvWriter.getOutputFilepath());
    }

    @Test
    public void getOutputFilepath_equalsDefault() {
        assertEquals(csvWriter.getOutputFilepath(), expectedPath);
    }

    @Test
    public void testWriteToCsv() {
        // TODO: Implement this after being able to read from csv
    }

}

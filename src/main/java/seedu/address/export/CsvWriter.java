package seedu.address.export;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Person;

//@@author jitwei98
/**
 * The writer for CSV file of the app.
 */
public class CsvWriter {
    private static final int INDEX_PERSON_NAME = 0;
    private static final int INDEX_PERSON_PHONE = 2;
    private static final int INDEX_PERSON_ADDRESS = 3;
    private static final int INDEX_PERSON_EMAIL = 4;

    private final String[] header = { "Name", "Phone", "Address", "Email" };
    private final ObservableList<Person> persons;
    private final Path outputFilepath = Paths.get("data" , "pineapple.csv");

    public CsvWriter(ObservableList<Person> persons) {
        requireAllNonNull(persons);

        this.persons = persons;
        // logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        // versionedAddressBook = new VersionedAddressBook(addressBook);
    }

    public void writeToCsv() throws IOException {
        try {

            File file = new File(String.valueOf(outputFilepath));
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);

            writer.writeNext(header);
            // TODO: Format data to List<String[]> and replace the following:

            List<String[]> data = new ArrayList<>();
            // TODO: Refactor the following lines of code
            persons.forEach(person -> {
                String[] details = new String[10];
                details[INDEX_PERSON_NAME] = person.getName().toString();
                details[INDEX_PERSON_PHONE] = person.getPhone().toString();
                details[INDEX_PERSON_ADDRESS] = person.getAddress().toString();
                details[INDEX_PERSON_EMAIL] = person.getEmail().toString();
                data.add(details);
            });

            writer.writeAll(data);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

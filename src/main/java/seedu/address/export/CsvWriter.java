package seedu.address.export;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.opencsv.CSVWriter;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;

//@@author jitwei98
/**
 * The writer for CSV file of the app.
 */
public class CsvWriter {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static final int INDEX_PERSON_NAME = 0;
    private static final int INDEX_PERSON_PHONE = 1;
    private static final int INDEX_PERSON_ADDRESS = 2;
    private static final int INDEX_PERSON_EMAIL = 3;

    private final String[] header = { "Name", "Phone", "Address", "Email" };
    private final ObservableList<Person> listOfPersons;
    // private final Path outputFilepath = Paths.get("data" , "pineapple.csv");
    private final Path outputFilepath;
    private final Person person;

    public CsvWriter(Person person, Path outputFilepath) {
        requireAllNonNull(person, outputFilepath);

        // TODO: Refactor this to a exportFileWriter interface
        if (!FileUtil.isFileExists(outputFilepath)) {
            try {
                FileUtil.createFile(outputFilepath);
            } catch (IOException e) {
                logger.severe("Error creating output file: " + outputFilepath.toString());
            }
        } else {
            logger.fine("Initializing with output file: " + outputFilepath.toString());
        }

        this.person = person;
        this.listOfPersons = null;
        // TODO: Customize outputFilepath based on person name exported
        this.outputFilepath = outputFilepath;
    }

    public CsvWriter(ObservableList<Person> persons, Path outputFilepath) {
        requireAllNonNull(persons);

        // TODO: Refactor this to a exportFileWriter interface
        if (!FileUtil.isFileExists(outputFilepath)) {
            try {
                FileUtil.createFile(outputFilepath);
            } catch (IOException e) {
                logger.severe("Error creating output file: " + outputFilepath.toString());
            }
        } else {
            logger.fine("Initializing with output file: " + outputFilepath.toString());
        }

        this.listOfPersons = persons;
        this.person = null;
        this.outputFilepath = outputFilepath;
    }

    /**
     * Writes to the .csv file as defined in {@code outputFilepath}.
     */
    public void write() throws IOException {
        try {

            File file = new File(String.valueOf(outputFilepath));
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);

            writer.writeNext(header);

            // TODO: think of a better way to minimize LOC
            if (listOfPersons != null) {
                writeMultiplePersons(writer);
            } else if (person != null) {
                writeSinglePerson(writer);
            }

            writer.close();

        } catch (IOException e) {
            throw new IOException();
        }
    }

    /**
     * Writes the {@code listOfPersons} to the csv file.
     */
    private void writeMultiplePersons(CSVWriter writer) {
        List<String[]> data = new ArrayList<>();
        listOfPersons.forEach(person -> {
            String[] personDetails = convertToStringArray(person);
            data.add(personDetails);
        });

        writer.writeAll(data);
    }

    /**
     * Writes the {@code person} to the csv file.
     */
    private void writeSinglePerson(CSVWriter writer) {
        String[] personDetails = convertToStringArray(person);

        writer.writeNext(personDetails);
    }

    // TODO: Implement a writeToCsv() with a specific filepath.

    /**
     * Returns a string array that contains the details of a {@code person}.
     * @param person {@code Person} to be saved to the string array.
     * @return A string array containing the name, phone, address, and email of
     *          the {@code person}.
     */
    private String[] convertToStringArray(Person person) {
        String[] personDetails = new String[header.length];
        personDetails[INDEX_PERSON_NAME] = person.getName().toString();
        personDetails[INDEX_PERSON_PHONE] = person.getPhone().toString();
        personDetails[INDEX_PERSON_ADDRESS] = person.getAddress().toString();
        personDetails[INDEX_PERSON_EMAIL] = person.getEmail().toString();
        return personDetails;
    }

    public Path getOutputFilepath() {
        return outputFilepath;
    }
}

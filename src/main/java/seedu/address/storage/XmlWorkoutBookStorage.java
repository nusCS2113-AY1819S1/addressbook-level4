package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyWorkoutBook;

/**
 * A class to access WorkoutBook data stored as an xml file on the hard disk.
 */
public class XmlWorkoutBookStorage implements WorkoutBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlWorkoutBookStorage.class);

    private Path filePath;

    public XmlWorkoutBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getWorkoutBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWorkoutBook> readWorkoutBook() throws DataConversionException, IOException {
        return readWorkoutBook(filePath);
    }

    /**
     * Similar to {@link #readWorkoutBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWorkoutBook> readWorkoutBook(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("WorkoutBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableWorkoutBook xmlWorkoutBook = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlWorkoutBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveWorkoutBook(ReadOnlyWorkoutBook workoutBook) throws IOException {
        saveWorkoutBook(workoutBook, filePath);
    }

    /**
     * Similar to {@link #saveWorkoutBook(ReadOnlyWorkoutBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveWorkoutBook(ReadOnlyWorkoutBook workoutBook, Path filePath) throws IOException {
        requireNonNull(workoutBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableWorkoutBook(workoutBook));
    }

}

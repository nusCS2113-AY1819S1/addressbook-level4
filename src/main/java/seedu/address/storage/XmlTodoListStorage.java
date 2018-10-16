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
import seedu.address.model.ReadOnlyTodoList;

/**
 * A class to access TodoList data stored as an xml file on the hard disk.
 */
public class XmlTodoListStorage implements TodoListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTodoListStorage.class);

    private Path filePath;

    public XmlTodoListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTodoListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTodoList> readTodoList() throws DataConversionException, IOException {
        return readTodoList(filePath);
    }

    /**
     * Similar to {@link #readTodoList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTodoList> readTodoList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("TodoList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableTodoList xmlTodoList = XmlFileStorage.loadTodoListDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlTodoList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList todoList) throws IOException {
        saveTodoList(todoList, filePath);
    }

    /**
     * Similar to {@link #saveTodoList(ReadOnlyTodoList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTodoList(ReadOnlyTodoList todoList, Path filePath) throws IOException {
        requireNonNull(todoList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveTodoListDataToFile(filePath, new XmlSerializableTodoList(todoList));
    }

}

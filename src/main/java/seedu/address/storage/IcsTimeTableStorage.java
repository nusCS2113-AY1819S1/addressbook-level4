package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.IcsUtil;
import seedu.address.model.person.TimeTable;

/**
 * A class to access TimeTable stored in the hard disk as an .ics file
 */
public class IcsTimeTableStorage implements TimeTableStorage {

    private Path filePath;

    public IcsTimeTableStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getTimeTableFilePath() {
        return filePath;
    }

    @Override
    public Optional<TimeTable> readTimeTable() throws DataConversionException {
        return readTimeTable(filePath);
    }

    /**
     * Similar to {@link #readTimeTable()}
     * @param timeTableFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<TimeTable> readTimeTable(Path timeTableFilePath) throws DataConversionException {

        requireNonNull(timeTableFilePath);

        try {
            return IcsUtil.getInstance().readIcsFile(timeTableFilePath);
        } catch (DataConversionException e) {
            throw new DataConversionException(e);
        }
    }

    @Override
    public void saveTimeTable(TimeTable timeTable) throws IOException {
        try {
            IcsUtil.getInstance().saveIcsFile(timeTable, filePath);
        } catch (FileNotFoundException | DataConversionException e) {
            throw new IOException();
        }
    }

}

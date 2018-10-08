package seedu.recruit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.recruit.commons.exceptions.DataConversionException;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.ReadOnlyCompanyBook;

/**
 * Represents a storage for {@link CompanyBook}.
 */
public interface CompanyBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCompanyBookFilePath();

    /**
     * Returns CompanyBook data as a {@link ReadOnlyCompanyBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCompanyBook> readCompanyBook() throws DataConversionException, IOException;

    /**
     * @see #getCompanyBookFilePath()
     */
    Optional<ReadOnlyCompanyBook> readCompanyBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCompanyBook} to the storage.
     * @param companyBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCompanyBook(ReadOnlyCompanyBook companyBook) throws IOException;

    /**
     * @see #saveCompanyBook(ReadOnlyCompanyBook)
     */
    void saveCompanyBook(ReadOnlyCompanyBook companyBook, Path filePath) throws IOException;

}

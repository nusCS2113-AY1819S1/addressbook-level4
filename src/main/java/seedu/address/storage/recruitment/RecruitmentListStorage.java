package seedu.address.storage.recruitment;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.recruitment.ReadOnlyRecruitmentList;

/**
 * Represents a storage for {@link Recruitment}.
 */
public interface RecruitmentListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRecruitmentListFilePath();

    /**
     * Returns RecruitmentList data as a {@link ReadOnlyRecruitmentList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRecruitmentList> readRecruitmentList() throws DataConversionException, IOException;

    /**
     * @see #getRecruitmentListFilePath()
     */
    Optional<ReadOnlyRecruitmentList> readRecruitmentList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRecruitmentList} to the storage.
     * @param recruitmentList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRecruitmentList(ReadOnlyRecruitmentList recruitmentList) throws IOException;

    /**
     * @see #saveRecruitmentList(ReadOnlyRecruitmentList)
     */
    void saveRecruitmentList(ReadOnlyRecruitmentList recruitmentList, Path filePath) throws IOException;

}

package seedu.address.storage.recruitment;

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
import seedu.address.model.recruitment.ReadOnlyRecruitmentList;

/**
 * A class to access RecruitmentList data stored as an xml file on the hard disk.
 */
public class XmlRecruitmentListStorage implements RecruitmentListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlRecruitmentListStorage.class);

    private Path filePath;

    public XmlRecruitmentListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getRecruitmentListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRecruitmentList> readRecruitmentList() throws DataConversionException, IOException {
        return readRecruitmentList(filePath);
    }

    /**
     * Similar to {@link #readRecruitmentList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRecruitmentList> readRecruitmentList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("RecruitmentList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableRecruitmentList
                xmlRecruitmentList = XmlRecruitmentFileStorage.loadDataFromSaveRecruitmentListFile(filePath);
        try {
            return Optional.of(xmlRecruitmentList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRecruitmentList(ReadOnlyRecruitmentList recruitmentList) throws IOException {
        saveRecruitmentList(recruitmentList, filePath);
    }

    /**
     * Similar to {@link #saveRecruitmentList(ReadOnlyRecruitmentList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveRecruitmentList(ReadOnlyRecruitmentList recruitmentList, Path filePath) throws IOException {
        requireNonNull(recruitmentList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlRecruitmentFileStorage.saveDataToFile(filePath, new XmlSerializableRecruitmentList(recruitmentList));
    }

}

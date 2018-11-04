package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.saleshistory.ReadOnlySalesHistory;
import seedu.address.model.saleshistory.SalesHistory;

/**
 * A class to access and modify {@link SalesHistory} stored on the hard disk in xml form.
 */
public class XmlSalesHistoryStorage implements SalesHistoryStorage {
    private static Logger logger = LogsCenter.getLogger(XmlSalesHistoryStorage.class);

    private Path filePath;

    public XmlSalesHistoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getSalesHistoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySalesHistory> readSalesHistory() throws DataConversionException, IOException {
        return readSalesHistory(filePath);
    }

    @Override
    public Optional<ReadOnlySalesHistory> readSalesHistory(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Saleshistory file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableSalesHistory xmlSalesHistory = XmlFileStorage.loadSalesHistoryFromFile(filePath);
        try {
            return Optional.of(xmlSalesHistory.toModelType());
        } catch (IllegalValueException e) {
            logger.info("Invalid values found in " + filePath + e.getMessage());
            throw new DataConversionException(e);
        }
    }

    @Override
    public void saveSalesHistory(ReadOnlySalesHistory salesHistory) throws IOException {
        saveSalesHistory(salesHistory, filePath);
    }

    @Override
    public void saveSalesHistory(ReadOnlySalesHistory salesHistory, Path filePath) throws IOException {
        requireAllNonNull(salesHistory, filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveSalesHistoryToFile(filePath, new XmlSerializableSalesHistory(salesHistory));
    }

    @Override
    public void deleteSalesHistory() throws IOException {
        requireNonNull(filePath);
        Files.delete(filePath);
    }
}

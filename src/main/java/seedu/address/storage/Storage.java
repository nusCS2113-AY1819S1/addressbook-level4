package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.ClubBudgetElementsBookChangedEvent;
import seedu.address.commons.events.model.FinalBudgetsBookChangedEvent;
import seedu.address.commons.events.model.LoginBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyClubBudgetElementsBook;
import seedu.address.model.ReadOnlyFinalBudgetBook;
import seedu.address.model.ReadOnlyLoginBook;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends LoginBookStorage, AddressBookStorage, ClubBudgetElementsBookStorage,
        FinalBudgetsBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getLoginBookFilePath();

    @Override
    Path getAddressBookFilePath();

    @Override
    Path getClubBudgetElementsBookFilePath();

    @Override
    Path getFinalBudgetsBookFilePath();

    @Override
    Optional<ReadOnlyLoginBook> readLoginBook() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyClubBudgetElementsBook> readClubBudgetElementsBook() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyFinalBudgetBook> readFinalBudgetsBook() throws DataConversionException, IOException;

    @Override
    void saveLoginBook(ReadOnlyLoginBook loginBook) throws IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    void saveClubBudgetElementsBook(ReadOnlyClubBudgetElementsBook clubBudgetElementsBook) throws IOException;

    @Override
    void saveFinalBudgetsBook(ReadOnlyFinalBudgetBook finalBudgetBook) throws IOException;

    /**
     * Saves the current version of the Login Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleLoginBookChangedEvent(LoginBookChangedEvent abce);

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    /**
     * Saves the current version of the Club Budget Elements Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleClubBudgetElementsBookChangedEvent(ClubBudgetElementsBookChangedEvent cbebce);

    /**
     * Saves the current version of the Final Budgets Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleFinalBudgetsBookChangedEvent(FinalBudgetsBookChangedEvent fbbce);

}

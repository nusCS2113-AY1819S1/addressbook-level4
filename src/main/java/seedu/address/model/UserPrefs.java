package seedu.address.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.util.FileUtil;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private Path addressBookFilePath;
    private Path addressBookBackupFilePath;
    private String addressBookGistId;

    private Path expenseBookFilePath;
    private Path expenseBookBackupFilePath;
    private String expenseBookGistId;

    private Path eventBookFilePath;
    private Path eventBookBackupFilePath;
    private String eventBookGistId;

    private Path taskBookFilePath;
    private Path taskBookBackupFilePath;
    private String taskBookGistId;


    public UserPrefs() {
        setGuiSettings(1000, 500, 0, 0);
        setAddressBookFilePath(getAddressBookFilePath());
        setAddressBookBackupFilePath(getAddressBookBackupFilePath());

        setExpenseBookFilePath(getExpenseBookFilePath());
        setExpenseBookBackupFilePath(getExpenseBookBackupFilePath());

        setEventBookFilePath(getEventBookFilePath());
        setEventBookBackupFilePath(getEventBookBackupFilePath());

        setTaskBookFilePath(getTaskBookFilePath());
        setTaskBookBackupFilePath(getTaskBookBackupFilePath());
    }

    /**
     * Enum type used to identify specific data book to target
     */
    public enum TargetBook {
        AddressBook,
        EventBook,
        ExpenseBook,
        TaskBook
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath == null ? Paths.get("data" , "addressbook.xml") : addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    public Path getAddressBookBackupFilePath() {
        return addressBookBackupFilePath == null ? Paths.get("data" , "addressbook.bak") : addressBookBackupFilePath;
    }

    public void setAddressBookBackupFilePath(Path addressBookBackupFilePath) {
        this.addressBookBackupFilePath = addressBookBackupFilePath;
    }

    public String getAddressBookGistId() {
        return addressBookGistId;
    }

    public void setAddressBookGistId(String addressBookGistId) {
        this.addressBookGistId = addressBookGistId;
    }

    public String getExpenseBookGistId() {
        return expenseBookGistId;
    }

    public void setExpenseBookGistId(String expenseBookGistId) {
        this.expenseBookGistId = expenseBookGistId;
    }

    public String getEventBookGistId() {
        return eventBookGistId;
    }

    public void setEventBookGistId(String eventBookGistId) {
        this.eventBookGistId = eventBookGistId;
    }

    public String getTaskBookGistId() {
        return taskBookGistId;
    }

    public void setTaskBookGistId(String taskBookGistId) {
        this.taskBookGistId = taskBookGistId;
    }

    /**
     * Helper method to check if any of the Gist Ids for the books data is null
     * @return True if any of the Gist Ids for the books data is null
     */
    public boolean hasNullGistId() {
        return (getAddressBookGistId() == null || getAddressBookGistId().isEmpty())
                || (getEventBookGistId() == null || getEventBookGistId().isEmpty())
                || (getExpenseBookGistId() == null || getExpenseBookGistId().isEmpty())
                || (getTaskBookGistId() == null || getTaskBookGistId().isEmpty());
    }

    /**
     * Helper method to check if any of the backup filepaths do not actually exists
     * @return True if any of the backup file paths do not exists
     */
    public boolean hasNonExistingBackupFile() {
        return (!FileUtil.isFileExists(getAddressBookBackupFilePath()))
                || (!FileUtil.isFileExists(getEventBookBackupFilePath()))
                || (!FileUtil.isFileExists(getExpenseBookBackupFilePath()))
                || (!FileUtil.isFileExists(getTaskBookBackupFilePath()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(addressBookFilePath.toAbsolutePath(), o.addressBookFilePath.toAbsolutePath())
                && Objects.equals(
                        addressBookBackupFilePath.toAbsolutePath(), o.addressBookBackupFilePath.toAbsolutePath())
                && Objects.equals(addressBookGistId, o.addressBookGistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath,
                addressBookBackupFilePath, addressBookGistId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nLocal data backup file location : " + addressBookBackupFilePath);
        sb.append("\nOnline data backup gist id : " + addressBookGistId);
        return sb.toString();
    }

    //=========== Expense =================================================================================

    public Path getExpenseBookFilePath() {
        return expenseBookFilePath == null ? Paths.get("data" , "expensebook.xml") : expenseBookFilePath;
    }

    public void setExpenseBookFilePath(Path expenseBookFilePath) {
        this.expenseBookFilePath = expenseBookFilePath;
    }

    public Path getExpenseBookBackupFilePath() {
        return expenseBookBackupFilePath == null ? Paths.get("data" , "expensebook.bak") : expenseBookBackupFilePath;
    }

    public void setExpenseBookBackupFilePath(Path expenseBookBackupFilePath) {
        this.expenseBookBackupFilePath = expenseBookBackupFilePath;
    }

    //===================Events=====================
    public Path getEventBookFilePath() {
        return eventBookFilePath == null ? Paths.get("data" , "eventbook.xml") : eventBookFilePath;
    }

    public void setEventBookFilePath(Path eventBookFilePath) {
        this.eventBookFilePath = eventBookFilePath;
    }

    public Path getEventBookBackupFilePath() {
        return eventBookBackupFilePath == null ? Paths.get("data" , "eventbook.bak") : eventBookBackupFilePath;
    }

    public void setEventBookBackupFilePath(Path eventBookBackupFilePath) {
        this.eventBookBackupFilePath = eventBookBackupFilePath;
    }
    //=========== Task ====================================================================================

    public Path getTaskBookFilePath() {
        return taskBookFilePath == null ? Paths.get("data" , "taskbook.xml") : taskBookFilePath;
    }

    public void setTaskBookFilePath(Path taskBookFilePath) {
        this.taskBookFilePath = taskBookFilePath;
    }

    public Path getTaskBookBackupFilePath() {
        return taskBookBackupFilePath == null ? Paths.get("data" , "taskbook.bak") : taskBookBackupFilePath;
    }

    public void setTaskBookBackupFilePath(Path taskBookBackupFilePath) {
        this.taskBookBackupFilePath = taskBookBackupFilePath;
    }
}

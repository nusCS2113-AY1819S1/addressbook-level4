package seedu.address.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private Path addressBookFilePath;
    private Path addressBookBackupFilePath;

    private Path expenseBookFilePath = Paths.get("data" , "expensebook.xml");
    private Path expenseBookBackupFilePath = Paths.get("data", "expensebook.bak");

    public UserPrefs() {
        setGuiSettings(500, 500, 0, 0);
        setAddressBookFilePath(getAddressBookFilePath());
        setAddressBookBackupFilePath(getAddressBookBackupFilePath());
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
                && Objects.equals(addressBookFilePath, o.addressBookFilePath)
                && Objects.equals(addressBookBackupFilePath, o.addressBookBackupFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, addressBookBackupFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nLocal data backup file location : " + addressBookBackupFilePath);
        return sb.toString();
    }

    //=========== Expense =================================================================================

    public Path getExpenseBookFilePath() {
        return expenseBookFilePath;
    }

    public Path getExpenseBookBackupFilePath() {
        return expenseBookBackupFilePath;
    }



}

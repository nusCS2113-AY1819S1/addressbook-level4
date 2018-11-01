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
    private Path addressBookFilePath = Paths.get("data" , "addressbook.xml");
    private Path loginBookFilePath = Paths.get("data" , "loginbook.xml");
    private Path clubBudgetElementsBookFilePath = Paths.get("data" , "budgetelementsbook.xml");
    private Path finalBudgetsBookFilePath = Paths.get("data" , "budgetsbook.xml");


    public UserPrefs() {
        setGuiSettings(500, 500, 0, 0);
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

    public Path getLoginBookFilePath() {
        return loginBookFilePath;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public Path getClubBudgetElementsBookFilePath() {
        return clubBudgetElementsBookFilePath;
    }

    public Path getFinalBudgetsBookFilePath() {
        return finalBudgetsBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    public void setClubBudgetElementsBookFilePath(Path clubBudgetElementsBookFilePath) {
        this.clubBudgetElementsBookFilePath = clubBudgetElementsBookFilePath;
    }

    public void setFinalBudgetsBookFilePath(Path finalBudgetsBookFilePath) {
        this.finalBudgetsBookFilePath = finalBudgetsBookFilePath;
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
                && Objects.equals(clubBudgetElementsBookFilePath, o.clubBudgetElementsBookFilePath)
                && Objects.equals(finalBudgetsBookFilePath, o.finalBudgetsBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, clubBudgetElementsBookFilePath,
                finalBudgetsBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nLocal data file location : " + clubBudgetElementsBookFilePath);
        sb.append("\nLocal data file location : " + finalBudgetsBookFilePath);
        return sb.toString();
    }

}

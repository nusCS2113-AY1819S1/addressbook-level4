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
    private Path bookInventoryFilePath = Paths.get("data" , "bookinventory.xml");
    private Path requestListFilePath = Paths.get("data" , "requestlist.xml");
    private Path statisticFilePath = Paths.get("data" , "statisticlist.json");

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

    public Path getBookInventoryFilePath() {
        return bookInventoryFilePath;
    }

    public Path getRequestListFilePath() {
        return requestListFilePath;
    }

    public Path getStatisticFilePath() {
        return statisticFilePath;
    }

    public void setBookInventoryFilePath(Path addressBookFilePath) {
        this.bookInventoryFilePath = addressBookFilePath;
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
                && Objects.equals(bookInventoryFilePath, o.bookInventoryFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, bookInventoryFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + bookInventoryFilePath);
        return sb.toString();
    }

}

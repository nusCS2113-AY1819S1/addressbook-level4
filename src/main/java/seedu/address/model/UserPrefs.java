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
    private Path workoutBookFilePath = Paths.get("data" , "workoutbook.xml");
    private Path trackedDataListFilePath = Paths.get("data" , "trackeddatalist.xml");
    private Path trackedDataFilePath = Paths.get("data", "trackeddata.xml");

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

    public Path getWorkoutBookFilePath() {
        return workoutBookFilePath;
    }

    public void setWorkoutBookFilePath(Path workoutBookFilePath) {
        this.workoutBookFilePath = workoutBookFilePath;
    }

    public Path getTrackedDataListFilePath() {
        return trackedDataListFilePath;
    }

    public void setTrackedDataListFilePath(Path trackedDataListFilePath) {
        this.trackedDataListFilePath = trackedDataListFilePath;
    }

    public Path getTrackedDataFilePath() {
        return trackedDataFilePath;
    }

    public void setTrackedDataFilePath(Path trackedDataFilePath) {
        this.trackedDataFilePath = trackedDataFilePath;
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
                && Objects.equals(workoutBookFilePath, o.workoutBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, workoutBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal workoutbook data file location : " + workoutBookFilePath);
        sb.append("\nLocal tracking data file location : " + trackedDataListFilePath);
        return sb.toString();
    }

}

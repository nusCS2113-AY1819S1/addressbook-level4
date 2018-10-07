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
    private Path candidateBookFilePath = Paths.get("data" , "candidatebook.xml");
    private Path jobBookFilePath = Paths.get("data" , "jobbook.xml");

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

    public Path getCandidateBookFilePath() {
        return candidateBookFilePath;
    }

    public void setCandidateBookFilePath(Path candidateBookFilePath) {
        this.candidateBookFilePath = candidateBookFilePath;
    }

    public Path getJobBookFilePath() {
        return jobBookFilePath;
    }

    public void setJobBookFilePath(Path jobBookFilePath) {
        this.jobBookFilePath = jobBookFilePath;
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
                && Objects.equals(candidateBookFilePath, o.candidateBookFilePath)
                && Objects.equals(jobBookFilePath, o.jobBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, candidateBookFilePath, jobBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal candidatebook file location : " + candidateBookFilePath);
        sb.append("\nLocal jobbook file location : " + jobBookFilePath);
        return sb.toString();
    }

}

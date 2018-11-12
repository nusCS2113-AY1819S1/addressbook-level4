package seedu.planner.ui;

/**
 * This represents any Ui panel that have the ability to be switched out with any UI panel
 */
public interface Switchable {

    /**
     * Hides the current UI
     */
    void hide();

    /**
     * Shows the current UI
     */
    void show();
}

package seedu.planner.ui;

import javafx.scene.Node;
import javafx.scene.control.Tab;

/**
 * This represents a typical tab which can be used to display statistics or summary in the Financial Planner.
 * This tab is generated using JavaFX only and not JavaFXML
 */
public class CustomTab extends Tab {

    public CustomTab(String tabName, Node node) {
        setText(tabName);
        setContent(node);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomTab // instanceof handles nulls
                && getText().equals(((CustomTab) other).getText()));
    }
}

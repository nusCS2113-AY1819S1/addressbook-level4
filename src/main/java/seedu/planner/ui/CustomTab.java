package seedu.planner.ui;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import seedu.planner.model.summary.CategoryStatistic;

/**
 * This represents a typical tab which can be used to display statistics or summary in the Financial Planner.
 * This tab is generated using JavaFX only and not JavaFXML
 */
public class CustomTab extends Tab {

    public CustomTab(ObservableList<CategoryStatistic> toDisplay) {
        setText("Category Breakdown for financial activity");
        CategoryBreakdown categoryBreakdown = new CategoryBreakdown(toDisplay);
        setContent(categoryBreakdown.getRoot());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomTab // instanceof handles nulls
                && getText().equals(((CustomTab) other).getText()));
    }
}

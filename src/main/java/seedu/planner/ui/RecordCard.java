package seedu.planner.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.planner.model.record.Record;

/**
 * An UI component that displays information of a {@code Record}.
 */
public class RecordCard extends UiPart<Region> {

    private static final String FXML = "RecordListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
<<<<<<< HEAD:src/main/java/seedu/address/ui/RecordCard.java
     * @see <a href="https://github.com/se-edu/financialplanner-level4/issues/336">
     *     The issue on FinancialPlanner level 4</a>
=======
     * @see <a href="https://github.com/se-edu/financialplanner-level4/issues/336">The
     * issue on FinancialPlanner level 4</a>
>>>>>>> 936a266304811392cda80acfbf3d1820aac87fed:src/main/java/seedu/planner/ui/RecordCard.java
     */

    public final Record record;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label moneyFlow;
    @FXML
    private FlowPane tags;

    // TODO: Refactor this next time
    public RecordCard(Record record, int displayedIndex) {
        super(FXML);
        this.record = record;
        id.setText(displayedIndex + ". ");
        name.setText(record.getName().fullName);
        date.setText(record.getDate().value);
        moneyFlow.setText(record.getMoneyFlow().value);
        record.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecordCard)) {
            return false;
        }

        // state check
        RecordCard card = (RecordCard) other;
        return id.getText().equals(card.id.getText())
                && record.equals(card.record);
    }
}

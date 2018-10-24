package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.expense.Expense;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class ExpenseCard extends UiPart<Region> {

    private static final String FXML = "ExpenseListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Expense expense;

    @FXML
    private HBox cardPane;
    @FXML
    private Label category;
    @FXML
    private Label date;
    @FXML
    private Label id;
    @FXML
    private Label value;
    @FXML
    private FlowPane tags;

    public ExpenseCard(Expense expense, int displayedIndex) {
        super(FXML);
        this.expense = expense;
        id.setText(displayedIndex + ". ");
        category.setText(expense.getExpenseCategory().expenseCategory);
        date.setText(expense.getExpenseDate().expenseDate);
        value.setText(expense.getExpenseValue().expenseValue);
        expense.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}

package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExpensesPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListExpensesRequestEvent;
import seedu.address.model.expenses.Expenses;

/**
 * Panel containing the list of expenses.
 */
public class ExpensesListPanel extends UiPart<Region> {
    private static final String FXML = "ExpensesListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExpensesListPanel.class);

    @FXML
    private ListView<Expenses> expensesListView;

    public ExpensesListPanel(ObservableList<Expenses> expensesList) {
        super(FXML);
        setConnections(expensesList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Expenses> expensesList) {
        expensesListView.setItems(expensesList);
        expensesListView.setCellFactory(listView -> new ExpensesListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        expensesListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in expenses list panel changed to : '" + newValue + "'");
                        raise(new ExpensesPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            expensesListView.scrollTo(index);
            expensesListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListExpensesRequestEvent(JumpToListExpensesRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code expenses} using a {@code ExpensesCard}.
     */
    class ExpensesListViewCell extends ListCell<Expenses> {
        @Override
        protected void updateItem(Expenses expenses, boolean empty) {
            super.updateItem(expenses, empty);

            if (empty || expenses == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExpensesCard(expenses, getIndex() + 1).getRoot());
            }
        }
    }

}

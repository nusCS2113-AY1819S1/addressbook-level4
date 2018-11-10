package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TransactionListChangedEvent;
import seedu.address.model.transaction.Transaction;

/**
 * Pane containing Transactions
 */
public class TransactionsPanel extends UiPart<Region> {
    private static final String FXML = "TransactionsListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TransactionsPanel.class);

    @javafx.fxml.FXML
    private ListView<Transaction> transactionListView;

    public TransactionsPanel(ObservableList<Transaction> transactions) {
        super(FXML);
        setConnections(transactions);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Transaction> transactionsList) {
        transactionListView.setItems(transactionsList);
        transactionListView.setCellFactory(listView -> new TransactionListViewCell());
        // setEventHandlerForSelectionChangeEvent();
    }

    @Subscribe
    private void handleTransactionListChangedEvent(TransactionListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        System.out.println("TRANSACTION LIST CHANGED!!!");

        // insert
        transactionListView.setItems(event.getData());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Transaction} using a {@code TransactionCard}.
     */
    class TransactionListViewCell extends ListCell<Transaction> {
        @Override
        protected void updateItem(Transaction transaction, boolean empty) {
            super.updateItem(transaction, empty);

            if (empty || transaction == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TransactionCard(transaction, getIndex() + 1).getRoot());
            }
        }
    }
}

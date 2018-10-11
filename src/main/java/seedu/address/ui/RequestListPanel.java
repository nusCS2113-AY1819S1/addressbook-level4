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
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.RequestPanelSelectionChangedEvent;
import seedu.address.model.request.Request;

/**
 * Panel containing the list of persons.
 */
public class RequestListPanel extends UiPart<Region> {
    private static final String FXML = "RequestListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RequestListPanel.class);

    @FXML
    private ListView<Request> requestListView;

    public RequestListPanel(ObservableList<Request> requestList) {
        super(FXML);
        setConnections(requestList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Request> requestList) {
        requestListView.setItems(requestList);
        requestListView.setCellFactory(listView -> new RequestListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        requestListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in book list panel changed to : '" + newValue + "'");
                        raise(new RequestPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code BookCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            requestListView.scrollTo(index);
            requestListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Book} using a {@code BookCard}.
     */
    class RequestListViewCell extends ListCell<Request> {
        @Override
        protected void updateItem(Request request, boolean empty) {
            super.updateItem(request, empty);

            if (empty || request == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RequestCard(request, getIndex() + 1).getRoot());
            }
        }
    }

}

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
import seedu.address.model.todo.Todo;

/**
 * Panel containing the list of todo tasks entered by JitHub users.
 */
public class TodoListPanel extends UiPart<Region> {
    private static final String FXML = "TodoListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TodoListPanel.class);

    @FXML
    private ListView<Todo> todoListView;

    public TodoListPanel(ObservableList<Todo> todoList) {
        super(FXML);
        setConnections(todoList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Todo> todoList) {
        todoListView.setItems(todoList);
        todoListView.setCellFactory(listView -> new todoListViewCell());
    }

    /**
     * Scrolls to the {@code TodoCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            todoListView.scrollTo(index);
            //todoListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Todo} using a {@code todoCard}.
     */
    class todoListViewCell extends ListCell<Todo> {
        @Override
        protected void updateItem(Todo todo, boolean empty) {
            super.updateItem(todo, empty);

            if (empty || todo == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TodoCard(todo, getIndex() + 1).getRoot());
            }
        }
    }

}

package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.note.Note;

/**
 * Panel containing the list of data.
 */
public class NoteListPanel extends UiPart<Region> {
    private static final String FXML = "NoteListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(NoteListPanel.class);

    @FXML
    private ListView<Note> notesListView;

    public NoteListPanel(ObservableList<Note> dataList) {
        super(FXML);
        setConnections(dataList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Note> dataList) {
        notesListView.setItems(dataList);
        notesListView.setCellFactory(listView -> new notesListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        notesListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in list panel changed to : '" + newValue + "'");
                        //raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
//    private void scrollTo(int index) {
//        Platform.runLater(() -> {
//            personListView.scrollTo(index);
//            personListView.getSelectionModel().clearAndSelect(index);
//        });
//    }

//    @Subscribe
//    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
//        logger.info(LogsCenter.getEventHandlingLogMessage(event));
//        scrollTo(event.targetIndex);
//    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class notesListViewCell extends ListCell<Note> {
        @Override
        protected void updateItem(Note note, boolean empty) {
            super.updateItem(note, empty);

            if (empty || note == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new NoteCard(note, getIndex() + 1).getRoot());
            }
        }
    }

}

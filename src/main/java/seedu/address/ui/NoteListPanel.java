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
        notesListView.setCellFactory(listView -> new NotesListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Note} using a {@code NoteCard}.
     */
    class NotesListViewCell extends ListCell<Note> {
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

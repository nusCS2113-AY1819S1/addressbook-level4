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
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.IsMergedPredicate;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class MergedTimetablePanel extends UiPart<Region> {
    private static final String FXML = "MergedTimetablePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MergedTimetablePanel.class);

    @FXML
    private ListView<Person> timetable;

    public MergedTimetablePanel(ObservableList<Person> timetableList) {
        super(FXML);
        timetableList = timetableList.filtered(new IsMergedPredicate());
        setConnections(timetableList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Person> timetableList) {
        timetable.setItems(timetableList);
        timetable.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        timetable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }



    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            timetable.scrollTo(index);
            timetable.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TimetableCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}

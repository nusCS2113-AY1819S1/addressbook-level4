package seedu.recruit.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.commons.events.ui.JumpToListRequestEvent;
import seedu.recruit.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.recruit.model.candidate.Candidate;

/**
 * Panel containing the list of persons.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonDetailsPanel.class);

    @FXML
    private ListView<Candidate> personDetailsView;

    public PersonDetailsPanel(ObservableList<Candidate> candidateList) {
        super(FXML);
        setConnections(candidateList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Candidate> candidateList) {
        personDetailsView.setItems(candidateList);
        personDetailsView.setCellFactory(listView -> new PersonDetailsViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personDetailsView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in candidate details panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            personDetailsView.scrollTo(index);
            personDetailsView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Candidate} using a {@code PersonCard}.
     */
    class PersonDetailsViewCell extends ListCell<Candidate> {
        @Override
        protected void updateItem(Candidate candidate, boolean empty) {
            super.updateItem(candidate, empty);

            if (empty || candidate == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonDetailsCard(candidate, getIndex() + 1).getRoot());
            }
        }
    }

}

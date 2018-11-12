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
import seedu.address.commons.events.ui.JumpToListRecruitmentPostRequestEvent;
import seedu.address.commons.events.ui.RecruitmentPanelSelectionChangedEvent;
import seedu.address.model.recruitment.Recruitment;

/**
 * Panel containing the list of recruitment.
 */
public class RecruitmentListPanel extends UiPart<Region> {
    private static final String FXML = "RecruitmentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecruitmentListPanel.class);

    @FXML
    private ListView<Recruitment> recruitmentListView;

    public RecruitmentListPanel(ObservableList<Recruitment> recruitmentList) {
        super(FXML);
        setConnections(recruitmentList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Recruitment> recruitmentList) {
        recruitmentListView.setItems(recruitmentList);
        recruitmentListView.setCellFactory(listView -> new RecruitmentListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRecruitmentPostRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            recruitmentListView.scrollTo(index);
            recruitmentListView.getSelectionModel().clearAndSelect(index);
        });
    }

    private void setEventHandlerForSelectionChangeEvent() {
        recruitmentListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in recruitment list panel changed to : '" + newValue + "'");
                        raise(new RecruitmentPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code recruitment} using a {@code Recruitment}.
     */
    class RecruitmentListViewCell extends ListCell<Recruitment> {
        @Override
        protected void updateItem(Recruitment recruitment, boolean empty) {
            super.updateItem(recruitment, empty);

            if (empty || recruitment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RecruitmentCard(recruitment, getIndex() + 1).getRoot());
            }
        }
    }

}

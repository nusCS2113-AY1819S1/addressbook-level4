package seedu.planner.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.RecordPanelSelectionChangedEvent;
import seedu.planner.model.record.Record;
//@@author tenvinc
/**
 * This UI component is responsible for displaying detailed information for each record
 */
public class DetailedRecordCard extends UiPart<Region> implements Switchable {

    private static final String FXML = "DetailedRecordCard.fxml";
    private static final String DATE_LABEL = "Date:";
    private static final String MONEYFLOW_LABEL = "Credit(+)/Debit(-):";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private ScrollPane detailedCard;

    @FXML
    private Label dateLabel;

    @FXML
    private Label moneyFlowLabel;

    @FXML
    private Label name;

    @FXML
    private Label date;

    @FXML
    private Label moneyFlow;

    @FXML
    private FlowPane biggerTags;

    public DetailedRecordCard() {
        super(FXML);
        dateLabel.setText(DATE_LABEL);
        moneyFlowLabel.setText(MONEYFLOW_LABEL);
        detailedCard.setVisible(false);
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the detailed record panel with all record information
     * @param record
     */
    private void loadRecordPage(Record record) {
        if (record != null) {
            name.setText(record.getName().toString());
            date.setText(record.getDate().toString());
            moneyFlow.setText(record.getMoneyFlow().toString());
            biggerTags.getChildren().clear();
            record.getTags().forEach(tag -> biggerTags.getChildren().add(new Label(tag.tagName)));
            show();
        } else {
            hide();
        }
    }

    @Override
    public void show() {
        getRoot().toFront();
        detailedCard.setVisible(true);
    }

    @Override
    public void hide() {
        getRoot().toBack();
        detailedCard.setVisible(false);
    }

    /**
     * Handles RecordPanelSelectionChangedEvent passed from MainWindow's delegate function
     * @param event event to be handled
     */
    public void handleRecordPanelSelectionChangedEvent(RecordPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadRecordPage(event.getNewSelection());
    }
}

package seedu.address.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.schedule.Activity;



/**
 * The Schedule Panel of the App.
 */
public class SchedulePanel extends UiPart<Region> {

    private static final String FXML = "SchedulePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private VBox schedulePanel;
    @FXML
    private ScrollPane scrollPane;

    public SchedulePanel(TreeMap<Date, ArrayList<Activity>> schedule) {
        super(FXML);
        loadSchedule(schedule);
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the schedule of the address book.
     */
    public void loadSchedule(TreeMap<Date, ArrayList<Activity>> schedule) {
        schedulePanel.getChildren().clear();
        Label title = new Label();
        title.setText("Schedule");
        title.setPadding(new Insets(5, 5, 5, 5));
        title.setFont(new Font(25));
        schedulePanel.getChildren().add(title);
        VBox contents = new VBox();
        int count = 1;
        for (Date date : schedule.keySet()) {
            FlowPane datePane = new FlowPane();
            datePane.setStyle("-fx-background-color: #eeeff1;");
            datePane.setPadding(new Insets(5, 5, 5, 5));
            Label dateLabel = new Label();
            dateLabel.setStyle("-fx-text-fill:Black;");
            dateLabel.setText(Activity.getDateString(date));
            datePane.getChildren().add(dateLabel);
            contents.getChildren().add(datePane);
            for (Activity activity : schedule.get(date)) {
                Label activityLabel = new Label();
                activityLabel.setText(count + ". " + activity.getActivityName());
                activityLabel.setPadding(new Insets(5, 5, 5, 5));
                contents.getChildren().add(activityLabel);
                count++;
            }
        }
        scrollPane.setContent(contents);
        schedulePanel.getChildren().add(scrollPane);
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "updating schedule"));

        loadSchedule(event.data.getSchedule());

    }

}

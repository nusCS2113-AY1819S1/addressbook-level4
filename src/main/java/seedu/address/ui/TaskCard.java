//@@author XiaoYunhan
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task task;

    //@FXML
    //private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label taskname;
    @FXML
    private Label taskmodule;
    @FXML
    private Label taskdate;
    @FXML
    private Label taskpriority;
    @FXML
    private Label taskstate;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        taskname.setText(task.getName().fullName);
        taskmodule.setText(task.getModule().value);
        taskdate.setText(task.getDate().value);

        if (task.getComplete()) { //set color for task status: red for uncompleted, green for completed
            taskstate.setStyle("-fx-text-fill: #7fce92");
        } else {
            taskstate.setStyle("-fx-text-fill: #c05d61");
        }

        int notification = task.notification();

        // deadline of task has passed or will come within 7 days
        if (notification != 0) {
            //deadline of task has passed
            if (notification == -1) {
                //task is marked as "uncompleted"
                if (!task.getComplete()) {
                    id.setStyle("-fx-text-fill: #c05d61");
                    taskname.setStyle("-fx-text-fill: #c05d61");
                    taskdate.setStyle("-fx-text-fill: #c05d61");
                    taskmodule.setStyle("-fx-text-fill: #c05d61");
                    taskpriority.setStyle("-fx-text-fill: #c05d61");
                }
            } else { //task will come within 7 days
                //task is marked as uncompleted
                if (!task.getComplete()) {
                    id.setStyle("-fx-text-fill: #fbf8af");
                    taskname.setStyle("-fx-text-fill: #fbf8af");
                    taskdate.setStyle("-fx-text-fill: #fbf8af");
                    taskmodule.setStyle("-fx-text-fill: #fbf8af");
                    taskpriority.setStyle("-fx-text-fill: #fbf8af");
                }
            }
        }


        if (task.getPriority().value.equals("1")) {
            taskpriority.setText("High priority (1)");
        } else if (task.getPriority().value.equals("2")) {
            taskpriority.setText("Medium priority (2)");
        } else {
            taskpriority.setText("Low priority (3)");
        }

        if (task.getComplete()) {
            taskstate.setText("Status: Completed");
        } else {
            taskstate.setText("Status: Uncompleted");
        }

        /**
         * Modify color based on intervals between the deadline of the task and current time
         * Testing feature
         */
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}

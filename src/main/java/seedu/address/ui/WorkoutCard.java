package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.workout.Workout;

/**
 * An UI component that displays information of a {@code Workout}.
 */
public class WorkoutCard extends UiPart<Region> {

    private static final String FXML = "WorkoutListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Workout workout;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label type;
    @FXML
    private Label duration;
    @FXML
    private Label difficulty;
    @FXML
    private Label equipment;
    @FXML
    private Label muscle;
    @FXML
    private Label calories;
    @FXML
    private Label instruction;
    @FXML
    private FlowPane tags;
    @FXML
    private Label remark;

    public WorkoutCard(Workout workout, int displayedIndex) {
        super(FXML);
        this.workout = workout;
        id.setText(displayedIndex + ". ");
        name.setText(workout.getName().fullName);
        type.setText(workout.getType().fullType);
        duration.setText(workout.getDuration().fullDuration);
        difficulty.setText(workout.getDifficulty().fullDifficulty);
        equipment.setText(workout.getEquipment().fullEquipment);
        muscle.setText(workout.getMuscle().fullMuscle);
        calories.setText(workout.getCalories().fullCalories);
        instruction.setText(workout.getInstruction().fullInstruction);
        remark.setText(workout.getRemark() == null ? "" : workout.getRemark().fullRemark);
        workout.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WorkoutCard)) {
            return false;
        }

        // state check
        WorkoutCard card = (WorkoutCard) other;
        return id.getText().equals(card.id.getText())
                && workout.equals(card.workout);
    }
}

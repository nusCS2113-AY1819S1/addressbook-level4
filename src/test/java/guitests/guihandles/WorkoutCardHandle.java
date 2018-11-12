package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.workout.Workout;

/**
 * Provides a handle to a workout card in the workout list panel.
 */
public class WorkoutCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String TYPE_FIELD_ID = "#type";
    private static final String DURATION_FIELD_ID = "#duration";
    private static final String DIFFICULTY_FIELD_ID = "#difficulty";
    private static final String EQUIPMENT_FIELD_ID = "#equipment";
    private static final String MUSCLE_FIELD_ID = "#muscle";
    private static final String CALORIES_FIELD_ID = "#calories";
    private static final String INSTRUCTION_FIELD_ID = "#instruction";
    private static final String REMARK_FIELD_ID = "#remark";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label typeLabel;
    private final Label durationLabel;
    private final Label difficultyLabel;
    private final Label equipmentLabel;
    private final Label muscleLabel;
    private final Label caloriesLabel;
    private final Label instructionLabel;
    private final Label remarkLabel;
    private final List<Label> tagLabels;

    public WorkoutCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        typeLabel = getChildNode(TYPE_FIELD_ID);
        durationLabel = getChildNode(DURATION_FIELD_ID);
        difficultyLabel = getChildNode(DIFFICULTY_FIELD_ID);
        equipmentLabel = getChildNode(EQUIPMENT_FIELD_ID);
        muscleLabel = getChildNode(MUSCLE_FIELD_ID);
        caloriesLabel = getChildNode(CALORIES_FIELD_ID);
        instructionLabel = getChildNode(INSTRUCTION_FIELD_ID);
        remarkLabel = getChildNode(REMARK_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getType() {
        return typeLabel.getText();
    }

    public String getDuration () {
        return durationLabel.getText();
    }

    public String getDifficulty () {
        return difficultyLabel.getText();
    }

    public String getEquipment () {
        return equipmentLabel.getText();
    }

    public String getMuscle () {
        return muscleLabel.getText();
    }

    public String getCalories () {
        return caloriesLabel.getText();
    }

    public String getInstruction () {
        return instructionLabel.getText();
    }

    public String getRemark () {
        return remarkLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code workout}.
     */
    public boolean equals(Workout workout) {
        return getName().equals(workout.getName().fullName)
                && getType().equals(workout.getType().fullType)
                && getDuration().equals(workout.getDuration().fullDuration)
                && getDifficulty().equals(workout.getDifficulty().fullDifficulty)
                && getEquipment().equals(workout.getEquipment().fullEquipment)
                && getMuscle().equals(workout.getMuscle().fullMuscle)
                && getCalories().equals(workout.getCalories().fullCalories)
                && getInstruction().equals(workout.getInstruction().fullInstruction)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(workout.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}

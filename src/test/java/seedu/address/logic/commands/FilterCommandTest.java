package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_WORKOUTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalWorkouts.GEORGE_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrackedData;
import seedu.address.model.TrackedDataList;
import seedu.address.model.UserPrefs;
import seedu.address.model.workout.DurationPredicate;
import seedu.address.model.workout.EquipmentPredicate;
import seedu.address.model.workout.TypePredicate;



public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalWorkoutBook(), new TrackedDataList(), new TrackedData(),
            new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWorkoutBook(), new TrackedDataList(), new TrackedData(),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        DurationPredicate firstDurationPredicate =
                new DurationPredicate(Collections.singletonList("firstDuration"));
        DurationPredicate secondDurationPredicate =
                new DurationPredicate(Collections.singletonList("secondDuration"));
        EquipmentPredicate firstEquipmentPredicate =
                new EquipmentPredicate(Collections.singletonList("firstEquipment"));
        EquipmentPredicate secondEquipmentPredicate =
                new EquipmentPredicate(Collections.singletonList("secondEquipment"));
        TypePredicate firstTypePredicate =
                new TypePredicate(Collections.singletonList("firstType"));
        TypePredicate secondTypePredicate =
                new TypePredicate(Collections.singletonList("secondType"));

        FilterCommand filterFirstCommand = new FilterCommand(firstDurationPredicate,
                firstTypePredicate, firstEquipmentPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondDurationPredicate,
                secondTypePredicate, secondEquipmentPredicate);


        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstDurationPredicate,
                firstTypePredicate, firstEquipmentPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));
    }

    @Test
    public void execute_zeroKeywords_noWorkoutFound() {
        String expectedMessage = String.format(MESSAGE_WORKOUTS_LISTED_OVERVIEW, 0);
        DurationPredicate durationPredicate = prepareDurationPredicate(" ");
        TypePredicate typePredicate = prepareTypePredicate(" ");
        EquipmentPredicate equipmentPredicate = prepareEquipmentPredicate(" ");
        FilterCommand command = new FilterCommand(durationPredicate, typePredicate, equipmentPredicate);
        expectedModel.updateFilteredWorkoutList(durationPredicate.and(typePredicate).and(equipmentPredicate));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredWorkoutList());
    }

    @Test
    public void execute_multipleKeywords_multipleWorkoutsFound() {
        String expectedMessage = String.format(MESSAGE_WORKOUTS_LISTED_OVERVIEW, 1);
        DurationPredicate durationPredicate = prepareDurationPredicate("30m");
        TypePredicate typePredicate = prepareTypePredicate("strength");
        EquipmentPredicate equipmentPredicate = prepareEquipmentPredicate("free weights");
        FilterCommand command = new FilterCommand(durationPredicate, typePredicate, equipmentPredicate);
        expectedModel.updateFilteredWorkoutList(durationPredicate.and(typePredicate).and(equipmentPredicate));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE_WORKOUT), model.getFilteredWorkoutList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private DurationPredicate prepareDurationPredicate(String userInput) {
        return new DurationPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    private TypePredicate prepareTypePredicate(String userInput) {
        return new TypePredicate(Arrays.asList(userInput.split("\\s+")));
    }

    private EquipmentPredicate prepareEquipmentPredicate(String userInput) {
        return new EquipmentPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

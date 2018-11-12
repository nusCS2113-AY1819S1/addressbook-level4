package seedu.address.model.workout;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EQUIPMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.parser.Prefix;
import seedu.address.testutil.WorkoutBuilder;

public class WorkoutContainsParameterPredicateTest {

    @Test
    public void equals() {
        List<Parameter> firstPredicateParameterList = Collections.singletonList(new Parameter(PREFIX_NAME, "test"));
        List<Parameter> secondPredicateParameterList = Arrays.asList(new Parameter(PREFIX_NAME, "name"),
                new Parameter(PREFIX_NAME, "different"));

        WorkoutContainsParameterPredicate firstPredicate =
                new WorkoutContainsParameterPredicate(firstPredicateParameterList);
        WorkoutContainsParameterPredicate secondPredicate =
                new WorkoutContainsParameterPredicate(secondPredicateParameterList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        WorkoutContainsParameterPredicate firstPredicateCopy =
                new WorkoutContainsParameterPredicate(firstPredicateParameterList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different workout -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_workoutContainsParameter_returnsTrue() {
        // test name
        WorkoutContainsParameterPredicate predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(new Parameter(PREFIX_NAME, "test")));
        assertTrue(predicate.test(new WorkoutBuilder().withName("test").build()));

        // test type
        predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(new Parameter(PREFIX_TYPE, "test")));
        assertTrue(predicate.test(new WorkoutBuilder().withType("test").build()));

        // test duration
        predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(new Parameter(PREFIX_DURATION, "10m")));
        assertTrue(predicate.test(new WorkoutBuilder().withDuration("10m").build()));

        // test difficulty
        predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(new Parameter(PREFIX_DIFFICULTY,
                        "beginner")));
        assertTrue(predicate.test(new WorkoutBuilder().withDifficulty("beginner").build()));

        // test equipment
        predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(new Parameter(PREFIX_EQUIPMENT,
                        "test")));
        assertTrue(predicate.test(new WorkoutBuilder().withEquipment("test").build()));

        // test muscle
        predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(new Parameter(PREFIX_MUSCLE, "test")));
        assertTrue(predicate.test(new WorkoutBuilder().withMuscle("test").build()));

        // test calories
        predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(new Parameter(PREFIX_CALORIES, "100")));
        assertTrue(predicate.test(new WorkoutBuilder().withCalories("100").build()));

        // test instruction
        predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(new Parameter(PREFIX_INSTRUCTION,
                        "test")));
        assertTrue(predicate.test(new WorkoutBuilder().withInstruction("test").build()));

        // test tag
        predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(new Parameter(PREFIX_TAG,
                        "test")));
        assertTrue(predicate.test(new WorkoutBuilder().withTags("test").build()));

        // test remark
        predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(new Parameter(PREFIX_REMARK,
                        "test")));
        assertTrue(predicate.test(new WorkoutBuilder().withRemark("test").build()));

        // Mixed-case value
        predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(new Parameter(PREFIX_NAME, "teST")));
        assertTrue(predicate.test(new WorkoutBuilder().withName("test").build()));
    }

    @Test
    public void test_workoutDoesNotContainsParameter_returnsFalse() {
        // Non-matching value
        WorkoutContainsParameterPredicate predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(new Parameter(PREFIX_NAME, "test")));
        assertFalse(predicate.test(new WorkoutBuilder().withName("Alice").build()));
    }

    @Test
    public void test_parameterHasInvalidPrefix_returnsFalse() {
        WorkoutContainsParameterPredicate predicate =
                new WorkoutContainsParameterPredicate(Collections.singletonList(
                        new Parameter(new Prefix("test/"), "test")));
        assertFalse(predicate.test(new WorkoutBuilder().withName("Alice").build()));
    }
}

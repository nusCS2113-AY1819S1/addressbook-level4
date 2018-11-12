package seedu.address.model.workout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalWorkouts.ALICE_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.BOB_WORKOUT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.workout.exceptions.DuplicateWorkoutException;
import seedu.address.model.workout.exceptions.WorkoutNotFoundException;

public class UniqueWorkoutListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueWorkoutList uniqueWorkoutList = new UniqueWorkoutList();

    @Test
    public void contains_nullWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkoutList.contains(null);
    }

    @Test
    public void contains_workoutNotInList_returnsFalse() {
        assertFalse(uniqueWorkoutList.contains(ALICE_WORKOUT));
    }

    @Test
    public void contains_workoutInList_returnsTrue() {
        uniqueWorkoutList.add(ALICE_WORKOUT);
        assertTrue(uniqueWorkoutList.contains(ALICE_WORKOUT));
    }
    /*
    @Test
    public void contains_workoutWithSameIdentityFieldsInList_returnsTrue() {
        uniqueWorkoutList.add(ALICE_WORKOUT);
        Workout editedAlice = new WorkoutBuilder(ALICE_WORKOUT).withAddress(VALID_ADDRESS_BOB)
        .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueWorkoutList.contains(editedAlice));
    }
    */
    @Test
    public void add_nullWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkoutList.add(null);
    }

    @Test
    public void add_duplicateWorkout_throwsDuplicateWorkoutException() {
        uniqueWorkoutList.add(ALICE_WORKOUT);
        thrown.expect(DuplicateWorkoutException.class);
        uniqueWorkoutList.add(ALICE_WORKOUT);
    }

    @Test
    public void setWorkout_nullTargetWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkoutList.setWorkout(null, ALICE_WORKOUT);
    }

    @Test
    public void setWorkout_nullEditedWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkoutList.setWorkout(ALICE_WORKOUT, null);
    }

    @Test
    public void setWorkout_targetWorkoutNotInList_throwsWorkoutNotFoundException() {
        thrown.expect(WorkoutNotFoundException.class);
        uniqueWorkoutList.setWorkout(ALICE_WORKOUT, ALICE_WORKOUT);
    }

    @Test
    public void setWorkout_editedWorkoutIsSameWorkout_success() {
        uniqueWorkoutList.add(ALICE_WORKOUT);
        uniqueWorkoutList.setWorkout(ALICE_WORKOUT, ALICE_WORKOUT);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(ALICE_WORKOUT);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }
    /*
    @Test
    public void setWorkout_editedWorkoutHasSameIdentity_success() {
        uniqueWorkoutList.add(ALICE_WORKOUT);
        Workout editedAlice = new WorkoutBuilder(ALICE_WORKOUT).withAddress(VALID_ADDRESS_BOB)
        .withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueWorkoutList.setWorkout(ALICE_WORKOUT, editedAlice);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(editedAlice);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }
    */
    @Test
    public void setWorkout_editedWorkoutHasDifferentIdentity_success() {
        uniqueWorkoutList.add(ALICE_WORKOUT);
        uniqueWorkoutList.setWorkout(ALICE_WORKOUT, BOB_WORKOUT);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(BOB_WORKOUT);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkout_editedWorkoutHasNonUniqueIdentity_throwsDuplicateWorkoutException() {
        uniqueWorkoutList.add(ALICE_WORKOUT);
        uniqueWorkoutList.add(BOB_WORKOUT);
        thrown.expect(DuplicateWorkoutException.class);
        uniqueWorkoutList.setWorkout(ALICE_WORKOUT, BOB_WORKOUT);
    }

    @Test
    public void remove_nullWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkoutList.remove(null);
    }

    @Test
    public void remove_workoutDoesNotExist_throwsWorkoutNotFoundException() {
        thrown.expect(WorkoutNotFoundException.class);
        uniqueWorkoutList.remove(ALICE_WORKOUT);
    }

    @Test
    public void remove_existingWorkout_removesWorkout() {
        uniqueWorkoutList.add(ALICE_WORKOUT);
        uniqueWorkoutList.remove(ALICE_WORKOUT);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkouts_nullUniqueWorkoutList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkoutList.setWorkouts((UniqueWorkoutList) null);
    }

    @Test
    public void setWorkouts_uniqueWorkoutList_replacesOwnListWithProvidedUniqueWorkoutList() {
        uniqueWorkoutList.add(ALICE_WORKOUT);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(BOB_WORKOUT);
        uniqueWorkoutList.setWorkouts(expectedUniqueWorkoutList);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkouts_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkoutList.setWorkouts((List<Workout>) null);
    }

    @Test
    public void setWorkouts_list_replacesOwnListWithProvidedList() {
        uniqueWorkoutList.add(ALICE_WORKOUT);
        List<Workout> workoutList = Collections.singletonList(BOB_WORKOUT);
        uniqueWorkoutList.setWorkouts(workoutList);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(BOB_WORKOUT);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkouts_listWithDuplicateWorkouts_throwsDuplicateWorkoutException() {
        List<Workout> listWithDuplicateWorkouts = Arrays.asList(ALICE_WORKOUT, ALICE_WORKOUT);
        thrown.expect(DuplicateWorkoutException.class);
        uniqueWorkoutList.setWorkouts(listWithDuplicateWorkouts);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueWorkoutList.asUnmodifiableObservableList().remove(0);
    }
}

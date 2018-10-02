package seedu.address.model.workout;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.workout.exceptions.DuplicateWorkoutException;
import seedu.address.model.workout.exceptions.WorkoutNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalWorkouts.ALICE;
import static seedu.address.testutil.TypicalWorkouts.BOB;

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
        assertFalse(uniqueWorkoutList.contains(ALICE));
    }

    @Test
    public void contains_workoutInList_returnsTrue() {
        uniqueWorkoutList.add(ALICE);
        assertTrue(uniqueWorkoutList.contains(ALICE));
    }

    @Test
    public void contains_workoutWithSameIdentityFieldsInList_returnsTrue() {
        uniqueWorkoutList.add(ALICE);
        Workout editedAlice = new WorkoutBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueWorkoutList.contains(editedAlice));
    }

    @Test
    public void add_nullWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkoutList.add(null);
    }

    @Test
    public void add_duplicateWorkout_throwsDuplicateWorkoutException() {
        uniqueWorkoutList.add(ALICE);
        thrown.expect(DuplicateWorkoutException.class);
        uniqueWorkoutList.add(ALICE);
    }

    @Test
    public void setWorkout_nullTargetWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkoutList.setWorkout(null, ALICE);
    }

    @Test
    public void setWorkout_nullEditedWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkoutList.setWorkout(ALICE, null);
    }

    @Test
    public void setWorkout_targetWorkoutNotInList_throwsWorkoutNotFoundException() {
        thrown.expect(WorkoutNotFoundException.class);
        uniqueWorkoutList.setWorkout(ALICE, ALICE);
    }

    @Test
    public void setWorkout_editedWorkoutIsSameWorkout_success() {
        uniqueWorkoutList.add(ALICE);
        uniqueWorkoutList.setWorkout(ALICE, ALICE);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(ALICE);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkout_editedWorkoutHasSameIdentity_success() {
        uniqueWorkoutList.add(ALICE);
        Workout editedAlice = new WorkoutBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueWorkoutList.setWorkout(ALICE, editedAlice);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(editedAlice);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkout_editedWorkoutHasDifferentIdentity_success() {
        uniqueWorkoutList.add(ALICE);
        uniqueWorkoutList.setWorkout(ALICE, BOB);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(BOB);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkout_editedWorkoutHasNonUniqueIdentity_throwsDuplicateWorkoutException() {
        uniqueWorkoutList.add(ALICE);
        uniqueWorkoutList.add(BOB);
        thrown.expect(DuplicateWorkoutException.class);
        uniqueWorkoutList.setWorkout(ALICE, BOB);
    }

    @Test
    public void remove_nullWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkoutList.remove(null);
    }

    @Test
    public void remove_workoutDoesNotExist_throwsWorkoutNotFoundException() {
        thrown.expect(WorkoutNotFoundException.class);
        uniqueWorkoutList.remove(ALICE);
    }

    @Test
    public void remove_existingWorkout_removesWorkout() {
        uniqueWorkoutList.add(ALICE);
        uniqueWorkoutList.remove(ALICE);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkouts_nullUniqueWorkoutList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWorkoutList.setWorkouts((UniqueWorkoutList) null);
    }

    @Test
    public void setWrokouts_uniqueWorkoutList_replacesOwnListWithProvidedUniqueWorkoutList() {
        uniqueWorkoutList.add(ALICE);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(BOB);
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
        uniqueWorkoutList.add(ALICE);
        List<Workout> workoutList = Collections.singletonList(BOB);
        uniqueWorkoutList.setWorkouts(workoutList);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(BOB);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkouts_listWithDuplicateWorkouts_throwsDuplicateWorkoutException() {
        List<Workout> listWithDuplicateWorkouts = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateWorkoutException.class);
        uniqueWorkoutList.setWorkouts(listWithDuplicateWorkouts);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueWorkoutList.asUnmodifiableObservableList().remove(0);
    }
}

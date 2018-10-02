package seedu.address.model.workout;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalWorkouts.ALICE;
import static seedu.address.testutil.TypicalWorkouts.BOB;

public class WorkoutTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Workout workout = new WorkoutBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        workout.getTags().remove(0);
    }

    @Test
    public void isSameWorkout() {
        // same object -> returns true
        assertTrue(ALICE.isSameWorkout(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameWorkout(null));

        // different phone and email -> returns false
        Workout editedAlice = new WorkoutBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameWorkout(editedAlice));

        // different name -> returns false
        editedAlice = new WorkoutBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameWorkout(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new WorkoutBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameWorkout(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new WorkoutBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameWorkout(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new WorkoutBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameWorkout(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Workout aliceCopy = new WorkoutBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different workout -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Workout editedAlice = new WorkoutBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new WorkoutBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new WorkoutBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new WorkoutBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new WorkoutBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}

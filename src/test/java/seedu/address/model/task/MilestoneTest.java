package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalMilestones.FIRST_MILESTONE;
import static seedu.address.testutil.TypicalMilestones.SECOND_MILESTONE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.MilestoneBuilder;

//@@author JeremyInElysium
public class MilestoneTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        Milestone firstMilestoneCopy = new MilestoneBuilder(FIRST_MILESTONE).build();

        //same values -> return true
        assertTrue(FIRST_MILESTONE.equals(firstMilestoneCopy));

        //same object -> return tue
        assertTrue(FIRST_MILESTONE.equals(FIRST_MILESTONE));

        //null -> returns false
        assertFalse(FIRST_MILESTONE.equals(null));

        //different type -> returns false
        assertFalse(FIRST_MILESTONE.equals(1));

        //different milestone -> returns false
        assertFalse(FIRST_MILESTONE.equals(SECOND_MILESTONE));

        //different rank -> returns false
        Milestone editedFirstMilestoneRank = new MilestoneBuilder(FIRST_MILESTONE).withRank("10").build();
        assertFalse(FIRST_MILESTONE.equals(editedFirstMilestoneRank));
    }
}

package seedu.address.model.clubbudget;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLOCATED_BUDGET_ECE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLUB_NAME_ECE;
import static seedu.address.testutil.TypicalFinalClubBudget.COMPUTING_CLUB;
import static seedu.address.testutil.TypicalFinalClubBudget.ECE_CLUB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.FinalClubBudgetBuilder;

public class FinalClubBudgetTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameFinalClubBudget() {
        // same object -> returns true
        assertTrue(COMPUTING_CLUB.isSameFinalClubBudget(COMPUTING_CLUB));

        // null -> returns false
        assertFalse(COMPUTING_CLUB.isSameFinalClubBudget(null));

        // different name -> returns false
        FinalClubBudget editedComputingClub = new FinalClubBudgetBuilder(COMPUTING_CLUB)
                .withClubName(VALID_CLUB_NAME_ECE).build();
        assertFalse(COMPUTING_CLUB.isSameFinalClubBudget(editedComputingClub));

        // same club name, different allocated budget -> returns true
        editedComputingClub = new FinalClubBudgetBuilder(COMPUTING_CLUB)
                .withAllocatedBudget(VALID_ALLOCATED_BUDGET_ECE).build();
        assertTrue(COMPUTING_CLUB.isSameFinalClubBudget(editedComputingClub));

    }

    @Test
    public void equals() {
        // same values -> returns true
        FinalClubBudget computingClubCopy = new FinalClubBudgetBuilder(COMPUTING_CLUB).build();
        assertTrue(COMPUTING_CLUB.equals(computingClubCopy));

        // same object -> returns true
        assertTrue(COMPUTING_CLUB.equals(COMPUTING_CLUB));

        // null -> returns false
        assertFalse(COMPUTING_CLUB.equals(null));

        // different type -> returns false
        assertFalse(COMPUTING_CLUB.equals(5));

        // different club -> returns false
        assertFalse(COMPUTING_CLUB.equals(ECE_CLUB));

        // different name -> returns false
        FinalClubBudget editedComputingClub = new FinalClubBudgetBuilder(COMPUTING_CLUB)
                .withClubName(VALID_CLUB_NAME_ECE).build();
        assertFalse(COMPUTING_CLUB.equals(editedComputingClub));

        // different allocated budget -> returns true
        editedComputingClub = new FinalClubBudgetBuilder(COMPUTING_CLUB)
                .withAllocatedBudget(VALID_ALLOCATED_BUDGET_ECE).build();
        assertTrue(COMPUTING_CLUB.equals(editedComputingClub));

    }

}

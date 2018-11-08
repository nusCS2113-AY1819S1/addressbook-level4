package seedu.address.model.clubbudget;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLOCATED_BUDGET_ECE;
import static seedu.address.testutil.TypicalFinalClubBudget.COMPUTING_CLUB;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.model.clubbudget.exceptions.DuplicateFinalClubBudgetException;
import seedu.address.testutil.FinalClubBudgetBuilder;

public class UniqueClubBudgetListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueClubBudgetList uniqueClubBudgetList = new UniqueClubBudgetList();

    @Test
    public void contains_nullFinalClubBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueClubBudgetList.contains(null);
    }

    @Test
    public void contains_clubBudgetNotInList_returnsFalse() {
        assertFalse(uniqueClubBudgetList.contains(COMPUTING_CLUB));
    }

    @Test
    public void contains_clubBudgetInList_returnsTrue() {
        uniqueClubBudgetList.add(COMPUTING_CLUB);
        assertTrue(uniqueClubBudgetList.contains(COMPUTING_CLUB));
    }

    @Test
    public void contains_clubBudgetWithSameIdentityFieldsInList_returnsTrue() {
        uniqueClubBudgetList.add(COMPUTING_CLUB);
        FinalClubBudget editedComputingClub = new FinalClubBudgetBuilder(COMPUTING_CLUB)
                .withAllocatedBudget(VALID_ALLOCATED_BUDGET_ECE)
                .build();
        assertTrue(uniqueClubBudgetList.contains(editedComputingClub));
    }

    @Test
    public void add_nullClubBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueClubBudgetList.add(null);
    }

    @Test
    public void add_duplicateClubBudget_throwsDuplicateFinalClubBudgetException() {
        uniqueClubBudgetList.add(COMPUTING_CLUB);
        thrown.expect(DuplicateFinalClubBudgetException.class);
        uniqueClubBudgetList.add(COMPUTING_CLUB);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueClubBudgetList.asUnmodifiableObservableList().remove(0);
    }
}

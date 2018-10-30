package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLOCATED_BUDGET_ECE;
import static seedu.address.testutil.TypicalFinalClubBudget.COMPUTING_CLUB;
import static seedu.address.testutil.TypicalFinalClubBudget.getTypicalFinalBudgetsBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.clubbudget.FinalClubBudget;
import seedu.address.model.clubbudget.exceptions.DuplicateFinalClubBudgetException;
import seedu.address.testutil.FinalClubBudgetBuilder;

public class FinalBudgetsBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FinalBudgetsBook finalBudgetsBook = new FinalBudgetsBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), finalBudgetsBook.getClubBudgetsList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        finalBudgetsBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyFinalBudgetBook_replacesData() {
        FinalBudgetsBook newData = getTypicalFinalBudgetsBook();
        finalBudgetsBook.resetData(newData);
        assertEquals(newData, finalBudgetsBook);
    }

    @Test
    public void resetData_withDuplicateFinalClubBudget_throwsDuplicateFinalClubBudgetException() {
        // Two final club budgets with the same identity fields
        FinalClubBudget editedClubBudget1 = new FinalClubBudgetBuilder(COMPUTING_CLUB)
                .withAllocatedBudget(VALID_ALLOCATED_BUDGET_ECE).build();
        List<FinalClubBudget> newBudgets = Arrays.asList(COMPUTING_CLUB, editedClubBudget1);
        FinalBudgetsBookStub newData = new FinalBudgetsBookStub(newBudgets);

        thrown.expect(DuplicateFinalClubBudgetException.class);
        finalBudgetsBook.resetData(newData);
    }

    @Test
    public void hasFinalClubBudget_nullFinalClubBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        finalBudgetsBook.hasClubBudget(null);
    }

    @Test
    public void hasFinalClubBudget_finalClubBudgetNotInFinalBudgetsBook_returnsFalse() {
        assertFalse(finalBudgetsBook.hasClubBudget(COMPUTING_CLUB));
    }

    @Test
    public void hasFinalClubBudget_finalClubBudgetInFinalBudgetsBook_returnsTrue() {
        finalBudgetsBook.addClubBudget(COMPUTING_CLUB);
        assertTrue(finalBudgetsBook.hasClubBudget(COMPUTING_CLUB));
    }

    @Test
    public void hasFinalClubBudget_finalClubBudgetWithSameIdentityFieldsInFinalBudgetsBook_returnsTrue() {
        finalBudgetsBook.addClubBudget(COMPUTING_CLUB);
        FinalClubBudget editedClubBudget1 = new FinalClubBudgetBuilder(COMPUTING_CLUB)
                .withAllocatedBudget(VALID_ALLOCATED_BUDGET_ECE).build();
        assertTrue(finalBudgetsBook.hasClubBudget(editedClubBudget1));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class FinalBudgetsBookStub implements ReadOnlyFinalBudgetBook {
        private final ObservableList<FinalClubBudget> budgets = FXCollections.observableArrayList();

        FinalBudgetsBookStub(Collection<FinalClubBudget> budgets) {
            this.budgets.setAll(budgets);
        }

        @Override
        public ObservableList<FinalClubBudget> getClubBudgetsList() {
            return budgets;
        }
    }
}

//@@author feijunzi
package seedu.address.model.expenditureinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalExpenditures.CHICKEN;
import static seedu.address.testutil.TypicalExpenditures.IPHONE;

import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.expenditureinfo.exceptions.DuplicateExpenditureException;
import seedu.address.model.expenditureinfo.exceptions.ExpenditureNotFoundException;

public class ExpenditureListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ExpenditureList expenditureList = new ExpenditureList();

    @Test
    public void contains_nullExpenditure_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.contains(null);
    }

    @Test
    public void contains_expenditureNotInList_returnsFalse() {
        assertFalse(expenditureList.contains(CHICKEN));
    }

    @Test
    public void contains_expenditureInList_returnsTrue() {
        expenditureList.add(CHICKEN);
        assertTrue(expenditureList.contains(CHICKEN));
    }

    @Test
    public void add_nullExpenditure_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.add(null);
    }

    @Test
    public void setExpenditures_nullTargetExpenditure_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.setExpenditures(null, CHICKEN);
    }

    @Test
    public void setExpenditures_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.setExpenditures(CHICKEN, null);
    }

    @Test
    public void setExpenditures_targetExpenditureNotInList_throwsPersonNotFoundException() {
        thrown.expect(ExpenditureNotFoundException.class);
        expenditureList.setExpenditures(CHICKEN, CHICKEN);
    }

    @Test
    public void setExpenditures_editedExpenditureIsSameExpenditure_success() {
        expenditureList.add(CHICKEN);
        expenditureList.setExpenditures(CHICKEN, CHICKEN);
        ExpenditureList expectedExpenditureList = new ExpenditureList();
        expectedExpenditureList.add(CHICKEN);
        assertEquals(expectedExpenditureList, expenditureList);
    }

    @Test
    public void setExpenditures_editedExpenditureHasDifferentIdentity_success() {
        expenditureList.add(CHICKEN);
        expenditureList.setExpenditures(CHICKEN, IPHONE);
        ExpenditureList expectedExpenditureList = new ExpenditureList();
        expectedExpenditureList.add(IPHONE);
        assertEquals(expectedExpenditureList, expenditureList);
    }

    @Test
    public void setExpenditures_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        expenditureList.add(CHICKEN);
        expenditureList.add(IPHONE);
        thrown.expect(DuplicateExpenditureException.class);
        expenditureList.setExpenditures(CHICKEN, IPHONE);
    }

    @Test
    public void remove_nullExpenditure_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.remove(null);
    }

    @Test
    public void remove_expenditureDoesNotExist_throwsExpenditureNotFoundException() {
        thrown.expect(ExpenditureNotFoundException.class);
        expenditureList.remove(CHICKEN);
    }

    @Test
    public void remove_existingExpenditure_removesExpenditure() {
        expenditureList.add(CHICKEN);
        expenditureList.remove(CHICKEN);
        ExpenditureList expectedExpenditureList = new ExpenditureList();
        assertEquals(expectedExpenditureList, expenditureList);
    }

    @Test
    public void setExpenditures_nullExpenditureList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.setExpenditures((ExpenditureList) null);
    }

    @Test
    public void setExpenditures_expenditureList_replacesOwnListWithProvidedExpenditureList() {
        expenditureList.add(CHICKEN);
        ExpenditureList expectedExpenditureList = new ExpenditureList();
        expectedExpenditureList.add(IPHONE);
        expenditureList.setExpenditures(expectedExpenditureList);
        assertEquals(expectedExpenditureList, expenditureList);
    }

    @Test
    public void setExpenditures_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.setExpenditures((List<Expenditure>) null);
    }

    @Test
    public void setExpenditures_list_replacesOwnListWithProvidedList() {
        expenditureList.add(CHICKEN);
        List<Expenditure> personList = Collections.singletonList(IPHONE);
        expenditureList.setExpenditures(personList);
        ExpenditureList expectedExpenditureList = new ExpenditureList();
        expectedExpenditureList.add(IPHONE);
        assertEquals(expectedExpenditureList, expenditureList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        expenditureList.asUnmodifiableObservableList().remove(0);
    }

}

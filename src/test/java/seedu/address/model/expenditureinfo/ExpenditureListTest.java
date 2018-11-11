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
    public void containsnullExpenditurethrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.contains(null);
    }

    @Test
    public void containsexpenditureNotInListreturnsFalse() {
        assertFalse(expenditureList.contains(CHICKEN));
    }

    @Test
    public void containsexpenditureInListreturnsTrue() {
        expenditureList.add(CHICKEN);
        assertTrue(expenditureList.contains(CHICKEN));
    }

    @Test
    public void addnullExpenditurethrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.add(null);
    }

    @Test
    public void setExpendituresnullTargetExpenditurethrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.setExpenditures(null, CHICKEN);
    }

    @Test
    public void setExpendituresnullEditedPersonthrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.setExpenditures(CHICKEN, null);
    }

    @Test
    public void setExpenditurestargetExpenditureNotInListthrowsPersonNotFoundException() {
        thrown.expect(ExpenditureNotFoundException.class);
        expenditureList.setExpenditures(CHICKEN, CHICKEN);
    }

    @Test
    public void setExpenditureseditedExpenditureIsSameExpendituresuccess() {
        expenditureList.add(CHICKEN);
        expenditureList.setExpenditures(CHICKEN, CHICKEN);
        ExpenditureList expectedExpenditureList = new ExpenditureList();
        expectedExpenditureList.add(CHICKEN);
        assertEquals(expectedExpenditureList, expenditureList);
    }

    @Test
    public void setExpenditureseditedExpenditureHasDifferentIdentitysuccess() {
        expenditureList.add(CHICKEN);
        expenditureList.setExpenditures(CHICKEN, IPHONE);
        ExpenditureList expectedExpenditureList = new ExpenditureList();
        expectedExpenditureList.add(IPHONE);
        assertEquals(expectedExpenditureList, expenditureList);
    }

    @Test
    public void setExpenditureseditedPersonHasNonUniqueIdentitythrowsDuplicatePersonException() {
        expenditureList.add(CHICKEN);
        expenditureList.add(IPHONE);
        thrown.expect(DuplicateExpenditureException.class);
        expenditureList.setExpenditures(CHICKEN, IPHONE);
    }

    @Test
    public void removenullExpenditurethrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.remove(null);
    }

    @Test
    public void removeexpenditureDoesNotExistthrowsExpenditureNotFoundException() {
        thrown.expect(ExpenditureNotFoundException.class);
        expenditureList.remove(CHICKEN);
    }

    @Test
    public void removeexistingExpenditureremovesExpenditure() {
        expenditureList.add(CHICKEN);
        expenditureList.remove(CHICKEN);
        ExpenditureList expectedExpenditureList = new ExpenditureList();
        assertEquals(expectedExpenditureList, expenditureList);
    }

    @Test
    public void setExpendituresnullExpenditureListthrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.setExpenditures((ExpenditureList) null);
    }

    @Test
    public void setExpendituresexpenditureListreplacesOwnListWithProvidedExpenditureList() {
        expenditureList.add(CHICKEN);
        ExpenditureList expectedExpenditureList = new ExpenditureList();
        expectedExpenditureList.add(IPHONE);
        expenditureList.setExpenditures(expectedExpenditureList);
        assertEquals(expectedExpenditureList, expenditureList);
    }

    @Test
    public void setExpendituresnullListthrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.setExpenditures((List<Expenditure>) null);
    }

    @Test
    public void setExpenditureslistreplacesOwnListWithProvidedList() {
        expenditureList.add(CHICKEN);
        List<Expenditure> personList = Collections.singletonList(IPHONE);
        expenditureList.setExpenditures(personList);
        ExpenditureList expectedExpenditureList = new ExpenditureList();
        expectedExpenditureList.add(IPHONE);
        assertEquals(expectedExpenditureList, expenditureList);
    }

    @Test
    public void asUnmodifiableObservableListmodifyListthrowsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        expenditureList.asUnmodifiableObservableList().remove(0);
    }

}

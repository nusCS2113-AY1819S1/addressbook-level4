package seedu.address.model.recruitment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POST_RE3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORK_EXP_RE3;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE1;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE2;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE5;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.recruitment.exceptions.DuplicateRecruitmentException;
import seedu.address.model.recruitment.exceptions.RecruitmentNotFoundException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.recruitment.RecruitmentBuilder;


public class UniqueRecruitmentListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueRecruitmentList uniqueRecruitmentList = new UniqueRecruitmentList();

    @Test
    public void contains_nullRecruitment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecruitmentList.contains(null);
    }

    @Test
    public void contains_recruitmentNotInList_returnsFalse() {
        assertFalse(uniqueRecruitmentList.contains(RECRUITMENT_EXAMPLE));
    }

    @Test
    public void contains_recruitmentInList_returnsTrue() {
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE);
        assertTrue(uniqueRecruitmentList.contains(RECRUITMENT_EXAMPLE));
    }

    @Test
    public void contains_recruitmentWithDifferentIdentityFieldsInList_returnsFalse() {
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE);
        Recruitment editedExample = new RecruitmentBuilder(
                RECRUITMENT_EXAMPLE).withPost(VALID_POST_RE3).withWorkExp(VALID_WORK_EXP_RE3)
                .build();
        assertFalse(uniqueRecruitmentList.contains(editedExample));
    }

    @Test
    public void add_nullRecruitment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecruitmentList.add(null);
    }

    @Test
    public void add_duplicateRecruitment_throwsDuplicateRecruitmentException() {
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE1);
        thrown.expect(DuplicateRecruitmentException.class);
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE1);
    }

    @Test
    public void setRecruitment_nullTargetRecruitment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecruitmentList.setRecruitment(null, RECRUITMENT_EXAMPLE2);
    }

    @Test
    public void setRecruitment_nullEditedRecruitment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecruitmentList.setRecruitment(RECRUITMENT_EXAMPLE, null);
    }

    @Test
    public void setRecruitment_targetRecruitmentNotInList_throwsRecruitmentNotFoundException() {
        thrown.expect(RecruitmentNotFoundException.class);
        uniqueRecruitmentList.setRecruitment(RECRUITMENT_EXAMPLE, RECRUITMENT_EXAMPLE);
    }

    @Test
    public void setRecruitment_editedRecruitmentIsSameRecruitment_success() {
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE5);
        uniqueRecruitmentList.setRecruitment(RECRUITMENT_EXAMPLE5, RECRUITMENT_EXAMPLE5);
        UniqueRecruitmentList expectedUniqueRecruitmentList = new UniqueRecruitmentList();
        expectedUniqueRecruitmentList.add(RECRUITMENT_EXAMPLE5);
        assertEquals(expectedUniqueRecruitmentList, uniqueRecruitmentList);
    }

    @Test
    public void setRecruitment_editedRecruitmentHasSameRecruitment_success() {
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE);
        Recruitment editedExample = new RecruitmentBuilder(RECRUITMENT_EXAMPLE).withPost(
                VALID_POST_RE3).withWorkExp(VALID_WORK_EXP_RE3)
                .build();
        uniqueRecruitmentList.setRecruitment(RECRUITMENT_EXAMPLE, editedExample);
        UniqueRecruitmentList expectedUniqueRecruitmentList = new UniqueRecruitmentList();
        expectedUniqueRecruitmentList.add(editedExample);
        assertEquals(expectedUniqueRecruitmentList, uniqueRecruitmentList);
    }

    @Test
    public void setRecruitment_editedRecruitmentHasDifferentRecruitment_success() {
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE);
        uniqueRecruitmentList.setRecruitment(RECRUITMENT_EXAMPLE, RECRUITMENT_EXAMPLE1);
        UniqueRecruitmentList expectedUniqueRecruitmentList = new UniqueRecruitmentList();
        expectedUniqueRecruitmentList.add(RECRUITMENT_EXAMPLE1);
        assertEquals(expectedUniqueRecruitmentList, uniqueRecruitmentList);
    }

    @Test
    public void setRecruitment_editedRecruitmentHasNonUniqueIdentity_throwsDuplicateRecruitmentException() {
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE);
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE1);
        thrown.expect(DuplicateRecruitmentException.class);
        uniqueRecruitmentList.setRecruitment(RECRUITMENT_EXAMPLE, RECRUITMENT_EXAMPLE1);
    }

    @Test
    public void remove_nullRecruitment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecruitmentList.remove(null);
    }

    @Test
    public void remove_recruitmentDoesNotExist_throwsRecruitmentNotFoundException() {
        thrown.expect(RecruitmentNotFoundException.class);
        uniqueRecruitmentList.remove(RECRUITMENT_EXAMPLE);
    }

    @Test
    public void remove_existingRecruitment_removesRecruitment() {
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE);
        uniqueRecruitmentList.remove(RECRUITMENT_EXAMPLE);
        UniqueRecruitmentList expectedUniqueRecruitmentList = new UniqueRecruitmentList();
        assertEquals(expectedUniqueRecruitmentList, uniqueRecruitmentList);
    }

    @Test
    public void setRecruitments_nullUniqueRecruitmentList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecruitmentList.setRecruitments((UniqueRecruitmentList) null);
    }

    @Test
    public void setRecruitments_uniqueRecruitmentList_replacesOwnListWithProvidedUniqueRecruitmentList() {
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE);
        UniqueRecruitmentList expectedUniqueRecruitmentList = new UniqueRecruitmentList();
        expectedUniqueRecruitmentList.add(RECRUITMENT_EXAMPLE1);
        uniqueRecruitmentList.setRecruitments(expectedUniqueRecruitmentList);
        assertEquals(expectedUniqueRecruitmentList, uniqueRecruitmentList);
    }

    @Test
    public void setRecruitments_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecruitmentList.setRecruitments((List<Recruitment>) null);
    }

    @Test
    public void setRecruitments_list_replacesOwnListWithProvidedList() {
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE);
        List<Recruitment> recruitmentList = Collections.singletonList(RECRUITMENT_EXAMPLE1);
        uniqueRecruitmentList.setRecruitments(recruitmentList);
        UniqueRecruitmentList expectedUniqueRecruitmentList = new UniqueRecruitmentList();
        expectedUniqueRecruitmentList.add(RECRUITMENT_EXAMPLE1);
        assertEquals(expectedUniqueRecruitmentList, uniqueRecruitmentList);
    }

    @Test
    public void setRecruitments_listWithDuplicateRecruitments_throwsDuplicateRecruitmentException() {
        List<Recruitment> listWithDuplicateRecruitments = Arrays.asList(RECRUITMENT_EXAMPLE, RECRUITMENT_EXAMPLE);
        thrown.expect(DuplicateRecruitmentException.class);
        uniqueRecruitmentList.setRecruitments(listWithDuplicateRecruitments);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRecruitmentList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void hashCode_validRecruitment_correctHashCodeRepresentation() {
        uniqueRecruitmentList.add(RECRUITMENT_EXAMPLE);
        Recruitment sameExample = new RecruitmentBuilder(RECRUITMENT_EXAMPLE).build();
        List<Recruitment> expected = Arrays.asList(sameExample);
        assertEquals(uniqueRecruitmentList.hashCode(), expected.hashCode());
    }


    //Iterator has been tested by java, we will just run a few test to check if iterator is returned from the method
    @Test
    public void iterator_emptyUniqueRecruitmentList_hasNoNextRecruitment() {
        assertFalse(uniqueRecruitmentList.iterator().hasNext());
    }

    @Test
    public void iterator_nextRecruitmentEmptyUniqueRecruitmentList_throwsNoSuchElementException() {
        Assert.assertThrows(NoSuchElementException.class, () -> uniqueRecruitmentList.iterator().next());
    }
}

package seedu.address.model.recruitment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE1;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.recruitment.exceptions.DuplicateRecruitmentException;
import seedu.address.testutil.recruitment.RecruitmentBuilder;


public class RecruitmentListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RecruitmentList recruitmentList = new RecruitmentList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), recruitmentList.getRecruitmentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        recruitmentList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyRecruitmentList_replacesData() {
        RecruitmentList newData = getTypicalRecruitmentList();
        recruitmentList.resetData(newData);
        assertEquals(newData, recruitmentList);
    }

    @Test
    public void resetData_withDuplicateRecruitments_throwsDuplicateRecruitmentException() {
        // Two recruitment posts with the same identity fields
        Recruitment editedExample = new RecruitmentBuilder(RECRUITMENT_EXAMPLE).build();
        List<Recruitment> newRecruitments = Arrays.asList(RECRUITMENT_EXAMPLE, editedExample);
        RecruitmentListStub newData = new RecruitmentListStub(newRecruitments);

        thrown.expect(DuplicateRecruitmentException.class);
        recruitmentList.resetData(newData);
    }

    @Test
    public void hasRecruitment_nullRecruitment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        recruitmentList.hasRecruitment(null);
    }

    @Test
    public void hasRecruitment_recruitmentNotInRecruitmentList_returnsFalse() {
        assertFalse(recruitmentList.hasRecruitment(RECRUITMENT_EXAMPLE));
    }

    @Test
    public void hasRecruitment_recruitmentInRecruitmentList_returnsTrue() {
        recruitmentList.addRecruitment(RECRUITMENT_EXAMPLE);
        assertTrue(recruitmentList.hasRecruitment(RECRUITMENT_EXAMPLE));
    }

    @Test
    public void hasRecruitment_recruitmentWithSameIdentityFieldsInRecruitmentList_returnsTrue() {
        recruitmentList.addRecruitment(RECRUITMENT_EXAMPLE);
        Recruitment editedExample = new RecruitmentBuilder(RECRUITMENT_EXAMPLE).build();
        assertTrue(recruitmentList.hasRecruitment(editedExample));
    }

    @Test
    public void getRecruitmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        recruitmentList.getRecruitmentList().remove(0);
    }

    @Test
    public void toString_validRecruitment_returnsOneRecruitmentSize() {
        recruitmentList.addRecruitment(RECRUITMENT_EXAMPLE);
        assertEquals(recruitmentList.toString(), "1 recruitments");
    }

    @Test
    public void hashCode_validRecruitment_correctHashCodeRepresentation() {
        recruitmentList.addRecruitment(RECRUITMENT_EXAMPLE);
        Recruitment sameExample = new RecruitmentBuilder(RECRUITMENT_EXAMPLE).build();
        List<Recruitment> expected = Arrays.asList(sameExample);
        assertEquals(recruitmentList.hashCode(), expected.hashCode());
    }

    @Test
    public void updateRecruitment_validRecruitment_recruitmentUpdated() {
        recruitmentList.addRecruitment(RECRUITMENT_EXAMPLE);
        recruitmentList.updateRecruitment(RECRUITMENT_EXAMPLE, RECRUITMENT_EXAMPLE1);
        assertTrue(recruitmentList.hasRecruitment(RECRUITMENT_EXAMPLE1));
    }

    /**
     * A stub ReadOnlyRecruitment whose recruitment list can violate interface constraints.
     */
    private static class RecruitmentListStub implements ReadOnlyRecruitmentList {
        private final ObservableList<Recruitment> recruitments = FXCollections.observableArrayList();

        RecruitmentListStub(Collection<Recruitment> recruitments) {
            this.recruitments.setAll(recruitments);
        }

        @Override
        public ObservableList<Recruitment> getRecruitmentList() {
            return recruitments;
        }
    }
}

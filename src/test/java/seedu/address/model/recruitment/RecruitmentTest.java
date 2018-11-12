package seedu.address.model.recruitment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_DESCRIPTION_RE3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POST_RE3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORK_EXP_RE3;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE;
import static seedu.address.testutil.TypicalRecruitments.VALID_JOB_DESCRIPTION;
import static seedu.address.testutil.TypicalRecruitments.VALID_JOB_POSITION;
import static seedu.address.testutil.TypicalRecruitments.VALID_MINIMUM_EXPERIENCE;

import java.util.Objects;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;
import seedu.address.testutil.recruitment.RecruitmentBuilder;

public class RecruitmentTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Recruitment(null, null, null));
    }

    @Test
    public void isSameRecruitment_sameRecruitment_true() {
        // same object -> returns true
        assertTrue(RECRUITMENT_EXAMPLE.isSameRecruitment(RECRUITMENT_EXAMPLE));
    }

    @Test
    public void isSameRecruitment_null_false() {
        // null object -> returns false
        assertFalse(RECRUITMENT_EXAMPLE.isSameRecruitment(null));
    }

    @Test
    public void isSameRecruitment_differentPost_false() {
        // different post position -> returns false
        Recruitment editedExample = new RecruitmentBuilder().withPost(VALID_POST_RE3)
                .withWorkExp(VALID_MINIMUM_EXPERIENCE).withJobDescription(VALID_JOB_DESCRIPTION).build();
        assertFalse(RECRUITMENT_EXAMPLE.isSameRecruitment(editedExample));
    }

    @Test
    public void isSameRecruitment_differentJobDescription_false() {
        // different job description -> returns false
        Recruitment editedExample = new RecruitmentBuilder().withPost(VALID_JOB_POSITION)
                .withWorkExp(VALID_MINIMUM_EXPERIENCE).withJobDescription(VALID_JOB_DESCRIPTION_RE3).build();
        assertFalse(RECRUITMENT_EXAMPLE.isSameRecruitment(editedExample));
    }

    @Test
    public void isSameRecruitment_differentWorkExp_false() {
        // different working experience -> returns false
        Recruitment editedExample = new RecruitmentBuilder().withPost(VALID_JOB_POSITION)
                .withWorkExp(VALID_WORK_EXP_RE3).withJobDescription(VALID_JOB_DESCRIPTION).build();
        assertFalse(RECRUITMENT_EXAMPLE.isSameRecruitment(editedExample));
    }

    @Test
    public void isSameRecruitment_sameAttributes_true() {
        // same post postion, working experience and job description -> returns true
        Recruitment editedExample = new RecruitmentBuilder().withPost(VALID_JOB_POSITION)
                .withWorkExp(VALID_MINIMUM_EXPERIENCE).withJobDescription(VALID_JOB_DESCRIPTION).build();
        assertTrue(RECRUITMENT_EXAMPLE.isSameRecruitment(editedExample));
    }

    @Test
    public void hashCode_validRecruitment_correctHashCodeRepresentation() {
        Recruitment editedExample = new RecruitmentBuilder(RECRUITMENT_EXAMPLE).withPost(VALID_JOB_POSITION)
                .withWorkExp(VALID_MINIMUM_EXPERIENCE).withJobDescription(VALID_JOB_DESCRIPTION).build();
        assertEquals(editedExample.hashCode(), Objects.hash(
                VALID_JOB_POSITION, VALID_MINIMUM_EXPERIENCE, VALID_JOB_DESCRIPTION));
    }


}

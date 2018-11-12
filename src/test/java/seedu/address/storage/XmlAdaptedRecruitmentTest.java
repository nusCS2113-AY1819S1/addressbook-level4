package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.recruitment.XmlAdaptedRecruitment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recruitment.JobDescription;
import seedu.address.model.recruitment.Post;
import seedu.address.model.recruitment.WorkExp;
import seedu.address.storage.recruitment.XmlAdaptedRecruitment;
import seedu.address.testutil.Assert;

public class XmlAdaptedRecruitmentTest {

    private static final String INVALID_POST = "Manager1";
    private static final String INVALID_WORKEXP = "3.0";
    private static final String INVALID_JOBDESCRIPTION = "d0";

    private static final String VALID_POST = RECRUITMENT_EXAMPLE.getPost().toString();
    private static final String VALID_WORKEXP = RECRUITMENT_EXAMPLE.getWorkExp().toString();
    private static final String VALID_JOBDESCRIPTION = RECRUITMENT_EXAMPLE.getJobDescription().toString();

    @Test
    public void toModelType_validRecruitmentDetails_returnsRecruitment() throws Exception {
        XmlAdaptedRecruitment recruitment = new XmlAdaptedRecruitment(RECRUITMENT_EXAMPLE);
        assertEquals(RECRUITMENT_EXAMPLE, recruitment.toModelPost());
    }

    //-------------------------------------------//
    @Test
    public void toModelType_invalidPost_throwsIllegalValueException() {
        XmlAdaptedRecruitment recruitment =
                new XmlAdaptedRecruitment (VALID_POST, INVALID_WORKEXP, VALID_JOBDESCRIPTION);
        String expectedMessage = WorkExp.MESSAGE_WORK_EXP_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recruitment::toModelPost);
    }

    @Test
    public void toModelType_nullWorkExp_throwsIllegalValueException() {
        XmlAdaptedRecruitment recruitment =
                new XmlAdaptedRecruitment (VALID_POST, null, VALID_JOBDESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WorkExp.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recruitment::toModelPost);
    }

    //-------------------------------------------//
    @Test
    public void toModelType_invalidJobDescription_throwsIllegalValueException() {
        XmlAdaptedRecruitment recruitment =
                new XmlAdaptedRecruitment (VALID_POST, VALID_WORKEXP, INVALID_JOBDESCRIPTION);
        String expectedMessage = JobDescription.MESSAGE_JOB_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recruitment::toModelPost);
    }

    @Test
    public void toModelType_nullJobDescription_throwsIllegalValueException() {
        XmlAdaptedRecruitment recruitment =
                new XmlAdaptedRecruitment (VALID_POST, VALID_WORKEXP, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, JobDescription.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recruitment::toModelPost);
    }

    //-------------------------------------------//

    @Test
    public void toModelPost_invalidPost_throwsIllegalValueException() {
        XmlAdaptedRecruitment recruitment =
                new XmlAdaptedRecruitment(INVALID_POST, VALID_WORKEXP, VALID_JOBDESCRIPTION);
        String expectedMessage = Post.MESSAGE_POST_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recruitment::toModelPost);
    }

    @Test
    public void toModelType_nullPost_throwsIllegalValueException() {
        XmlAdaptedRecruitment recruitment =
                new XmlAdaptedRecruitment(null, VALID_WORKEXP, VALID_JOBDESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Post.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recruitment::toModelPost);
    }
}

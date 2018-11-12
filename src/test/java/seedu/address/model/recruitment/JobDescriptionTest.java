package seedu.address.model.recruitment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class JobDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new JobDescription(null));
    }

    @Test
    public void constructor_invalidJobDescription_throwsIllegalArgumentException() {
        String jobDescription = "";
        Assert.assertThrows(NumberFormatException.class, () -> new JobDescription(jobDescription));
    }

    @Test
    public void isValidJobDescription() {
        // null working experience
        Assert.assertThrows(NullPointerException.class, () -> JobDescription.isValidJobDescription(null));

        // invalid job description
        assertFalse(JobDescription.isValidJobDescription("")); // empty string
        assertFalse(JobDescription.isValidJobDescription("31")); // digits not allowed
        assertFalse(JobDescription.isValidJobDescription("IT Manager1")); // alphabets with digits
        assertFalse(JobDescription.isValidJobDescription("To ensure company server!")); // Punctuation marks
        // accept only comma, full stop and single right quote
        assertFalse(JobDescription.isValidJobDescription("To ensure company server#&^%$@*")); //Punctuation marks
        // accept only comma, full stop and single right quote
        assertFalse(JobDescription.isValidJobDescription("To ensure company server To ensure company server To "
                + "ensure company server To ensure company server "
                + "To ensure company server To ensure company server To ensure company server To ensure company "
                + "serve and")); //exceed 200 character limits

        // valid job description
        assertTrue(JobDescription.isValidJobDescription("T")); //within 200 character limits
        assertTrue(JobDescription.isValidJobDescription("To maintain company's server, and IT "
                + "framework of the entire company.")); //within 200 character limits and accepts punctuation
        // marks of comma, full stop and single right quote
        assertTrue(JobDescription.isValidJobDescription("To ensure company server To ensure company server To "
                + "ensure company server To ensure company server "
                + "To ensure company server To ensure company server To ensure "
                + "company server To ensure company serv")); // 200 characters

    }

}

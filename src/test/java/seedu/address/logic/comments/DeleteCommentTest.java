package seedu.address.logic.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.DeleteCommentCommand;

class DeleteCommentTest {
    private String comments =
            "<span>Comment Section</span>\n"
                    + "<ol>\n"
                    + "<li>hello</li>\n"
                    + "<li>My name is Gerald</li>\n"
                    + "<li>Yup</li>\n"
                    + "<li>REPLY--->HArlo</li>\n"
                    + "<li>REPLY--->my name is</li>\n"
                    + "<li>REPLY--->Chua</li>\n"
                    + "<li>hello</li>\n"
                    + "<li>there</li>\n"
                    + "<li>hello</li>\n"
                    + "</ol>";

    private String testcase =
            "<span>Comment Section</span>\n"
                    + "<ol>\n"
                    + "<li>hello</li>\n"
                    + "<li>My name is Gerald</li>\n"
                    + "<li>Yup</li>\n"
                    + "<li>REPLY--->my name is</li>\n"
                    + "<li>REPLY--->Chua</li>\n"
                    + "<li>hello</li>\n"
                    + "<li>there</li>\n"
                    + "<li>hello</li>\n"
                    + "</ol>";
    @Test
    void deleteComment_correctInput_success() throws Exception {
        DeleteComment test = new DeleteComment(comments);
        assertEquals(testcase, test.deleteComment(4));
    }

    @Test
    void deleteComment_incorrectInput_failure() throws Exception {
        DeleteComment test = new DeleteComment(comments);
        assertNotEquals(testcase, test.deleteComment(3));
    }

    @Test
    void deleteComment_lineNotPresent_exceptionThrown() throws Exception {
        DeleteComment test = new DeleteComment(comments);
        try {
            assertNotEquals(testcase, test.deleteComment( 15));
        } catch (Exception e) {
            assertEquals("Line is invalid, try again", DeleteCommentCommand.MESSAGE_LINE_INVALID);
        }
    }
}

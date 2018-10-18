package seedu.address.logic.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReplyCommentCommand;

class ReplyCommentTest {
    private String comments =
            "<span>Comment Section</span>\n"
                    + "<ol>\n"
                    + "<li>hello</li>\n"
                    + "<li>My name is Gerald</li>\n"
                    + "<li>Yup</li>\n"
                    + "<li>REPLY--->HArlo</li>\n"
                    + "<li>REPLY--->my name is</li>\n"
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
                    + "<li>REPLY--->HArlo</li>\n"
                    + "<li>REPLY--->my name is</li>\n"
                    + "<li>REPLY--->Chua</li>\n"
                    + "<li>hello</li>\n"
                    + "<li>there</li>\n"
                    + "<li>hello</li>\n"
                    + "</ol>";
    @Test
    void replyComment_correctInput_success() throws Exception {
        ReplyComment test = new ReplyComment(comments);
        assertEquals(testcase, test.replyComment("Chua", 5));
    }

    @Test
    void replyComment_incorrectInput_failure() throws Exception {
        ReplyComment test = new ReplyComment(comments);
        assertNotEquals(testcase, test.replyComment("Chu", 5));
    }

    @Test
    void replyComment_lineNotPresent_exceptionThrown() throws Exception {
        ReplyComment test = new ReplyComment(comments);
        try {
            assertNotEquals(testcase, test.replyComment("Chua", 15));
        } catch (Exception e) {
            assertEquals("Line is invalid, try again", ReplyCommentCommand.MESSAGE_LINE_INVALID);
        }
    }


}

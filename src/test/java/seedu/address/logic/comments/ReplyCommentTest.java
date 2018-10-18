package seedu.address.logic.comments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void replyComment() throws Exception {
        ReplyComment test = new ReplyComment(comments);
        assertEquals(testcase, test.replyComment("Chua", 5));
    }
}

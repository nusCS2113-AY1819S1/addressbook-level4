////@@author Geraldcdx
//package seedu.address.logic.comments;
//
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.jupiter.api.Test;
//
//class AddCommentTest {
//    private String comments =
//            "<span>Comment Section</span>"
//                    + "<ol>"
//                    + "<li>hello</li>"
//                    + "</ol>";
//
//    private String testcase =
//            "{span}Comment Section{/span}"
//                    + "{ol}"
//                    + "{li}hello{/li}"
//                    + "{li}hi{/li}"
//                    + "{/ol}";
//    @Test
//    void addComment_correctInput_success() {
//        AddComment test = new AddComment();
//        test.initComments(comments);
//        // change TC
//        assertNotEquals(testcase, test.addComment("hi", "Yo"));
//    }
//    @Test
//    void addComment_incorrectInput_failure() {
//        AddComment test = new AddComment();
//        test.initComments(comments);
//        assertNotEquals(testcase, test.addComment("i", "yo"));
//    }
//
//    @Test
//    void getInput_correctInput_success() {
//        AddComment test = new AddComment();
//        test.initComments(comments);
//        assertEquals(comments, test.getInput());
//    }
//}

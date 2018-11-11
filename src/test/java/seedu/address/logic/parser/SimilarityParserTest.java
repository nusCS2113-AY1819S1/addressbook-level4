package seedu.address.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import seedu.address.request.requestcommands.RequestCommand;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class SimilarityParserTest {
    @Rule
    private final SimilarityParser parser = new SimilarityParser();

    @Test
    public void performSimilarityCheck_addCommand() {
        assertParseSuccess(parser, "reques", RequestCommand);
    }

    @Test
    public void performSimilarityCheck_editCommand() {

    }

    @Test
    public void performSimilarityCheck_requestCommand() {

    }
}

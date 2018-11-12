package seedu.divelog.logic.parser;

import static seedu.divelog.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.divelog.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.divelog.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.divelog.commons.enums.Units;
import seedu.divelog.logic.commands.SetUnitsCommand;

//@@author arjo129
public class SetUnitsParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetUnitsCommand.MESSAGE_USAGE);
    @Test
    public void setUnitsParser_testNormalCasesOk() {
        SetUnitsCommand expectedMeters = new SetUnitsCommand(Units.METERS);
        SetUnitsCommand expectedFeet = new SetUnitsCommand(Units.FEET);
        SetUnitsCommandParser parser = new SetUnitsCommandParser();
        assertParseSuccess(parser, "meters", expectedMeters);
        assertParseSuccess(parser, "feet", expectedFeet);
    }
    @Test
    public void setUnitsParser_testNonsenseEnteredFails() {
        SetUnitsCommandParser parser = new SetUnitsCommandParser();
        assertParseFailure(parser, "0cd0e993", MESSAGE_INVALID_FORMAT);
    }
}

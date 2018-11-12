package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetPriorityLevelCommand;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.testutil.TypicalIndexes;

public class SetPriorityLevelCommandParserTest {
    private SetPriorityLevelCommandParser parser = new SetPriorityLevelCommandParser();

    @Test
    void parse_missingParts_failure() {
        String setPlvlCommand = SetPriorityLevelCommand.COMMAND_WORD + " %s %s";
        // no index specified
        assertParseFailure(parser, String.format(setPlvlCommand, null, "0"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPriorityLevelCommand.MESSAGE_USAGE));

        // no field specified
        assertParseFailure(parser, String.format(setPlvlCommand, "1", null),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPriorityLevelCommand.MESSAGE_USAGE));

        // no index and no field specified
        assertParseFailure(parser, String.format(setPlvlCommand, null, null),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPriorityLevelCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        String setPlvlCommand = " iWantCrashThisApp " + CliSyntax.PREFIX_PRIORITYLEVEL
                + PriorityLevelEnum.BASIC;
        assertParseFailure(parser, setPlvlCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPriorityLevelCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPriorityLevel_failure() {
        String setPlvlCommand = " 1 " + CliSyntax.PREFIX_PRIORITYLEVEL + "9999";
        assertParseFailure(parser, setPlvlCommand,
                PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);

        setPlvlCommand = " 1 " + CliSyntax.PREFIX_PRIORITYLEVEL + "-6.3";
        assertParseFailure(parser, setPlvlCommand,
                PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);

        setPlvlCommand = " 1 " + CliSyntax.PREFIX_PRIORITYLEVEL + "LimpehTryToCrashThisApp";
        assertParseFailure(parser, setPlvlCommand,
                PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
    }

    @Test
    void parse_validParameters_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_PERSON;
        PriorityLevel expectedPlvl = new PriorityLevel(PriorityLevelEnum.BASIC.getPriorityLevelCode());
        SetPriorityLevelCommand expectedCommandReturn = new SetPriorityLevelCommand(targetIndex, expectedPlvl);

        String spl = String.valueOf(targetIndex.getOneBased()) + " " + CliSyntax.PREFIX_PRIORITYLEVEL
                + expectedPlvl.priorityLevelCode;
        assertParseSuccess(parser, spl, expectedCommandReturn);
    }
}

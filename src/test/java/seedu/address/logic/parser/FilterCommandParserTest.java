package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.EQUIPMENT_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.EQUIPMENT_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_BOB_WORKOUT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.workout.DurationPredicate;
import seedu.address.model.workout.EquipmentPredicate;
import seedu.address.model.workout.TypePredicate;



public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_failure() {
        //filter type only
        assertParseFailure(parser, TYPE_DESC_AMY_WORKOUT, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        //filter type and duration only
        assertParseFailure(parser, TYPE_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        //filter type and equipment only
        assertParseFailure(parser, TYPE_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        FilterCommand.MESSAGE_USAGE));

        //filter equipment and duration only
        assertParseFailure(parser, EQUIPMENT_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        FilterCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new DurationPredicate(Arrays.asList("25m")),
                        new TypePredicate(Arrays.asList("strength")),
                        new EquipmentPredicate(Arrays.asList("dumbbell")));
        String userInput = TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT;

        assertParseSuccess(parser, userInput, expectedFilterCommand);
    }
}

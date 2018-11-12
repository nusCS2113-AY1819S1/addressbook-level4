package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EARLY_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LATE_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_EVENT2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.person.EventName;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.TheDate;
import seedu.address.model.person.Time;
import seedu.address.testutil.ScheduleBuilder;

public class ScheduleCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE);

    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, DATE_DESC_EVENT1 + START_TIME_DESC_EVENT1 + END_TIME_DESC_EVENT1
                + EVENT_NAME_DESC_EVENT1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
        // no date specified
        assertParseFailure(parser, "1" + START_TIME_DESC_EVENT1 + END_TIME_DESC_EVENT1
                + EVENT_NAME_DESC_EVENT1, MESSAGE_INVALID_FORMAT);
        // no start time specified
        assertParseFailure(parser, "1" + DATE_DESC_EVENT1 + END_TIME_DESC_EVENT1
                + EVENT_NAME_DESC_EVENT1, MESSAGE_INVALID_FORMAT);
        // no end time specified
        assertParseFailure(parser, "1" + DATE_DESC_EVENT1 + START_TIME_DESC_EVENT1
                + EVENT_NAME_DESC_EVENT1, MESSAGE_INVALID_FORMAT);
        // no event name specified
        assertParseFailure(parser, "1" + DATE_DESC_EVENT1 + START_TIME_DESC_EVENT1
                + END_TIME_DESC_EVENT1, MESSAGE_INVALID_FORMAT);
        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DATE_DESC_EVENT1 + START_TIME_DESC_EVENT1 + END_TIME_DESC_EVENT1
                + EVENT_NAME_DESC_EVENT1, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DATE_DESC_EVENT1 + START_TIME_DESC_EVENT1 + END_TIME_DESC_EVENT1
                + EVENT_NAME_DESC_EVENT1, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DATE_DESC + START_TIME_DESC_EVENT1 + END_TIME_DESC_EVENT1
                + EVENT_NAME_DESC_EVENT1, TheDate.MESSAGE_DATE_CONSTRAINTS); // invalid date followed by valid fields

        assertParseFailure(parser, "1" + DATE_DESC_EVENT1 + INVALID_START_TIME_DESC + END_TIME_DESC_EVENT1
                + EVENT_NAME_DESC_EVENT1, Time.MESSAGE_TIME_CONSTRAINTS); // invalid start time with other valid fields

        assertParseFailure(parser, "1" + DATE_DESC_EVENT1 + START_TIME_DESC_EVENT1 + INVALID_END_TIME_DESC
                + EVENT_NAME_DESC_EVENT1, Time.MESSAGE_TIME_CONSTRAINTS); // invalid end time with other valid fields

        assertParseFailure(parser, "1" + DATE_DESC_EVENT1 + START_TIME_DESC_EVENT1 + END_TIME_DESC_EVENT1
                + INVALID_EVENT_NAME_DESC,
                EventName.MESSAGE_EVENT_NAME_CONSTRAINTS); // invalid event name with other valid fields

        // valid date followed by invalid date. The test case for invalid date followed by valid date
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + VALID_DATE_EVENT1 +INVALID_DATE_DESC + START_TIME_DESC_EVENT1
                + END_TIME_DESC_EVENT1 + EVENT_NAME_DESC_EVENT1, TheDate.MESSAGE_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DATE_DESC + INVALID_START_TIME_DESC + END_TIME_DESC_EVENT1
                + EVENT_NAME_DESC_EVENT1, TheDate.MESSAGE_DATE_CONSTRAINTS);

        //start time is later than end time
        assertParseFailure(parser, "1" + DATE_DESC_EVENT1 + INVALID_LATE_START_TIME_DESC
                + INVALID_EARLY_END_TIME_DESC + EVENT_NAME_DESC_EVENT1, Schedule.MESSAGE_START_END_CONSTRAINTS);
    }


        @Test
        public void parse_multipleRepeatedFields_acceptsLast() { //accepts last
            Index targetIndex = INDEX_FIRST_PERSON;
            String userInput = targetIndex.getOneBased() + DATE_DESC_EVENT1 + START_TIME_DESC_EVENT1
                    + END_TIME_DESC_EVENT1 + EVENT_NAME_DESC_EVENT1 + DATE_DESC_EVENT2 + START_TIME_DESC_EVENT2
                    + END_TIME_DESC_EVENT2 + EVENT_NAME_DESC_EVENT2;

            Schedule schedule = new ScheduleBuilder().withTheDate(VALID_DATE_EVENT2)
                    .withStartTime(VALID_START_TIME_EVENT2)
                    .withEndTime(VALID_END_TIME_EVENT2)
                    .withEventName(VALID_EVENT_NAME_EVENT2).build();

            ScheduleCommand expectedCommand = new ScheduleCommand(schedule, targetIndex);

            assertParseSuccess(parser, userInput, expectedCommand);
        }

        @Test
        public void parse_invalidValueFollowedByValidValue_success() { //invalid date followed by valid
            // invalid date followed by valid date
            Index targetIndex = INDEX_FIRST_PERSON;
            String userInput = targetIndex.getOneBased() + INVALID_DATE_DESC + START_TIME_DESC_EVENT1
                    + END_TIME_DESC_EVENT1 + EVENT_NAME_DESC_EVENT1 + DATE_DESC_EVENT2 + START_TIME_DESC_EVENT2
                    + END_TIME_DESC_EVENT2 + EVENT_NAME_DESC_EVENT2;

            Schedule schedule = new ScheduleBuilder().withTheDate(VALID_DATE_EVENT1)
                    .withStartTime(VALID_START_TIME_EVENT1)
                    .withEndTime(VALID_END_TIME_EVENT1)
                    .withEventName(VALID_EVENT_NAME_EVENT1).build();

            ScheduleCommand expectedCommand = new ScheduleCommand(schedule, targetIndex);

            assertParseSuccess(parser, userInput, expectedCommand);

        }

        @Test
        public void parse_allFieldsSpecified_success() { //all valid
            Index targetIndex = INDEX_SECOND_PERSON;
            String userInput = targetIndex.getOneBased() + DATE_DESC_EVENT1 + START_TIME_DESC_EVENT1
                    + END_TIME_DESC_EVENT1 + EVENT_NAME_DESC_EVENT1;

            Schedule schedule = new ScheduleBuilder().withTheDate(VALID_DATE_EVENT1)
                    .withStartTime(VALID_START_TIME_EVENT1)
                    .withEndTime(VALID_END_TIME_EVENT1)
                    .withEventName(VALID_EVENT_NAME_EVENT1).build();

            ScheduleCommand command = new ScheduleCommand(schedule, targetIndex);

            assertParseSuccess(parser, userInput, command);
        }

}

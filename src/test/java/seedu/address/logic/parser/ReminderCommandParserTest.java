package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalReminders.REMINDER1;

import org.junit.Test;

import seedu.address.logic.commands.ReminderCommand;
import seedu.address.model.todo.Title;
//import seedu.address.model.person.Date;
import seedu.address.model.person.Time;
//import seedu.address.model.reminder.Title;
import seedu.address.model.reminder.Date;
//import seedu.address.model.reminder.Time;
import seedu.address.model.reminder.Agenda;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.ReminderBuilder;

//@@author junweiljw
public class ReminderCommandParserTest {
    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Reminder expectedReminder = new ReminderBuilder(REMINDER1).withTitle(VALID_REMINDER1_TITLE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TIME_DESC_REMINDER1 + DATE_DESC_REMINDER1
                + TIME_DESC_REMINDER1 + AGENDA_DESC_REMINDER1, new ReminderCommand(expectedReminder));

        // multiple titles - last title accepted
        assertParseSuccess(parser, TITLE_DESC_REMINDER2 + TITLE_DESC_REMINDER1 + DATE_DESC_REMINDER1
                + TIME_DESC_REMINDER1 + AGENDA_DESC_REMINDER1, new ReminderCommand(expectedReminder));

        // multiple dates - last date accepted
        assertParseSuccess(parser, TITLE_DESC_REMINDER1 + DATE_DESC_REMINDER2 + DATE_DESC_REMINDER1
                + TIME_DESC_REMINDER1 + AGENDA_DESC_REMINDER1, new ReminderCommand(expectedReminder));

        // multiple times - last time accepted
        assertParseSuccess(parser, TITLE_DESC_REMINDER1 + DATE_DESC_REMINDER1 + TIME_DESC_REMINDER2
                + TIME_DESC_REMINDER1 + AGENDA_DESC_REMINDER1, new ReminderCommand(expectedReminder));

        // multiple agendas - last agenda accepted
        assertParseSuccess(parser, TITLE_DESC_REMINDER1 + DATE_DESC_REMINDER1 + TIME_DESC_REMINDER1
                + AGENDA_DESC_REMINDER2 + AGENDA_DESC_REMINDER1, new ReminderCommand(expectedReminder));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_REMINDER1_TITLE + DATE_DESC_REMINDER1 + TIME_DESC_REMINDER1
                + AGENDA_DESC_REMINDER1, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, TITLE_DESC_REMINDER1 + VALID_REMINDER1_DATE + TIME_DESC_REMINDER1
                + AGENDA_DESC_REMINDER1, expectedMessage);

        // missing time prefix
        assertParseFailure(parser, TITLE_DESC_REMINDER1 + DATE_DESC_REMINDER1 + VALID_REMINDER1_TIME
                + AGENDA_DESC_REMINDER1, expectedMessage);

        // missing agenda prefix
        assertParseFailure(parser, TITLE_DESC_REMINDER1 + DATE_DESC_REMINDER1 + TIME_DESC_REMINDER1
                + VALID_REMINDER1_AGENDA, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_REMINDER1_TITLE + VALID_REMINDER1_DATE + VALID_REMINDER1_TIME
                + VALID_REMINDER1_AGENDA, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + DATE_DESC_REMINDER1 + TIME_DESC_REMINDER1
                + AGENDA_DESC_REMINDER1, Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, TITLE_DESC_REMINDER1 + INVALID_DATE_DESC + TIME_DESC_REMINDER1
                + AGENDA_DESC_REMINDER1, Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, TITLE_DESC_REMINDER1 + DATE_DESC_REMINDER1 + INVALID_TIME_DESC
                + AGENDA_DESC_REMINDER1, Time.MESSAGE_TIME_CONSTRAINTS);

        // invalid agenda
        assertParseFailure(parser, TITLE_DESC_REMINDER1 + DATE_DESC_REMINDER1 + TIME_DESC_REMINDER1
                + INVALID_AGENDA_DESC, Agenda.MESSAGE_AGENDA_CONSTRAINTS);
    }
}

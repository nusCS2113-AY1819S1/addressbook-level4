package seedu.planner.logic.parser;

import org.junit.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.commands.DeleteCommandByDateEntry;
import seedu.planner.testutil.TypicalDates;

public class DeleteCommandByDateEntryParserTest {
    private DeleteCommandByDateEntryParser parser = new DeleteCommandByDateEntryParser();

    @Test
    public void parse_validArgs_returnsDeleteCommandByDateEntry() {
        CommandParserTestUtil.assertParseSuccess(
                parser, TypicalDates.DATE_FIRST_INDEX_DATE.value,
                new DeleteCommandByDateEntry(TypicalDates.DATE_FIRST_INDEX_DATE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(
                parser, "31/02/1999",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommandByDateEntry.MESSAGE_USAGE));
    }
}

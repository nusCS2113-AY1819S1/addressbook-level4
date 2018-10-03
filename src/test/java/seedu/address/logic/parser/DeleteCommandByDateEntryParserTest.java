package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.DeleteCommandByDateEntry;
import seedu.address.testutil.TypicalDates;

import java.security.MessageDigestSpi;

public class DeleteCommandByDateEntryParserTest {
    private  DeleteCommandByDateEntryParser parser = new DeleteCommandByDateEntryParser();

    @Test
    public void parse_validArgs_returnsDeleteCommandByDateEntry() {
        CommandParserTestUtil.assertParseSuccess(
                parser, TypicalDates.DATE_FIRST_INDEX_DATE.toString(), new DeleteCommandByDateEntry(TypicalDates.DATE_FIRST_INDEX_DATE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(
                parser, "31/03/1999", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommandByDateEntry.MESSAGE_USAGE));
    }
}

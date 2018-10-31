package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FreeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FreeCommand object
 */
public class FreeCommandParser implements Parser<FreeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FreeCommand
     * and returns an FreeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FreeCommand parse(String args) throws ParseException {
        try {
            Collection <Index> indices = new ArrayList<>();

            for (String string : Arrays.asList(args.trim().split("\\s+"))) {
                indices.add(ParserUtil.parseIndex(string));
            }

            return new FreeCommand(indices);

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE), pe);
        }
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ModuleFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a {@code ModuleFindCommand} object.
 */
public class ModuleFindCommandParser implements Parser<ModuleFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ModuleFindCommand}
     * and returns a {@code ModuleFindCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ModuleFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleFindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        return new ModuleFindCommand(Arrays.asList(keywords));
    }
}

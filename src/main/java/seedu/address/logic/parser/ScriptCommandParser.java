package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEXT_FILE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ScriptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.script.CommandType;
import seedu.address.model.script.TextFile;

/**
 * Parses input arguments and creates a new ScriptCommand object
 */
public class ScriptCommandParser implements Parser<ScriptCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ScriptCommand
     * and returns an ScriptCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScriptCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TEXT_FILE, PREFIX_COMMAND);

        if (!arePrefixesPresent(argMultimap, PREFIX_TEXT_FILE, PREFIX_COMMAND)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScriptCommand.MESSAGE_USAGE));
        }

        TextFile textFile = ParserUtil.parseTextFile(argMultimap.getValue(PREFIX_TEXT_FILE).get());
        CommandType commandType = ParserUtil.parseCommandType(argMultimap.getValue(PREFIX_COMMAND).get());

        return new ScriptCommand(textFile, commandType);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

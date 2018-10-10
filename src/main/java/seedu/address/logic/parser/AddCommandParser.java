package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.task.*;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_MODULE, PREFIX_DATE, PREFIX_PRIORITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK, PREFIX_MODULE, PREFIX_DATE, PREFIX_PRIORITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        TaskName taskName= ParserUtil.parseName(argMultimap.getValue(PREFIX_TASK).get());
        TaskModule taskModule = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE).get());
        TaskDate taskDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        TaskPriority taskPriority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());

        Task task = new Task(taskName, taskModule, taskDate, taskPriority);

        return new AddCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

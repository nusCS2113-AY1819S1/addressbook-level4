package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public AddTaskCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_ACTION, PREFIX_DESCRIPTION, PREFIX_PRIORITY, PREFIX_HOURS);

        if (!arePrefixesPresent(argMultimap, PREFIX_ACTION, PREFIX_DESCRIPTION, PREFIX_PRIORITY, PREFIX_HOURS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        String title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_ACTION).get());
        String description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        PriorityLevel priority = ParserUtil.parsePriorityLevel(argMultimap.getValue(PREFIX_PRIORITY).get());
        int expectedNumOfHours = ParserUtil.parseHours(argMultimap.getValue(PREFIX_HOURS).get());

        Task task = new Task(title, description, priority, expectedNumOfHours);

        return new AddTaskCommand(task);
    }
}

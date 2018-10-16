package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    @Override
    public AddTaskCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_PRIORITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_PRIORITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        String title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        String description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        PriorityLevel priority = ParserUtil.parsePriorityLevel(argMultimap.getValue(PREFIX_PRIORITY).get());

        Task task = new Task(title, description, priority);

        return new AddTaskCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

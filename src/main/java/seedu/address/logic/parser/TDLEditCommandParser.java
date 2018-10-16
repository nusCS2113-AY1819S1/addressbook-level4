package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;



import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TDLEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class TDLEditCommandParser implements Parser<TDLEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TDLEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_MODULE, PREFIX_DATE, PREFIX_PRIORITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TDLEditCommand.MESSAGE_USAGE), pe);
        }

        TDLEditCommand.TDLEditTaskDescriptor editTaskDescriptor = new TDLEditCommand.TDLEditTaskDescriptor();
        if (argMultimap.getValue(PREFIX_TASK).isPresent()) {
            editTaskDescriptor.setName(ParserUtil.parseTask(argMultimap.getValue(PREFIX_TASK).get()));
        }
        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            editTaskDescriptor.setModule(ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editTaskDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editTaskDescriptor.setPriority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }
        return new TDLEditCommand(index, editTaskDescriptor);
    }
}

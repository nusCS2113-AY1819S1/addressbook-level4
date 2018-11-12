package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FinishTodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author linnnruoo
/**
 * Parses input arguments and creates a new FinishTodo object
 */
public class FinishTodoCommandParser implements Parser<FinishTodoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FinishTodoCommand
     * and returns an FinishTodoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FinishTodoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FinishTodoCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FinishTodoCommand.MESSAGE_USAGE), pe);
        }
    }

}

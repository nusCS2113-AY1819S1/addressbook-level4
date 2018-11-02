package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.commands.SortCommand.ASCENDING_CONDITION;
import static seedu.planner.logic.commands.SortCommand.CATEGORY_AND_ORDER_SPECIFIED;
import static seedu.planner.logic.commands.SortCommand.CATEGORY_NAME;
import static seedu.planner.logic.commands.SortCommand.CATEGORY_SET;
import static seedu.planner.logic.commands.SortCommand.DESCENDING_CONDITION;
import static seedu.planner.logic.commands.SortCommand.ONLY_CATEGORY_OR_ORDER_SPECIFIED;
import static seedu.planner.logic.commands.SortCommand.ORDER_SET;

import seedu.planner.logic.commands.SortCommand;
import seedu.planner.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)));
        }

        String category;
        Boolean ascending;
        String[] argList = trimmedArgs.split("\\s+");

        if ((argList.length) == ONLY_CATEGORY_OR_ORDER_SPECIFIED) {
            if (!CATEGORY_SET.contains(argList[0]) && !ORDER_SET.contains(argList[0])) {
                throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)));
            }
            category = argList[0];
            ascending = true;
            if (category.equals(DESCENDING_CONDITION)) {
                category = CATEGORY_NAME;
                ascending = false;
            } else if (category.equals(ASCENDING_CONDITION)) {
                category = CATEGORY_NAME;
            }
        } else if (argList.length == CATEGORY_AND_ORDER_SPECIFIED) {
            if ((ORDER_SET.contains(argList[0]) || ORDER_SET.contains(argList[1]))
                    && (CATEGORY_SET.contains(argList[0])
                    || CATEGORY_SET.contains(argList[1]))) {
                if (ORDER_SET.contains(argList[0])) {
                    ascending = !(argList[0].equals(DESCENDING_CONDITION));
                    category = argList[1];
                } else {
                    ascending = (argList[1].equals(ASCENDING_CONDITION));
                    category = argList[0];
                }
            } else {
                throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)));
            }
        } else {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)));
        }

        return new SortCommand(category, ascending);
    }

}

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

public class SortCommandParser implements Parser<SortCommand> {

    public SortCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new SortCommand("name", true);
        }

        String category;
        Boolean ascending;
        String[] argList = trimmedArgs.split("\\s+");

        if ((argList.length) == ONLY_CATEGORY_OR_ORDER_SPECIFIED){
            if (!CATEGORY_SET.contains(argList[0]) && !ORDER_SET.contains(argList[0])){
                throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)));
            }
            category = argList[0];
            ascending = true;
            if (category.equals(DESCENDING_CONDITION)){
                category = CATEGORY_NAME;
                ascending = false;
            } else if (category.equals(ASCENDING_CONDITION)){
                category = CATEGORY_NAME;
            }
        } else if (argList.length == CATEGORY_AND_ORDER_SPECIFIED){
            if ((ORDER_SET.contains(argList[0]) || ORDER_SET.contains(argList[1]))
                    && (CATEGORY_SET.contains(argList[0]) || CATEGORY_SET.contains(argList[1]))){
                if (ORDER_SET.contains(argList[0])){
                    ascending = (argList[0] == DESCENDING_CONDITION) ? false : true;
                    category = argList[1];
                }
                else {
                    category = argList[0];
                    ascending = (argList[1] == ASCENDING_CONDITION) ? true : false;
                }
            }
            else {
                throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)));
            }
        } else {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)));
        }

        return new SortCommand(category, ascending);
    }

}

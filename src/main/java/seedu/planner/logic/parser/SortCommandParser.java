package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.commands.SortCommand.DESCENDING_CONDITION;
import static seedu.planner.logic.commands.SortCommand.ONLY_CATEGORY_SPECIFIED;

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
        if ((argList.length) == ONLY_CATEGORY_SPECIFIED){
            category = argList[0];
            ascending = true;
        } else if (argList.length == SortCommand.CATEGORY_AND_ORDER_SPECIFIED){
            category = argList[0];
            ascending = !(argList[1] == DESCENDING_CONDITION);
        } else {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)));
        }

        return new SortCommand(category, ascending);
    }

}

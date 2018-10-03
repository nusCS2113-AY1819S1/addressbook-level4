package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.regex.Pattern;

public class TrajectoryParser {

    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord1>\\S+)(\\s*)(?<commandWord2>\\S+)(\\s*)(?<arguments>.*)"); // to be updated

//    public Command parseCommand(String userInput) throws ParseException {
//
//    }
}

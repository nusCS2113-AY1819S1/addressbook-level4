package seedu.address.logic.parser;

import java.util.regex.Pattern;

/**
 * This is a parser.
 */
public class TrajectoryParser {

    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord1>\\S+)(\\s*)(?<commandWord2>\\S+)(\\s*)(?<arguments>.*)"); // to be updated

//    public Command parseCommand(String userInput) throws ParseException {
//
//    }
}

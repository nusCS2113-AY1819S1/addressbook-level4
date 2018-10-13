package seedu.address.logic.commands;

import seedu.address.logic.parser.exceptions.ParseException;

//@@author Chelsey
/**
 * CommandParser is able to
 * pass in arguments
 */
public interface CommandParser {
    public Command parse(String arguments) throws ParseException;

    public String getCommandWord();
}

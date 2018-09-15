package seedu.address.logic.parser;

import seedu.address.logic.commands.PasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class PasswordCommandParser implements Parser<PasswordCommand>{

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PasswordCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        System.out.println(nameKeywords[0]); // Read off the argument

        return new PasswordCommand();
    }


}

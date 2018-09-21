package seedu.address.logic.parser;

import seedu.address.storage.PasswordManager;
import seedu.address.logic.commands.PasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import java.io.File;


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

        String[] credentials = trimmedArgs.split("\\s+");

        if (credentials.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.MESSAGE_USAGE));
        }

        String message;
        File f = new File("password.txt");
        if(f.exists() && !f.isDirectory()) {
            message = PasswordManager.authenticate(credentials[1], credentials[0]);

        } else {
            message = "Password stored successfully!";
            message += "\nhash: " + PasswordManager.storePassword(credentials[1], credentials[0]);
        }

        return new PasswordCommand(message);
    }


}

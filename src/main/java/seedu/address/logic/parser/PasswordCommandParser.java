package seedu.address.logic.parser;

import seedu.address.commons.util.Passwords;
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

        String[] password = trimmedArgs.split("\\s+");

        if (password.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.MESSAGE_USAGE));
        }


        byte hash[] = Passwords.hash(password[1].toCharArray(), password[0].getBytes());
//        System.out.println(hash);

        return new PasswordCommand(hash);
    }


}

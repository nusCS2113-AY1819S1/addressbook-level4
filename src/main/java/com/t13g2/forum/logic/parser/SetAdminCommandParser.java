package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_ADMIN_SET;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;

import java.util.stream.Stream;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.commands.SetAdminCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

//@@xllx1
/**
 * Parses input arguments and setor revert a certain user as admin.
 */
public class SetAdminCommandParser implements Parser<SetAdminCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SetAdminCommand.
     * and returns an SetAdminCommand object for execution.
     * @param userInput
     * @return
     * @throws ParseException
     */
    public SetAdminCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_USER_NAME, PREFIX_ADMIN_SET);
        if (!arePrefixesPresent(argMultimap, PREFIX_USER_NAME, PREFIX_ADMIN_SET)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                SetAdminCommand.MESSAGE_USAGE));
        }

        String userName = argMultimap.getValue(PREFIX_USER_NAME).get();
        boolean setAdmin;
        if (argMultimap.getValue(PREFIX_ADMIN_SET).get().equals("true")) {
            setAdmin = true;
        } else if (argMultimap.getValue(PREFIX_ADMIN_SET).get().equals("false")) {
            setAdmin = false;
        } else {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                SetAdminCommand.MESSAGE_USAGE));
        }

        return new SetAdminCommand(userName, setAdmin);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

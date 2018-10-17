package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.login.UserId.MESSAGE_USERID_CONSTRAINTS;
import static seedu.address.model.login.UserPassword.MESSAGE_USERPASSWORD_CONSTRAINTS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import seedu.address.logic.commands.CreateAccountCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.model.login.UserPassword;

/**
 * Parses input arguments and creates a new CreateAccountCommand object
 */
public class CreateAccountCommandParser implements Parser<CreateAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateAccountCommand
     * and returns an CreateAccountCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateAccountCommand parse(String args) throws ParseException, CommandException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAccountCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        List<String> keywordsList = new ArrayList<String>(Arrays.asList(keywords));

        StringTokenizer st = new StringTokenizer(args);

        UserId userId = null;
        UserPassword userPassword = null;
        for (int i = 1; st.hasMoreTokens(); i++) {
            if (i == 2) {
                String tokenUserId = st.nextToken();
                if (!UserId.isValidUserId(tokenUserId)) {
                    throw new CommandException(MESSAGE_USERID_CONSTRAINTS);
                }
                userId = new UserId(tokenUserId);
            } else if (i == 3) {
                String tokenUserPassword = st.nextToken();
                if (!UserPassword.isValidUserPassword(tokenUserPassword)) {
                    throw new CommandException(MESSAGE_USERPASSWORD_CONSTRAINTS);
                }
                userPassword = new UserPassword(tokenUserPassword);
            }
        }
        LoginDetails details = new LoginDetails(userId, userPassword);

        return new CreateAccountCommand(new UserIdContainsKeywordsPredicate(keywordsList), details);
    }
}

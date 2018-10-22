package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.parseUserId;
import static seedu.address.logic.parser.ParserUtil.parseUserPassword;
import static seedu.address.logic.parser.ParserUtil.parseUserRole;

import java.util.StringTokenizer;

import seedu.address.logic.commands.CreateAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserPassword;
import seedu.address.model.login.UserRole;


/**
 * Parses input arguments and creates a new CreateAccountCommand object
 */
public class CreateAccountCommandParser implements Parser<CreateAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateAccountCommand
     * and returns an CreateAccountCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public CreateAccountCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAccountCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        LoginDetails details = extractLoginDetailsFromInput(keywords, args);
        return new CreateAccountCommand(details);
    }

    /**
     * Constructs and returns a LoginDetails object from the input of user account credentials
     * @param keywords string array representation of login user input
     * @param args from input of user account credentials
     * @return LoginDetails object constructed from parsed user input consisting of userId, userPassword and userRole
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LoginDetails extractLoginDetailsFromInput(String[] keywords, String args) throws ParseException {
        if (keywords.length != 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAccountCommand.MESSAGE_USAGE));
        } else {
            StringTokenizer st = new StringTokenizer(args);
            UserId userId = null;
            UserPassword userPassword = null;
            UserRole userRole = null;
            for (int i = 1; st.hasMoreTokens(); i++) {
                if (i == 2) {
                    userId = parseUserId(st.nextToken());
                } else if (i == 3) {
                    userPassword = parseUserPassword(st.nextToken());
                } else if (i == 4) {
                    userRole = parseUserRole(st.nextToken());
                }
            }
            return new LoginDetails(userId, userPassword, userRole);
        }
    }
}

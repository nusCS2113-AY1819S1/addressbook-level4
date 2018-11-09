package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author: IcedCoffeeBoy

/**
 * Parses user input.
 */
public class LoginCommandParser implements Parser<LoginCommand> {
    public static final String KEY_MANAGER = "manager";
    public static final String KEY_EMPLOYEE = "employee";
    private static final String SPECIAL_CHARACTERS = "!#$%&'*+/=?`{|}~^.-";
    private static final String LOCAL_PART_REGEX = "^[\\w" + SPECIAL_CHARACTERS + "]+";
    private static final String DOMAIN_FIRST_CHARACTER_REGEX = "[^\\W_]"; // alphanumeric characters except underscore
    private static final String DOMAIN_MIDDLE_REGEX = "[a-zA-Z0-9.-]*"; // alphanumeric, period and hyphen
    private static final String DOMAIN_LAST_CHARACTER_REGEX = "[^\\W_]$";
    public static final String EMAIL_VALIDATION_REGEX = LOCAL_PART_REGEX + "@"
            + DOMAIN_FIRST_CHARACTER_REGEX + DOMAIN_MIDDLE_REGEX + DOMAIN_LAST_CHARACTER_REGEX;


    private static final String MESSAGE_INVALID_LOGIN = "Login identity should be either the following:"
            + "\nmanager\nemployee\nas EMAIL"
            + "\nExample: login manager"
            + "\nExample: login as hello@gmail.com";


    /**
     * Parses user input into command for execution.
     *
     * @param args full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (isArgsAs(args) && isEmailEmpty(args)) {
            throw new ParseException(MESSAGE_INVALID_LOGIN);
        } else if (isArgsAs(args) && isArgsEmail(args)) {
            String emailArgs = extractEmail(args);
            return new LoginCommand(emailArgs, 1);
        } else if (trimmedArgs.equalsIgnoreCase("manager")) {
            return new LoginCommand(KEY_MANAGER, 2);
        } else if (trimmedArgs.equalsIgnoreCase("employee")) {
            return new LoginCommand(KEY_EMPLOYEE, 3);
        } else {
            throw new ParseException(MESSAGE_INVALID_LOGIN);
        }

    }

    private boolean isEmailEmpty(String args) {
        return args.trim().split("\\s+").length == 1;
    }

    /**
     * Checks if first word of the userInput is equals to "as"
     * @return boolean true if it is equal, if not, false
     */
    private boolean isArgsAs(String userInput) {
        return userInput.trim().split("\\s+")[0]
                .equalsIgnoreCase("as");
    }

    /**
     * Checks if second word of the userInput is equals to an email
     * @return boolean true if it is equal, if not, false
     */
    private boolean isArgsEmail(String userInput) {
        String specificEmail = userInput.trim().split("\\s+")[1];
        return isEmailValid(specificEmail);
    }

    /**
     * Checks in userInput is of valid email type
     * @return boolean true if is of valid type, if not, false
     */
    private boolean isEmailValid(String userInput) {
        Pattern pattern = Pattern.compile(EMAIL_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(userInput);
        return matcher.matches();
    }

    /**
     * Extracts email
     * @return email
     */
    private String extractEmail(String userInput) {
        String emailArgs = userInput.trim().split("\\s+")[1];
        return emailArgs;
    }
}

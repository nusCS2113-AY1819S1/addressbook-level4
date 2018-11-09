package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandsParser;
import seedu.address.logic.parser.EmployeeParser;
import seedu.address.logic.parser.ManagerParser;
import seedu.address.model.Model;
import seedu.address.model.person.Email;


//@@author: IcedCoffeeBoy
/**
 * Login the person into ProManage
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";
    public static final String MESSAGE_INVALID_LOGIN = "Login identity should be either the following:"
            + "\nmanager\nemployee\nas EMAIL"
            + "\nExample: login manager"
            + "\nExample: login as hello@gmail.com";

    private static final String KEY_MANAGER = "manager";
    private static final String KEY_EMPLOYEE = "employee";

    private static final String MESSAGE_SUCCESS = "Successfully login as %s";

    private String loginIdentity;
    /**
     * type == 1 -> argument is an email, there is a need to check if email is present in addressbook and check the
     * person's designation to see whether the person is a manager or employee
     * type == 2 -> argument is a manager
     * type == 3 -> argument is an employee
     */
    private final int type;
    private Model model;

    public LoginCommand(String arguments, int type) {
        requireNonNull(arguments);
        requireNonNull(type);
        this.loginIdentity = arguments;
        this.type = type;
    }

    public CommandsParser getParser(Model model) throws CommandException {
        this.model = model;

        if (type == 1 && isEmailPresent(loginIdentity)) {
            if (isEmailManager(loginIdentity)) {
                loginIdentity = KEY_MANAGER;
            } else if (isEmailEmployee(loginIdentity)) {
                loginIdentity = KEY_EMPLOYEE;
            } else {
                throw new CommandException(MESSAGE_INVALID_LOGIN);
            }
        }
        switch (loginIdentity) {
        case KEY_MANAGER:
            return new ManagerParser();
        case KEY_EMPLOYEE:
            return new EmployeeParser();
        default:
            throw new CommandException(MESSAGE_INVALID_LOGIN);
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(String.format(MESSAGE_SUCCESS, loginIdentity));
    }

    /**
     *
     * @param loginIdentity input
     * @return true or false depending if the email is present in the addressbook
     */
    private boolean isEmailPresent(String loginIdentity) {
        return model.hasEmail(new Email(loginIdentity));
    }

    /**
     *
     * @param loginIdentity input
     * @return true or false depending if the login identity is manager
     */
    private boolean isEmailManager(String loginIdentity) {
        return model.getPerson(new Email(loginIdentity))
            .getDesignation().toString().equalsIgnoreCase("manager");
    }

    /**
     *
     * @param loginIdentity input
     * @return true or false depending if the login identity is employee
     */
    private boolean isEmailEmployee(String loginIdentity) {
        //return model.getPerson(new Email(loginIdentity)).get().getDesignation().equals(new Designation("employee"));
        return model.getPerson(new Email(loginIdentity)).getDesignation().toString()
            .equalsIgnoreCase("employee");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand
                && ((loginIdentity == null && ((LoginCommand) other).loginIdentity == null) // instanceof handles nulls
                || loginIdentity.equals(((LoginCommand) other).loginIdentity)));
    }
}

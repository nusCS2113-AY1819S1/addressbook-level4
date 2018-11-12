package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATEOFBIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.DateOfBirthContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.EmployeeIdContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_EMPLOYEEID + "EMPLOYEEID "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATEOFBIRTH + "DATEOFBIRTH "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_DEPARTMENT + "DEPARTMENT "
            + PREFIX_POSITION + "POSITION "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_SALARY + "SALARY "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMPLOYEEID + "008888 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DATEOFBIRTH + "03/12/1993 "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_DEPARTMENT + "Finance "
            + PREFIX_POSITION + "Intern "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_SALARY + "1000.00 "
            + PREFIX_TAG + "FlyKite";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_EMPLOYEEID = "This employee ID already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_DUPLICATE_EMAIL = "This email already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PHONE = "This phone already exists in the address book";

    private static boolean isEmailDuplicated = false;
    private static boolean isPhoneDuplicated = false;
    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    public static void setIsEmailDuplicated(boolean verifyEmailDuplication) {
        isEmailDuplicated = verifyEmailDuplication;
    }

    public static void setIsPhoneDuplicated(boolean verifyPhoneDuplication) {
        isPhoneDuplicated = verifyPhoneDuplication;
    }

    /**
     * Execution of the command will depend on whether there are duplicated EmployeeIds, Email, Phone or Name &
     * DateOfBirth. If any of the duplicated check is true, an exception will be thrown, otherwise,
     * the command will be executed accordingly.
     * @param model The actual model
     * @param history The actual history
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // Checks for duplicated employee id
        if (model.hasEmployeeId(toAdd)) {
            EmployeeIdContainsKeywordsPredicate employeeIdPredicate =
                    new EmployeeIdContainsKeywordsPredicate(toAdd.getEmployeeId().value);
            model.updateFilteredPersonList(employeeIdPredicate);
            throw new CommandException(MESSAGE_DUPLICATE_EMPLOYEEID);
        // Checks for duplicated email
        } else if (model.hasPerson(toAdd) && isEmailDuplicated && !isPhoneDuplicated) {
            EmailContainsKeywordsPredicate emailPredicate =
                    new EmailContainsKeywordsPredicate(toAdd.getEmail().value);
            model.updateFilteredPersonList(emailPredicate);
            throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
        // Checks for duplicated phone
        } else if (model.hasPerson(toAdd) && !isEmailDuplicated && isPhoneDuplicated) {
            PhoneContainsKeywordsPredicate phonePredicate =
                    new PhoneContainsKeywordsPredicate(toAdd.getPhone().value);
            model.updateFilteredPersonList(phonePredicate);
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        // Checks for duplicated name & date of birth
        } else if (model.hasPerson(toAdd) && ((!isEmailDuplicated && !isPhoneDuplicated))) {
            NameContainsKeywordsPredicate namePredicate =
                    new NameContainsKeywordsPredicate(Collections.singletonList(toAdd.getName().fullName));
            DateOfBirthContainsKeywordsPredicate dobPredicate =
                    new DateOfBirthContainsKeywordsPredicate(toAdd.getDateOfBirth().value);
            model.updateFilteredPersonList(namePredicate.and(dobPredicate));
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

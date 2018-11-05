package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.events.logic.RegisterSuccessEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.security.SecurityAuthenticationException;

/**
 * Adds a person to the address book.
 */
public class RegisterCommand extends Command {

    public static final String COMMAND_WORD = "register";
    public static final String COMMAND_WORD_ALIAS = "re";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Registers a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person registered: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;
    private final String password;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public RegisterCommand(Person person, String password) {
        requireNonNull(person);
        toAdd = person;
        this.password = password;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history)
            throws CommandException, SecurityAuthenticationException {
        requireNonNull(model);

        if (model.getUser() != null) {
            throw new SecurityAuthenticationException("Please logout if you would like register a new account");
        }

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }


        model.addPerson(toAdd);
        model.commitAddressBook();

        raise(new RegisterSuccessEvent(toAdd.getName().toString(), password));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterCommand // instanceof handles nulls
                && toAdd.equals(((RegisterCommand) other).toAdd));
    }
}

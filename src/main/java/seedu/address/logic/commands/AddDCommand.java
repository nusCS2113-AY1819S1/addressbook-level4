package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
// import seedu.address.model.person.Person;
import seedu.address.model.distributor.Distributor;

/**
 * Adds a person to the address book.
 */
public class AddDCommand extends Command {

    public static final String COMMAND_WORD = "addD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a distributor to the inventarie. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Ah Bee Distributors"
            + PREFIX_PHONE + "60123456 ";

    public static final String MESSAGE_SUCCESS = "New distributor added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This distributor already exists in the address book";

    private final Distributor toAdd;

    /**
     * Creates an AddDCommand to add the specified {@code Distributor}
     */
    public AddDCommand(Distributor distributor) {
        requireNonNull(distributor);
        toAdd = distributor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasDistributor(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addDistributor(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDCommand // instanceof handles nulls
                && toAdd.equals(((AddDCommand) other).toAdd));
    }
}

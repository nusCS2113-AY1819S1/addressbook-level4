package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.login.User;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.storage.XmlAdaptedUser;

import java.util.List;

public class CreateUserCommand extends Command {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": creates a new user and adds to the user database. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD ";


    public static final String MESSAGE_SUCCESS = "New user created: %1$s";
    public static final String MESSAGE_DUPLICATE_USER = "This username has already been taken.";

    private final User toCreate;

    /**
     * Creates a CreateUserCommand to add the specified {@code User}
     */
    public CreateUserCommand(User user) {
        requireNonNull(user);
        toCreate = user;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            model.addUser(toCreate);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toCreate.getUsername().fullUsername));
        } catch (DuplicateUserException e) {
            throw new CommandException(MESSAGE_DUPLICATE_USER);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateUserCommand // instanceof handles nulls
                && toCreate.equals(((CreateUserCommand) other).toCreate));
    }
}

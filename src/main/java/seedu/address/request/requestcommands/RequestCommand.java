//@@author guiyong96
package seedu.address.request.requestcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.request.Request;
import seedu.address.request.requestmodel.RequestModel;

/**
 * Adds a request to the BookInventory.
 */
public class RequestCommand extends CommandSecondary {

    public static final String COMMAND_WORD = "request";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Request a book. "
            + "Parameters: "
            + PREFIX_ISBN + "ISBN "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_EMAIL + "EMAIL "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ISBN + "9783161484100 "
            + PREFIX_QUANTITY + "42 "
            + PREFIX_EMAIL + "johnd@example.com ";

    public static final String MESSAGE_SUCCESS = "New request added: %1$s";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_EMAIL + " "
            + PREFIX_QUANTITY + " "
            + PREFIX_ISBN;

    private final Request toAdd;

    /**
     * Creates an RequestCommand to add the specified {@code Request}
     */
    public RequestCommand(Request request) {
        requireNonNull(request);
        toAdd = request;
    }

    @Override
    public CommandResult execute(RequestModel requestModel, CommandHistory history) throws CommandException {
        requireNonNull(requestModel);

        //if (requestModel.hasRequest(toAdd)) {
        //    throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        //}

        requestModel.addRequest(toAdd);
        requestModel.commitRequests();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RequestCommand // instanceof handles nulls
                && toAdd.equals(((RequestCommand) other).toAdd));
    }
}

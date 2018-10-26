package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.User;
import seedu.address.model.person.DeconflictTimeTable;
import seedu.address.model.person.Person;

/**
 * Selects a person identified using it's displayed index from the address book.
 */
public class FreeCommand extends Command {

    public static final String COMMAND_WORD = "free";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all common free timeslots between your timetable and the timetables of all selected people.\n"
            + "Parameters: [INDEX]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_FREE_SUCCESS = "Displaying free timeslots with people: ";

    private final Collection<Index> targetIndices;

    public FreeCommand(Collection <Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        User user = model.getUser();
        List<Person> friendList = model.getFriendList(user);

        DeconflictTimeTable deconflict = new DeconflictTimeTable(user.getTimeTable());

        for (Index index : targetIndices) {
            if (index.getZeroBased() >= friendList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person person = friendList.get(index.getZeroBased());
            deconflict.addTimeTable(person.getTimeTable());
        }

        model.updateTimeTable(deconflict);

        StringBuilder resultString = new StringBuilder(MESSAGE_FREE_SUCCESS);

        for (Index index : targetIndices) {
            resultString.append(index.getOneBased());
            resultString.append(' ');
        }

        return new CommandResult(resultString.toString().trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FreeCommand // instanceof handles nulls
                && targetIndices.equals(((FreeCommand) other).targetIndices)); // state check
    }
}

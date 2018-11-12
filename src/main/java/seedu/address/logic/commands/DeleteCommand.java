package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.NricContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.session.SessionManager;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    private static String nric;

    private static final String MESSAGE_CANNOT_DELETE_YOURSELF = "You can't delete yourself!";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        SessionManager sessionManager = SessionManager.getInstance(model);
        /**
         * Throws exception if user is not logged in.
         */
        if (!sessionManager.isLoggedIn()) {
            throw new CommandException(SessionManager.NOT_LOGGED_IN);
        }
        /**
         * Throws exception if user does not have the required access level.
         */
        if (!sessionManager.hasSufficientPriorityLevelForThisSession(PriorityLevelEnum.ADMINISTRATOR)) {
            throw new CommandException(String.format(PriorityLevel.INSUFFICIENT_PRIORITY_LEVEL,
                    PriorityLevelEnum.ADMINISTRATOR));
        }

        List<Person> lastShownList = model.getFilteredPersonList();
        List<Leave> lastShownLeaveList;
        NricContainsKeywordsPredicate keyword;

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        nric = personToDelete.getNric().nric;
        keyword = new NricContainsKeywordsPredicate(Arrays.asList(nric));

        if (personToDelete == sessionManager.getLoggedInPersonDetails()) {
            throw new CommandException(MESSAGE_CANNOT_DELETE_YOURSELF);
        }

        model.updateFilteredLeaveList(keyword);
        lastShownLeaveList = model.getFilteredLeaveList();
        model.deletePerson(personToDelete);

        while (lastShownLeaveList.size() != 0) {
            Leave leaveToDelete = lastShownLeaveList.get(0);
            model.deleteLeave(leaveToDelete);
        }
        model.updateFilteredLeaveList(Model.PREDICATE_SHOW_ALL_LEAVES);
        model.commitLeaveList();
        model.commitAddressBook();


        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}

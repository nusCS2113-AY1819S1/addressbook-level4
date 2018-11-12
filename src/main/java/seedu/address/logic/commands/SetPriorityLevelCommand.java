package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.EditLeaveCommand.createEditedLeave;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITYLEVEL;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.NricContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.session.SessionManager;

//@@author jylee-git
/**
 * This command sets the priority level of other users.
 */
public class SetPriorityLevelCommand extends Command {

    public static final String COMMAND_WORD = "setplvl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the priority level of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PRIORITYLEVEL + "PRIORITYLEVEL\n"
            + "Example: " + COMMAND_WORD + " 2 " + PREFIX_PRIORITYLEVEL + "3";

    public static final String MESSAGE_CHANGE_PLVL_SUCCESS = "Successfully changed the priority level of %s to %s";
    public static final String MESSAGE_CANNOT_EDIT_OWN_PLVL = "You can't edit your own priority level.";
    private static String nric;

    private final Index index;
    private final PriorityLevel priorityLevel;

    public SetPriorityLevelCommand (Index index, PriorityLevel priorityLevel) {
        requireAllNonNull (index, priorityLevel);
        this.index = index;
        this.priorityLevel = priorityLevel;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        SessionManager sessionManager = SessionManager.getInstance(model);

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

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        if (personToEdit == sessionManager.getLoggedInPersonDetails()) {
            throw new CommandException(MESSAGE_CANNOT_EDIT_OWN_PLVL);
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getNric(),
                personToEdit.getPassword(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getDepartment(),
                priorityLevel,
                personToEdit.getAddress(),
                personToEdit.getMode(),
                personToEdit.getWorkingRate(),
                personToEdit.getCheckedInTime(),
                personToEdit.getTags(),
                personToEdit.getSchedule());

        model.updatePerson(personToEdit, editedPerson);
        model.commitAddressBook();

        List<Leave> lastShownLeaveList;
        NricContainsKeywordsPredicate keyword;

        Person personToUpdate = lastShownList.get(index.getZeroBased());
        nric = personToUpdate.getNric().nric;
        keyword = new NricContainsKeywordsPredicate(Arrays.asList(nric));

        model.updateFilteredLeaveList(keyword);
        lastShownLeaveList = model.getFilteredLeaveList();

        EditLeaveDescriptor editLeaveDescriptor = new EditLeaveDescriptor();
        editLeaveDescriptor.setPriorityLevel(priorityLevel);

        for (int i = 0; i < lastShownLeaveList.size(); i++) {
            Leave leaveToUpdate = lastShownLeaveList.get(i);
            Leave editedLeave = createEditedLeave(leaveToUpdate, editLeaveDescriptor);
            model.updateLeave(leaveToUpdate, editedLeave);
        }
        model.updateFilteredLeaveList(Model.PREDICATE_SHOW_ALL_LEAVES);
        model.commitLeaveList();


        return new CommandResult(String.format(MESSAGE_CHANGE_PLVL_SUCCESS,
                editedPerson.getName(), priorityLevel));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetPriorityLevelCommand // instanceof handles nulls
                && (index.equals(((SetPriorityLevelCommand) other).index)
                && priorityLevel.equals(((SetPriorityLevelCommand) other).priorityLevel)));
    }
}

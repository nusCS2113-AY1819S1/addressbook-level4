package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Department;
import seedu.address.model.person.Person;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.session.SessionManager;

//@@author Woonhian
/**
 * This command sets the department of other users.
 */
public class SetDepartmentCommand extends Command {

    public static final String COMMAND_WORD = "setdepartment";
    public static final String COMMAND_ALIAS = "sd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the department of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DEPARTMENT + "DEPARTMENT\n"
            + "Example: " + COMMAND_WORD + " 2 " + PREFIX_DEPARTMENT + "Junior Management";

    public static final String MESSAGE_CHANGE_DEPARTMENT_SUCCESS = "Successfully changed the department of %s to %s";
    public static final String MESSAGE_CANNOT_EDIT_OWN_DEPARTMENT = "You can't edit your own department.";

    private final Index index;
    private final Department department;

    public SetDepartmentCommand (Index index, Department department) {
        requireAllNonNull (index, department);
        this.index = index;
        this.department = department;
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
            throw new CommandException(MESSAGE_CANNOT_EDIT_OWN_DEPARTMENT);
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getNric(),
                personToEdit.getPassword(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                department,
                personToEdit.getPriorityLevel(),
                personToEdit.getAddress(),
                personToEdit.getMode(),
                personToEdit.getWorkingRate(),
                personToEdit.getCheckedInTime(),
                personToEdit.getTags(),
                personToEdit.getSchedule());

        model.updatePerson(personToEdit, editedPerson);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_CHANGE_DEPARTMENT_SUCCESS,
                editedPerson.getName(), department));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetDepartmentCommand // instanceof handles nulls
                && (index.equals(((SetDepartmentCommand) other).index)
                && department.equals(((SetDepartmentCommand) other).department)));
    }
}

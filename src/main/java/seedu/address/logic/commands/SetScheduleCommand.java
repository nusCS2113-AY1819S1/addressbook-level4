package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.model.schedule.Schedule;
import seedu.address.session.SessionManager;

/**
 * Sets schedule of a person in the address book to the user.
 */
public class SetScheduleCommand extends Command {

    public static final String COMMAND_WORD = "setschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the schedule of the person identified"
            + "\nParameters: INDEX "
            + PREFIX_TIME_START + "START_TIME "
            + PREFIX_TIME_END + "END_TIME "
            + PREFIX_VENUE + "VENUE "
            + "\nExample: "
            + COMMAND_WORD + " 1 "
            + PREFIX_TIME_START + "1000 "
            + PREFIX_TIME_END + "1600 "
            + PREFIX_VENUE + "Toilet";

    public static final String MESSAGE_SCHEDULE_SUCCESS = "Set Schedule Successful";
    public static final String MESSAGE_SCHEDULE_FAIL = "Set Schedule Command Failed.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    public SetScheduleCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = editPersonDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        SessionManager sessionManager = SessionManager.getInstance(model);

        if (!sessionManager.isLoggedIn()) {
            throw new CommandException(SessionManager.NOT_LOGGED_IN);
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        /**
         * Throws exception if user does not have the required access level
         * and the login user is not the modified user.
         */
        if (!sessionManager.hasSufficientPriorityLevelForThisSession(PriorityLevelEnum.ADMINISTRATOR)
                && !(personToEdit.getNric().equals(sessionManager.getLoggedInSessionNric()))) {
            throw new CommandException(String.format(PriorityLevel.INSUFFICIENT_PRIORITY_LEVEL,
                    PriorityLevelEnum.ADMINISTRATOR));
        }
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        model.updatePerson(personToEdit, editedPerson);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SCHEDULE_SUCCESS, editedPerson));
    }

    /**
     * Creates an edited Schedule Object with given edit person descriptor
     * Set Schedule Command Parser will create a EditPersonDescriptor Object with only edited Schedule
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Schedule updatedSchedule = editPersonDescriptor.getSchedule().orElse(personToEdit.getSchedule());

        return new Person(personToEdit.getName(), personToEdit.getNric(), personToEdit.getPassword(),
                personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getDepartment(),
                personToEdit.getPriorityLevel(), personToEdit.getAddress(), personToEdit.getMode(),
                personToEdit.getWorkingRate(), personToEdit.getCheckedInTime(), personToEdit.getTags(),
                updatedSchedule);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.AttendeeContainsEmailPredicate;
import seedu.address.model.event.EventContainsAttendeeAndDatePredicate;
import seedu.address.model.event.TimeType;
import seedu.address.model.person.Person;

//@@author jieliangang
/**
 * Selects an employee identified using it's displayed index from the address book and
 * display the selected employee's events on the event list.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the employee identified by the index number used in the displayed person list "
            + "and display events the employee is attending. Show events in selected date/month/year if indicated\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_YEAR + "YEAR] "
            + "[" + PREFIX_MONTH + "MONTH] "
            + "If " + PREFIX_DATE + " is used, " + PREFIX_YEAR + " and " + PREFIX_MONTH + " must not be used\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DATE + "2018-05-24" + "   or  "
            + COMMAND_WORD + " 1 " + PREFIX_MONTH + "05";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";

    private final Index targetIndex;
    private final String date;
    private final TimeType type;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.date = null;
        this.type = TimeType.NONE;
    }

    public SelectCommand(Index targetIndex, String date, TimeType type) {
        this.targetIndex = targetIndex;
        this.date = date;
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> filteredPersonList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= filteredPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = filteredPersonList.get(targetIndex.getZeroBased());
        String personEmail = person.getEmail().toString();

        if (type == TimeType.NONE) {
            AttendeeContainsEmailPredicate predicateNoDateFilter = new AttendeeContainsEmailPredicate(personEmail);
            model.updateFilteredEventList(predicateNoDateFilter);
        } else if (type == TimeType.DAY || type == TimeType.MONTH
                || type == TimeType.YEAR || type == TimeType.MONTH_AND_YEAR) {
            assert date != null;
            EventContainsAttendeeAndDatePredicate predicateWithDateFilter =
                    new EventContainsAttendeeAndDatePredicate(personEmail, date, type);
            model.updateFilteredEventList(predicateWithDateFilter);
        } else {
            throw new CommandException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }

        model.sortByDate();

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex) // state check
                && (((date == null && (((SelectCommand) other).date) == null)) // short circuit if both are null
                    || ((date != null && (((SelectCommand) other).date) != null)
                    && date.equals(((SelectCommand) other).date)))
                && type.equals(((SelectCommand) other).type));
    }
}

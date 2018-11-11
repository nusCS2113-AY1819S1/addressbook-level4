package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.Parameter.isValidParameter;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Parameter;
import seedu.address.model.person.Person;

/**
 * Sorts all persons in the address book by specified criteria.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Sorted %1$d people";
    public static final String MESSAGE_USAGE = "Error: Failed to sort. Please enter your command in the following"
            + "format:\nsort st/[PARAMETER]\nValid Parameters: 'name', 'skill', 'sl'";

    private final Parameter parameter;

    public SortCommand(Parameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> filteredPersonList = new ArrayList<>(model.getFilteredPersonList());
        try {
            filteredPersonList.sort(Person.getComparator(parameter));
        } catch (IllegalArgumentException ive) {
            throw new CommandException(MESSAGE_USAGE);
        }
        for (Person person: filteredPersonList) {
            model.deletePerson(person);
        }
        for (Person person: filteredPersonList) {
            model.addPerson(person);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, filteredPersonList.size()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }
        // state check
        SortCommand e = (SortCommand) other;
        return parameter.equals(e.parameter);
    }
}

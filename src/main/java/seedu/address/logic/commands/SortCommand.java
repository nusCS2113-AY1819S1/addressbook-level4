package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Parameter;
import seedu.address.model.person.Person;

/**
 * Sorts all persons in the address book by specified criteria.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_ARGUMENTS = "Parameter: %1$s";
    public static final String MESSAGE_SUCCESS = "Sorted %1$d people";

    private final Parameter parameter;

    public SortCommand(Parameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Person> filteredPersonList = new ArrayList<>(model.getFilteredPersonList());
        filteredPersonList.sort(Person.getByName());
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

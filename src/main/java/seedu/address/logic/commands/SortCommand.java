package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
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
    private static Predicate<Person> predicate;

    private final Parameter parameter;

    public SortCommand(Parameter parameter) {
        this.parameter = parameter;
    }

    public static void setPredicateForSort(Predicate<Person> sortPredicate) {
        predicate = sortPredicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Person> filteredPersonList = new ArrayList<>(model.getFilteredPersonList());
        try {
            filteredPersonList.sort(Person.getComparator(parameter));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Person person: filteredPersonList) {
            model.deletePerson(person);
        }
        for (Person person: filteredPersonList) {
            model.addPerson(person);
        }
        model.executeSearch(predicate);
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

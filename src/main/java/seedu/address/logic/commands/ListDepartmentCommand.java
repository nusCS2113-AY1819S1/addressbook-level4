package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

//@@author Woonhian
/**
 * Lists all departments in the address book to the user.
 */
public class ListDepartmentCommand extends Command {

    public static final String COMMAND_WORD = "listdepartment";
    public static final String COMMAND_ALIAS = "ld";
    public static final String MESSAGE_SUCCESS = "List of departments available:\n%1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<Person> lastShownList = model.getFilteredPersonList();
        List<String> departments = new ArrayList<>();

        for (Person department : lastShownList) {
            departments.add(department.getDepartment().toString());
        }

        Set<String> hs = new HashSet<>(departments);
        departments.clear();
        departments.addAll(hs);

        Collections.sort(departments);

        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join(", ", departments)));
    }
}

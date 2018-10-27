package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.HtmlTableProcessor;

/**
 * Lists all persons in the address book to the user.
 */
public class StudentListCommand extends Command {

    public static final String COMMAND_WORD = "student list";

    public static final String MESSAGE_SUCCESS = "Listed all students";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        // TO RETAIN BACKWARDS COMPATIBILITY
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        requireNonNull(model);
        StringBuilder sb = new StringBuilder();

        sb.append(HtmlTableProcessor.getH3Representation("Student List"));
        sb.append(HtmlTableProcessor.renderTableStart(new ArrayList<String>(
                Arrays.asList("Matric No", "Full Name", "Email Address"))));

        sb.append(HtmlTableProcessor.getTableItemStart());
        for (Person p: model.getAddressBook().getPersonList()) {
            sb.append(HtmlTableProcessor
                    .renderTableItem(new ArrayList<String>(Arrays
                            .asList(p.getMatricNo().toString(), p.getName().toString(), p.getEmail().toString()))));
        }
        sb.append(HtmlTableProcessor.getTableItemEnd());

        return new CommandResult(MESSAGE_SUCCESS + "\n" + "", sb.toString());
    }
}

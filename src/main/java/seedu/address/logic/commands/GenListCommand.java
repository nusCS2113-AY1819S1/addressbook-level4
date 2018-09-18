package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Generate a attendance list for all the people in the address book
 * Keyword matching is case insensitive.
 */
public class GenListCommand extends Command {

    public static final String COMMAND_WORD = "genlist";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        for(Person p : model.getFilteredPersonList()){
            System.out.print(p.getName() + " ");
        }
        System.out.print("\n");
        return new CommandResult(String.format(Messages.MESSAGE_GENERATE_ATTENDANCE_LIST));
    }
}

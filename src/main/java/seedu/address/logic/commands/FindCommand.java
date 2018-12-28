package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose prefix contains any of the argument keyword.
 * Keyword matching is case insensitive.
 * v1.2 find by 1 keyword for name, phone, email, address, tag with 1 keyword only
 * v1.3 will find by different newly implemented categories with multiple keywords each
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    //@@author chokxy
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose prefix contain any "
            + "of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: PREFIX/ KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " t/ " + "Stroke diaBETes \n"
            + "Personal prefixes:\t n/ (Name)\t p/ (Phone)\t e/ (Email)\t\t\t a/ (Address)\t\t t/ (tag)\t ic/ (NRIC)\n"
            + "Medical prefixes:\t i/ (MedInfo)\t hsa/ (Allergy)\t hsb/ (BloodType)\t hsc/ (LastCountry)";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}

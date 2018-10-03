package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.FileEncryptor;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = CliSyntax.COMMAND_LIST;

    public static final int TYPE_ALL = 0;
    public static final int TYPE_TAG = 1;

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    /**
     * Instance variables
     */
    private int listType;
    private List<String> predicatesList;

    public ListCommand(int listType, List<String> predicatesList) {
        this.listType = listType;
        this.predicatesList = predicatesList;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        FileEncryptor fe = new FileEncryptor("data/addressbook.xml");
        Predicate<Person> predicateToUse;

        if (fe.isLocked()) {
            throw new CommandException(FileEncryptor.MESSAGE_ADDRESS_BOOK_LOCKED);
        }

        switch(listType) {
        case TYPE_TAG:
            System.out.println("tag");
        default:
            predicateToUse = PREDICATE_SHOW_ALL_PERSONS;
        }

        model.updateFilteredPersonList(predicateToUse);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

package com.t13g2.forum.logic.commands;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.person.Person;

import static java.util.Objects.requireNonNull;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_INDEX;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT;

/**
 * Create a new thread to the forum book under certain module.
 */
/**
 * create m/MODULE_NAME t/THREAD_NAME [c/REPLY_MESSAGE]…​
 */

public class CreateThreadCommand extends Command {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a thread to the forum book. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_THREAD + "THREAD "
            + "[" + PREFIX_COMMENT + "COMMENT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2113 "
            + PREFIX_THREAD + "EXAM DISCUSSION "
            + PREFIX_COMMENT + "Hi fellows, what is the topic coverage for the final?";

    public static final String MESSAGE_SUCCESS = "New thread added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This thread already exists in the forum book";

    private final Person toAdd;

    /**
     * Creates a CreateThreadCommand to create the specified {@code Person}
     */
    public CreateThreadCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        model.commitForumBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
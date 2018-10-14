package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.AddGroup;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Add persons to a group via their respective indices
 */
public class AddGroupCommand extends Command {
    public static final String COMMAND_WORD = "addgroup";
    public static final String COMMAND_WORD_2 = "addgrp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds persons to a group specified. "
            + "Parameters: "
            + PREFIX_GROUP_INDEX + "GROUP_INDEX "
            + PREFIX_PERSON_INDEX + "PERSON_INDEX...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP_INDEX + "1 "
            + PREFIX_PERSON_INDEX + "2";

    public static final String MESSAGE_SUCCESS = "Person index(s) added to group at index %1$s";
    public static final String MESSAGE_DUPLICATE_PERSONS = "Person(s) already exist in group";

    private final AddGroup toAdd;

    /**
     * Creates an AddGroupCommand to add persons to group
     * specified in {@code AddGroup}
     */
    public AddGroupCommand(AddGroup toAdd){
        requireAllNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Group> lastShownGroupList = model.getFilteredGroupList();

        if (!toAdd.validGroupIndex(lastShownGroupList.size())) {
            throw new CommandException(MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }else if (!toAdd.validPersonIndexSet(lastShownPersonList.size())) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        toAdd.setPersonSet(lastShownPersonList);
        toAdd.setGroupSet(lastShownGroupList);

        if (model.hasPersonInGroup(toAdd)){
            throw new CommandException(MESSAGE_DUPLICATE_PERSONS);
        }

        model.addGroup(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGroupCommand // instanceof handles nulls
                && toAdd.equals(((AddGroupCommand) other).toAdd));
    }

}

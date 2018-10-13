package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.AddGroup;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

public class AddGroupCommand extends Command {
    public static final String COMMAND_WORD = "addgroup";
    public static final String COMMAND_WORD_2 = "addgrp";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds persons to the group identified "
            + "by the index numbers used in the last person listing.\n"
            + "Parameters: "
            + PREFIX_NAME + "GROUP_NAME "
            + PREFIX_PERSON_INDEX + "INDEX(S)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "CS1231 "
            + PREFIX_PERSON_INDEX + "1 2 3";
    public static final String MESSAGE_SUCCESS = "Index(s) added to group %1$s";

    public final AddGroup addGroup;

    public AddGroupCommand(AddGroup addGroup){
        requireAllNonNull(addGroup);
        this.addGroup = addGroup;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Group> lastShownGroupList = model.getFilteredGroupList();

        if (addGroup.validGroupName(lastShownGroupList) == false) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_NAME);
        }else if (addGroup.validPersonIndexsSet(lastShownPersonList.size()) == false) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        throw new CommandException(String.format(MESSAGE_SUCCESS,addGroup.toString()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddGroupCommand)) {
            return false;
        }
        // state check
        AddGroupCommand e = (AddGroupCommand) other;
        return addGroup.equals(e.addGroup);
    }

}

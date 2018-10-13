package seedu.address.logic;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

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

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Add group command not implemented yet";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}

package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Skill;

/**
 * Adds a skill for a person in the Addressbook.
 */
public class AddSkillCommand extends Command {

    public static final String COMMAND_WORD = "addskill";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the skill of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing skill will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_SKILL + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SKILL + "Can swim.";

    public static final String MESSAGE_SUCCESS = "New skill added: %1$s";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";
    private final Index index;
    private final Skill skill;
    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param skill of the person to be updated to
     */
    public AddSkillCommand(Index index, Skill skill) {
        requireAllNonNull(index, skill);
        this.index = index;
        this.skill = skill;
    }

    /**
     * Creates an AddSkillCommand to add the specified {@code Skill}
     */

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), skill));
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddSkillCommand)) {
            return false;
        }
        // state check
        AddSkillCommand e = (AddSkillCommand) other;
        return index.equals(e.index)
                && skill.equals(e.skill);
    }
}

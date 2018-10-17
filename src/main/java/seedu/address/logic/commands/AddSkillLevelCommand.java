package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLLEVEL;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
/**
 * Changes the skill of an existing person in the address book, with a tagged skill level.
 */

public class AddSkillLevelCommand extends Command {
    public static final String COMMAND_WORD = "asl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the skill of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing skill will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_SKILL + "[SKILL] " + PREFIX_SKILLLEVEL + "[LEVEL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SKILL + "Photography " + PREFIX_SKILLLEVEL + "4\n";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Skill: %2$s, Level: %3$d";
    private final Index index;
    private final String skill;
    private final int level;
    /**
     * @param index of the person in the filtered person list to edit the skill
     * @param skill of the person to be added
     * @param level of the skill that the person has
     */
    public AddSkillLevelCommand(Index index, String skill, int level) {
        requireAllNonNull(index, skill);
        this.index = index;
        this.skill = skill;
        this.level = level;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), skill, level));
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddSkillLevelCommand)) {
            return false;
        }
        // state check
        AddSkillLevelCommand e = (AddSkillLevelCommand) other;
        return index.equals(e.index)
                && skill.equals(e.skill);
    }
}

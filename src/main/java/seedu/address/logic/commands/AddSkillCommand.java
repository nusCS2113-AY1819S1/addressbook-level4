package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Skill;

//@@author derpyplops-reused
//{The implementation is a simple renaming of the "remark" example in the Dev Guide.
// Code from it is also reused throughout the project.
// Which will be extended later to other uses.}

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

    public static final String MESSAGE_ADD_SKILL_SUCCESS = "Added skill to person: %1$s";
    public static final String MESSAGE_DELETE_SKILL_SUCCESS = "Remove skill to person: %1$s";
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
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), skill, personToEdit.getTags());
        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(generateSuccessMessage(editedPerson));
    }
    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !skill.value.isEmpty() ? MESSAGE_ADD_SKILL_SUCCESS : MESSAGE_DELETE_SKILL_SUCCESS;
        return String.format(message, personToEdit);
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

//@@author

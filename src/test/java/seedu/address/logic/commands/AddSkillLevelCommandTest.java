package seedu.address.logic.commands;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddSkillLevelCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.LoginBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for AddSkillLevelCommand.
 */
public class AddSkillLevelCommandTest {
    private Model model = new ModelManager(new LoginBook(), getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final String skill = "Some remark";
        final int level = 3;
        assertCommandFailure(new AddSkillLevelCommand(INDEX_FIRST_PERSON, skill, level), model, new CommandHistory(),
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), skill, level));
    }
    @Test
    public void equals() {
        final AddSkillLevelCommand standardCommand = new AddSkillLevelCommand(INDEX_FIRST_PERSON,
                VALID_SKILL_AMY, VALID_SKILL_LEVEL_AMY);
        // same values -> returns true
        AddSkillLevelCommand commandWithSameValues = new AddSkillLevelCommand(INDEX_FIRST_PERSON,
                VALID_SKILL_AMY, VALID_SKILL_LEVEL_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));    
        // different index -> returns false
        assertFalse(standardCommand.equals(new AddSkillLevelCommand(INDEX_SECOND_PERSON,
                VALID_SKILL_AMY, VALID_SKILL_LEVEL_AMY)));
        // different remark -> returns false
        assertFalse(standardCommand.equals(new AddSkillLevelCommand(INDEX_FIRST_PERSON,
                VALID_SKILL_BOB, VALID_SKILL_LEVEL_BOB)));
    }
}

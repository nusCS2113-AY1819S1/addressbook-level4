package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAccounts.getTypicalLoginBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.LoginBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Skill;
import seedu.address.model.person.SkillLevel;
import seedu.address.testutil.PersonBuilder;

//@@author derpyplops-reused
//{The implementation is a simple renaming of the "remark" example in the Dev Guide, extended slightly
// Code from it is also reused throughout the project.
// Which will be extended later to other uses.}


/**
 * Contains integration tests (interaction with the Model) and unit tests for AddSkillLevelCommand.
 */
public class AddSkillLevelCommandTest {
    private static final String SKILL_STUB = "Some skill";
    private static final int SKILLLEVEL_STUB = 99;
    private Model model = new ModelManager(getTypicalLoginBook(), getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    @Test
    public void execute_addAddSkillLevelUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withSkill(SKILL_STUB).build();
        AddSkillLevelCommand addSkillCommand = new AddSkillLevelCommand(
                INDEX_FIRST_PERSON,
                new Skill(editedPerson.getSkill().value),
                new SkillLevel(editedPerson.getSkillLevel().skillLevel)
        );
        String expectedMessage = String.format(AddSkillLevelCommand.MESSAGE_ADD_SKILL_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new LoginBook(model.getLoginBook()),
                new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addSkillCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_deleteAddSkillLevelUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withSkill("").build();
        AddSkillLevelCommand addSkillCommand = new AddSkillLevelCommand(
                INDEX_FIRST_PERSON,
                new Skill(editedPerson.getSkill().value),
                new SkillLevel(editedPerson.getSkillLevel().skillLevel)
        );
        String expectedMessage = String.format(AddSkillLevelCommand.MESSAGE_DELETE_SKILL_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new LoginBook(model.getLoginBook()),
                new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addSkillCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withSkill(SKILL_STUB).build();
        AddSkillLevelCommand addSkillCommand = new AddSkillLevelCommand(
                INDEX_FIRST_PERSON,
                new Skill(editedPerson.getSkill().value),
                new SkillLevel(editedPerson.getSkillLevel().skillLevel)
        );
        String expectedMessage = String.format(AddSkillLevelCommand.MESSAGE_ADD_SKILL_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new LoginBook(model.getLoginBook()),
                new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addSkillCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddSkillLevelCommand addSkillCommand = new AddSkillLevelCommand(outOfBoundIndex,
                new Skill(VALID_SKILL_BOB), new SkillLevel(VALID_SKILL_LEVEL_BOB));
        assertCommandFailure(addSkillCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        AddSkillLevelCommand addSkillCommand = new AddSkillLevelCommand(outOfBoundIndex,
                new Skill(VALID_SKILL_BOB), new SkillLevel(VALID_SKILL_LEVEL_BOB));
        assertCommandFailure(addSkillCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withSkill(SKILL_STUB).build();
        AddSkillLevelCommand addSkillCommand = new AddSkillLevelCommand(INDEX_FIRST_PERSON,
                new Skill(SKILL_STUB), new SkillLevel(SKILLLEVEL_STUB));
        Model expectedModel = new ModelManager(new LoginBook(model.getLoginBook()),
                new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();
        // skill -> first person skill changed
        addSkillCommand.execute(model, commandHistory);
        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        // redo -> same first person modified again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }
    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddSkillLevelCommand addSkillCommand = new AddSkillLevelCommand(outOfBoundIndex,
                new Skill(""), new SkillLevel(SKILLLEVEL_STUB));
        // execution failed -> address book state not added into model
        assertCommandFailure(addSkillCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
    /**
     * 1. Modifies {@code Person#skill} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        AddSkillLevelCommand addSkillCommand = new AddSkillLevelCommand(INDEX_FIRST_PERSON,
                new Skill(SKILL_STUB), new SkillLevel(SKILLLEVEL_STUB));
        Model expectedModel = new ModelManager(new LoginBook(model.getLoginBook()),
                new AddressBook(model.getAddressBook()), new UserPrefs());
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withSkill(SKILL_STUB).build();
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();
        // skill -> modifies second person in unfiltered person list / first person in filtered person list
        addSkillCommand.execute(model, commandHistory);
        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> modifies same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }
    @Test
    public void equals() {
        final AddSkillLevelCommand standardCommand = new AddSkillLevelCommand(INDEX_FIRST_PERSON,
                new Skill(VALID_SKILL_AMY), new SkillLevel(VALID_SKILL_LEVEL_AMY));

        // same values -> returns true
        AddSkillLevelCommand commandWithSameValues = new AddSkillLevelCommand(INDEX_FIRST_PERSON,
                new Skill(VALID_SKILL_AMY), new SkillLevel(VALID_SKILL_LEVEL_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddSkillLevelCommand(INDEX_SECOND_PERSON,
                new Skill(VALID_SKILL_AMY), new SkillLevel(VALID_SKILL_LEVEL_AMY))));

        // different skill -> returns false
        assertFalse(standardCommand.equals(new AddSkillLevelCommand(INDEX_FIRST_PERSON,
                new Skill(VALID_SKILL_BOB), new SkillLevel(VALID_SKILL_LEVEL_BOB))));
    }
}

package seedu.address.logic.commands;

import static seedu.address.logic.commands.ClassEditCommand.MESSAGE_EDIT_CLASSROOM_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.StorageController;
import seedu.address.model.UserPrefs;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;
import seedu.address.model.classroom.Enrollment;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.testutil.ClassroomBuilder;
import seedu.address.testutil.ModuleBuilder;

/**
 * Provides a test for the class edit command
 */
public class ClassEditCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private ClassroomManager classroomManager;
    ModuleManager moduleManager;

    @Before
    public void setup() {
        StorageController.enterTestMode();
        moduleManager = ModuleManager.getInstance();
        Module module = new ModuleBuilder().withModuleCode("CG1111").build();

        try {
            moduleManager.addModule(module);
        } catch (DuplicateModuleException e) {
            e.printStackTrace();
        }

        moduleManager.saveModuleList();
        classroomManager = ClassroomManager.getInstance();

    }

    @Test
    public void constructor_nullClassroom_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ClassEditCommand(null, null, null);
    }

    @Test
    public void execute_classroomAccepted_editSuccessful() {
        Classroom classroom = new ClassroomBuilder().build();
        classroomManager.addClassroom(classroom);

        ClassEditCommand.EditClassDescriptor editClassroomDescriptor = new ClassEditCommand.EditClassDescriptor();
        editClassroomDescriptor.setClassName(classroom.getClassName());
        editClassroomDescriptor.setModuleCode(classroom.getModuleCode());
        Enrollment editedEnrollmentValue = new Enrollment("99");
        editClassroomDescriptor.setEnrollment(editedEnrollmentValue);

        ClassEditCommand classEditCommand = new ClassEditCommand(classroom.getClassName().getValue(),
                classroom.getModuleCode().moduleCode,
                editClassroomDescriptor);

        String successMessage = String.format(MESSAGE_EDIT_CLASSROOM_SUCCESS,
                classroom.getClassName(), classroom.getModuleCode());

        assertCommandSuccess(classEditCommand, model, commandHistory, successMessage, model);
    }

    @Test
    public void execute_invalidClassroomModule_throwsCommandException() throws Exception {
        ClassEditCommand classEditCommand = new ClassEditCommand("T16",
                "Invalid Module Code",
                new ClassEditCommand.EditClassDescriptor());

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassEditCommand.MESSAGE_MODULE_CODE_INVALID);
        classEditCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_classroomNotFound_throwsCommandException() throws Exception {
        Module module = new ModuleBuilder().withModuleCode("CG1112").build();
        moduleManager.addModule(module);
        ClassEditCommand classEditCommand = new ClassEditCommand("T16",
                "CG1112",
                new ClassEditCommand.EditClassDescriptor());

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassEditCommand.MESSAGE_FAIL);
        classEditCommand.execute(model, commandHistory);
    }

    @AfterClass
    public static void tearDown() {
        ClassroomManager.getInstance().clearClassrooms();
        ClassroomManager.getInstance().saveClassroomList();
    }

}

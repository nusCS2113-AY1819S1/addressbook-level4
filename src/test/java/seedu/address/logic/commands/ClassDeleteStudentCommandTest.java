package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_T16;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATRIC_NO_MEGAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CG1111;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseCode;
import seedu.address.model.course.CourseManager;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.person.Person;
import seedu.address.model.student.StudentManager;
import seedu.address.testutil.ClassroomBuilder;
import seedu.address.testutil.CourseBuilder;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Provides a test for the class unassign student command
 */
public class ClassDeleteStudentCommandTest {
    private static ClassroomManager classroomManager;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Person student;
    private Person student2;
    private Person student3;
    private Classroom classroom;

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        CourseManager courseManager = CourseManager.getInstance();
        ModuleManager moduleManager = ModuleManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        classroomManager = ClassroomManager.getInstance();

        Course course = new CourseBuilder().build();
        course.setCourseCode(new CourseCode("CEG"));
        courseManager.addCourse(course);

        student = new PersonBuilder().withCourseCode("CEG").withMatricNo("A0168000B").build();
        student2 = new PersonBuilder().withName(VALID_NAME_BOB)
                .withCourseCode("CEG").withMatricNo("A0168001B").build();
        student3 = new PersonBuilder().withName(VALID_NAME_AMY)
                .withCourseCode("CEG").withMatricNo("A0168002B").build();
        model.addPerson(student);
        model.addPerson(student2);
        model.addPerson(student3);
        studentManager.initializeModel(model);

        Module module = new ModuleBuilder().withModuleCode("CG1111").build();
        try {
            moduleManager.addModule(module);
        } catch (DuplicateModuleException e) {
            e.printStackTrace();
        }
        moduleManager.enrolStudentInModule(module, student);
        moduleManager.enrolStudentInModule(module, student3);
        moduleManager.saveModuleList();
        classroom = new ClassroomBuilder().build();
        classroom.getStudents().add(student.getMatricNo().matricNo);
        classroomManager.addClassroom(classroom);
    }

    @Test
    public void constructor_nullClassroomInfo_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ClassDeleteStudentCommand(null, null, null);
    }

//    @Test
//    public void execute_classroomUnassignStudentAccepted_deleteSuccessful() {
//        String expectedMessage = String.format(MESSAGE_SUCCESS, student.getMatricNo().matricNo,
//                classroom.getClassName(), classroom.getModuleCode());
//        ClassDeleteStudentCommand classDeleteStudentCommand = new ClassDeleteStudentCommand(
//                classroom.getClassName().getValue(),
//                classroom.getModuleCode().moduleCode, student.getMatricNo().matricNo);
//        assertCommandSuccess(classDeleteStudentCommand, model,
//                commandHistory, expectedMessage,
//                model);
//    }

    @Test
    public void execute_classroomInvalidModule_throwsCommandException() throws CommandException {
        ClassDeleteStudentCommand classDeletetStudentCommand = new ClassDeleteStudentCommand(
                classroom.getClassName().getValue(),
                "Invalid Module Code", student.getMatricNo().matricNo);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassDeleteStudentCommand.MESSAGE_MODULE_CODE_INVALID);
        classDeletetStudentCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_classroomInvalidStudent_throwsCommandException() throws CommandException {
        ClassDeleteStudentCommand classDeleteStudentCommand = new ClassDeleteStudentCommand(
                classroom.getClassName().getValue(),
                classroom.getModuleCode().moduleCode, "Invalid Matric No");

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassDeleteStudentCommand.MESSAGE_INVALID_STUDENT);
        classDeleteStudentCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_classroomValidModuleUnenrolledStudent_throwsCommandException() throws CommandException {
        ClassDeleteStudentCommand classDeleteStudentCommand = new ClassDeleteStudentCommand(
                classroom.getClassName().getValue(),
                classroom.getModuleCode().moduleCode, student2.getMatricNo().matricNo);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassDeleteStudentCommand.MESSAGE_INVALID_STUDENT_MODULE);
        classDeleteStudentCommand.execute(model, commandHistory);
    }

//    @Test
//    public void execute_invalidClassroom_throwsCommandException() throws CommandException {
//        ClassDeleteStudentCommand classDeleteStudentCommand = new ClassDeleteStudentCommand(
//                "Invalid Classroom",
//                classroom.getModuleCode().moduleCode, student.getMatricNo().matricNo);
//
//        thrown.expect(CommandException.class);
//        thrown.expectMessage(ClassDeleteStudentCommand.MESSAGE_FAIL);
//        classDeleteStudentCommand.execute(model, commandHistory);
//    }

//    @Test
//    public void execute_studentNotAssignedClassroom_throwsCommandException() throws CommandException {
//        String expectedMessage = String.format(MESSAGE_CLASSROOM_STUDENT_NOT_FOUND, student3.getMatricNo().matricNo);
//        thrown.expect(CommandException.class);
//        thrown.expectMessage(expectedMessage);
//        ClassDeleteStudentCommand classDeleteStudentCommand = new ClassDeleteStudentCommand(
//                classroom.getClassName().getValue(),
//                classroom.getModuleCode().moduleCode, student3.getMatricNo().matricNo);
//        classDeleteStudentCommand.execute(model, commandHistory);
//    }

    @Test
    public void equals() {
        final ClassDeleteStudentCommand standardCommand = new ClassDeleteStudentCommand(VALID_CLASS_T16,
                VALID_MODULE_CODE_CG1111,
                VALID_MATRIC_NO_MEGAN);
        // same values -> retDeletes true
        ClassDeleteStudentCommand commandWithSameValues = new ClassDeleteStudentCommand(VALID_CLASS_T16,
                VALID_MODULE_CODE_CG1111,
                VALID_MATRIC_NO_MEGAN);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    @AfterClass
    public static void tearDown() {
        classroomManager.clearClassrooms();
        classroomManager.saveClassroomList();
    }
}

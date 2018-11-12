package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ClassAddStudentAttendanceCommand.MESSAGE_DUPLICATE_CLASSROOM_STUDENT_ATTENDANCE;
import static seedu.address.logic.commands.ClassAddStudentAttendanceCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_T16;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATRIC_NO_MEGAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CG1111;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

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
import seedu.address.model.person.MatricNo;
import seedu.address.model.person.Person;
import seedu.address.model.student.StudentManager;
import seedu.address.testutil.ClassroomBuilder;
import seedu.address.testutil.CourseBuilder;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Provides a test for the class add student attendance command
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClassAddStudentAttendanceCommandTest {

    private static ClassroomManager classroomManager;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Person student;
    private Classroom classroom;
    @Before
    public void setUp() {
        StorageController.enterTestMode();
        CourseManager courseManager = CourseManager.getInstance();
        ModuleManager moduleManager = ModuleManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();
        studentManager.initializeModel(model);
        classroomManager = ClassroomManager.getInstance();

        Course course = new CourseBuilder().build();
        course.setCourseCode(new CourseCode("CEG"));
        courseManager.addCourse(course);

        student = new PersonBuilder()
                .withCourseCode("CEG").withMatricNo("A0168000B").build();
        model.addPerson(student);
        model.commitAddressBook();

        Module module = new ModuleBuilder().withModuleCode("CG1111").build();

        try {
            moduleManager.addModule(module);
        } catch (DuplicateModuleException e) {
            e.getMessage();
        }

        Set<MatricNo> studentMatricNo = new HashSet<>();
        studentMatricNo.add(student.getMatricNo());
        ModuleEnrolCommand moduleEnrolCommand = new ModuleEnrolCommand(module.getModuleCode(),
                studentMatricNo);

        try {
            moduleEnrolCommand.execute(model, commandHistory);
        } catch (CommandException e) {
            e.getMessage();
        }

        classroom = new ClassroomBuilder().withClassName("T16").withModuleCode("CG1111").build();
        classroomManager.addClassroom(classroom);
    }

    @Test
    public void execute_classroomInvalidModule_throwsCommandException() throws CommandException {
        ClassAddStudentAttendanceCommand classAddStudentAttendanceCommand =
                new ClassAddStudentAttendanceCommand(classroom.getClassName().getValue(),
                "Invalid Module Code", student.getMatricNo().matricNo);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassAddStudentAttendanceCommand.MESSAGE_MODULE_CODE_INVALID);
        classAddStudentAttendanceCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_classroomInvalidStudent_throwsCommandException() throws CommandException {
        ClassAddStudentAttendanceCommand classAddStudentAttendanceCommand =
                new ClassAddStudentAttendanceCommand(classroom.getClassName().getValue(),
                classroom.getModuleCode().moduleCode, "Invalid Matric No");

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassAddStudentAttendanceCommand.MESSAGE_INVALID_STUDENT);
        classAddStudentAttendanceCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_invalidClassroom_throwsCommandException() throws CommandException {
        ClassAddStudentAttendanceCommand classAddStudentAttendanceCommand =
                new ClassAddStudentAttendanceCommand("Invalid Classroom",
                classroom.getModuleCode().moduleCode, student.getMatricNo().matricNo);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassAddStudentAttendanceCommand.MESSAGE_FAIL);
        classAddStudentAttendanceCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_classroomMarkStudentPresent_addSuccessful() {
        String expectedMessage = String.format(MESSAGE_SUCCESS, student.getMatricNo().matricNo,
                classroom.getClassName(), classroom.getModuleCode());
        ClassAddStudentAttendanceCommand classAddStudentAttendanceCommand =
                new ClassAddStudentAttendanceCommand(classroom.getClassName().getValue(),
                classroom.getModuleCode().moduleCode, student.getMatricNo().matricNo);
        assertCommandSuccess(classAddStudentAttendanceCommand, model,
                commandHistory, expectedMessage,
                model);
    }

    @Test
    public void execute_duplicateClassroomStudentAttendance_throwsCommandException() throws CommandException {
        String expectedMessage = String.format(MESSAGE_DUPLICATE_CLASSROOM_STUDENT_ATTENDANCE,
                student.getMatricNo().matricNo);
        thrown.expect(CommandException.class);
        thrown.expectMessage(expectedMessage);
        ClassAddStudentAttendanceCommand classAddStudentAttendanceCommand =
                new ClassAddStudentAttendanceCommand(classroom.getClassName().getValue(),
                classroom.getModuleCode().moduleCode, student.getMatricNo().matricNo);
        classAddStudentAttendanceCommand.execute(model, commandHistory);
    }

    @Test
    public void constructor_nullClassroomAttendanceInfo_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ClassAddStudentAttendanceCommand(null, null, null);
    }

    @Test
    public void equals() {
        final String className = "T16";
        final String moduleCode = "CG1111";
        final String matricNo = "A0168412C";
        final ClassAddStudentAttendanceCommand standardCommand = new ClassAddStudentAttendanceCommand(className,
                moduleCode,
                matricNo);
        // same values -> returns true
        ClassAddStudentAttendanceCommand commandWithSameValues = new ClassAddStudentAttendanceCommand(VALID_CLASS_T16,
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

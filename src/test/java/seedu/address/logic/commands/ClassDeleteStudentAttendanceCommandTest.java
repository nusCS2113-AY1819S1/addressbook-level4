package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ClassDeleteStudentAttendanceCommand.MESSAGE_NOT_CLASSROOM_STUDENT_ATTENDANCE;
import static seedu.address.logic.commands.ClassDeleteStudentAttendanceCommand.MESSAGE_NO_CLASSROOM_STUDENT_ATTENDANCE;
import static seedu.address.logic.commands.ClassDeleteStudentAttendanceCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.ClassDeleteStudentAttendanceCommand.MESSAGE_UNMARKED_CLASSROOM_STUDENT_ATTENDANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_T16;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATRIC_NO_MEGAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CG1111;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.address.model.classroom.Attendance;
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
 * Provides a test for the class delete student attendance command
 */
public class ClassDeleteStudentAttendanceCommandTest {

    private static ClassroomManager classroomManager;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Person student;
    private Person student2;
    private Person student3;
    private Classroom classroom;
    private Classroom classroom2;
    private Attendance attendance;

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
        student2 = new PersonBuilder().withName(VALID_NAME_BOB)
                .withCourseCode("CEG").withMatricNo("A0168001B").build();
        student3 = new PersonBuilder().withName(VALID_NAME_AMY)
                .withCourseCode("CEG").withMatricNo("A0168002B").build();
        model.addPerson(student);
        model.addPerson(student2);
        model.addPerson(student3);
        model.commitAddressBook();

        Module module = new ModuleBuilder().withModuleCode("CG1111").build();

        try {
            moduleManager.addModule(module);
        } catch (DuplicateModuleException e) {
            e.getMessage();
        }

        classroom = new ClassroomBuilder().withClassName("T16").withModuleCode("CG1111").build();
        classroom2 = new ClassroomBuilder().withClassName("T17").withModuleCode("CG1111").build();
        classroom.getStudents().add(student.getMatricNo().matricNo);
        classroom2.getStudents().add(student3.getMatricNo().matricNo);
        classroomManager.addClassroom(classroom);
        classroomManager.addClassroom(classroom2);
        attendance = new Attendance();
        classroomManager.markStudentAttendance(classroom, attendance, student.getMatricNo().matricNo);
    }

    @Test
    public void execute_classroomInvalidModule_throwsCommandException() throws CommandException {
        ClassDeleteStudentAttendanceCommand classDeleteStudentAttendanceCommand =
                new ClassDeleteStudentAttendanceCommand(classroom.getClassName().getValue(),
                "Invalid Module Code", student.getMatricNo().matricNo);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassDeleteStudentAttendanceCommand.MESSAGE_MODULE_CODE_INVALID);
        classDeleteStudentAttendanceCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_classroomInvalidStudent_throwsCommandException() throws CommandException {
        ClassDeleteStudentAttendanceCommand classDeleteStudentAttendanceCommand =
                new ClassDeleteStudentAttendanceCommand(classroom.getClassName().getValue(),
                classroom.getModuleCode().moduleCode, "Invalid Matric No");

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassDeleteStudentAttendanceCommand.MESSAGE_INVALID_STUDENT);
        classDeleteStudentAttendanceCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_invalidClassroom_throwsCommandException() throws CommandException {
        ClassDeleteStudentAttendanceCommand classDeleteStudentAttendanceCommand =
                new ClassDeleteStudentAttendanceCommand("Invalid Classroom",
                classroom.getModuleCode().moduleCode, student.getMatricNo().matricNo);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassDeleteStudentAttendanceCommand.MESSAGE_FAIL);
        classDeleteStudentAttendanceCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_studentNotInClassroom_throwsCommandException() throws CommandException {
        String expectedMessage = String.format(MESSAGE_NOT_CLASSROOM_STUDENT_ATTENDANCE,
                student2.getMatricNo().matricNo);
        ClassDeleteStudentAttendanceCommand classDeleteStudentAttendanceCommand =
                new ClassDeleteStudentAttendanceCommand(classroom.getClassName().getValue(),
                        classroom.getModuleCode().moduleCode, student2.getMatricNo().matricNo);
        thrown.expect(CommandException.class);
        thrown.expectMessage(expectedMessage);
        classDeleteStudentAttendanceCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_invalidClassroomAttendance_throwsCommandException() throws CommandException {
        ClassDeleteStudentAttendanceCommand classDeleteStudentAttendanceCommand =
                new ClassDeleteStudentAttendanceCommand(classroom2.getClassName().getValue(),
                        classroom2.getModuleCode().moduleCode, student3.getMatricNo().matricNo);
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_NO_CLASSROOM_STUDENT_ATTENDANCE);
        classDeleteStudentAttendanceCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_classroomMarkStudentAbsent_deleteSuccessful() {
        String expectedMessage = String.format(MESSAGE_SUCCESS, student.getMatricNo().matricNo,
                classroom.getClassName(), classroom.getModuleCode());
        ClassDeleteStudentAttendanceCommand classDeleteStudentAttendanceCommand =
                new ClassDeleteStudentAttendanceCommand(classroom.getClassName().getValue(),
                        classroom.getModuleCode().moduleCode, student.getMatricNo().matricNo);
        assertCommandSuccess(classDeleteStudentAttendanceCommand, model,
                commandHistory, expectedMessage,
                model);
    }

    @Test
    public void execute_classroomMarkStudentAlreadyAbsent_throwsCommandException() throws CommandException {
        String expectedMessage = String.format(MESSAGE_UNMARKED_CLASSROOM_STUDENT_ATTENDANCE,
                student.getMatricNo().matricNo);
        attendance.getStudentsPresent().remove(student.getMatricNo().matricNo);
        ClassDeleteStudentAttendanceCommand classDeleteStudentAttendanceCommand =
                new ClassDeleteStudentAttendanceCommand(classroom.getClassName().getValue(),
                        classroom.getModuleCode().moduleCode, student.getMatricNo().matricNo);
        thrown.expect(CommandException.class);
        thrown.expectMessage(expectedMessage);
        classDeleteStudentAttendanceCommand.execute(model, commandHistory);
    }

    @Test
    public void constructor_nullClassroomAttendanceInfo_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ClassDeleteStudentAttendanceCommand(null, null, null);
    }

    @Test
    public void equals() {
        final String className = "T16";
        final String moduleCode = "CG1111";
        final String matricNo = "A0168412C";
        final ClassDeleteStudentAttendanceCommand standardCommand = new ClassDeleteStudentAttendanceCommand(
                className,
                moduleCode,
                matricNo);
        // same values -> returns true
        ClassDeleteStudentAttendanceCommand commandWithSameValues =
                new ClassDeleteStudentAttendanceCommand(VALID_CLASS_T16,
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

package seedu.address.logic.commands;

import java.util.HashSet;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.StorageController;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseCode;
import seedu.address.model.course.CourseManager;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FacultyName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.MatricNo;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.student.StudentManager;
import seedu.address.ui.HtmlTableProcessor;


/**
 * This class is designed for debug use only.
 */
public class DebugCommand extends Command {
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        StringBuilder sb = new StringBuilder();

        StorageController.wipeAllProductionData();
        StorageController.createFiles();
        CourseManager.getInstance().clearAll();
        StudentManager.getInstance().initializeModel(model);
        model.resetData(new AddressBook());
        model.commitAddressBook();

        CourseManager.getInstance().addCourse(new Course(new CourseCode("CEG"),
                new CourseName("Computer Engineering"), new FacultyName("Faculty of Engineering")));
        CourseManager.getInstance().addCourse(new Course(new CourseCode("MECHENG"),
                new CourseName("Mechanical Engineering"), new FacultyName("Faculty of Engineering")));
        CourseManager.getInstance().addCourse(new Course(new CourseCode("EEE"),
                new CourseName("Electrical Engineering"), new FacultyName("Faculty of Engineering")));
        CourseManager.getInstance().addCourse(new Course(new CourseCode("CS"),
                new CourseName("Computer Science"), new FacultyName("School of Computing")));
        CourseManager.getInstance().addCourse(new Course(new CourseCode("IS"),
                new CourseName("Information Systems"), new FacultyName("School of Computing")));
        CourseManager.getInstance().addCourse(new Course(new CourseCode("INFOSEC"),
                new CourseName("Information Security"), new FacultyName("School of Computing")));
        CourseManager.getInstance().addCourse(new Course(new CourseCode("BA"),
                new CourseName("Business Analytics"), new FacultyName("School of Computing")));
        CourseManager.getInstance().addCourse(new Course(new CourseCode("POLSCI"),
                new CourseName("Political Science"), new FacultyName("Faculty of Arts and Social Sciences")));
        CourseManager.getInstance().saveCourseList();

        model.addPerson(new Person(new Name("Megan Nicole"), new Phone("91234567"),
                new Email("megannicole@u.nus.edu"), new Address("1 Park Avenue"),
                new HashSet<>(), new CourseCode("CEG"), new MatricNo("A0168372L")));
        model.addPerson(new Person(new Name("James Bond"), new Phone("98367527"),
                new Email("jamesbond@u.nus.edu"), new Address("Blk 211 Bedok Avenue 3"),
                new HashSet<>(), new CourseCode("CEG"), new MatricNo("A0166371K")));
        model.addPerson(new Person(new Name("Natalie Shimmie"), new Phone("81112222"),
                new Email("natalie_s@u.nus.edu"), new Address("Blk 92 Punggol"),
                new HashSet<>(), new CourseCode("EEE"), new MatricNo("A0196361C")));
        model.commitAddressBook();


        return new CommandResult("DEBUG COMMAND EXECUTED. ",
                HtmlTableProcessor.getH1Representation("Populated data set."));
    }
}

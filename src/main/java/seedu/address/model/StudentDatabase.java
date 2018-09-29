//@@author m-aslam-mj2
package seedu.address.model;

import java.util.ArrayList;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.student.Student2;


/**
 *  The class holds students' data for use by the system.
 */
public class StudentDatabase {
    // THIS IS A CUSTOM IMPLEMENTATION THAT OPERATES OUTSIDE THE BOUNDS OF HOW AB4 STORES ADDRESS BOOK DATA.
    private static ArrayList<Student2> studentDB;

    public void setupStudentData() {

        studentDB = new ArrayList<Student2>();

        // PLACEHOLDER
        studentDB.add(new Student2(new Name("Megan Nicole") , new Phone("91234567") ,
                new Email("megannicole@outlook.com"),
                new Address("1 Park Avenue") , null , "CEG1" , "A1"));

        // READ FROM LOCAL STORAGE AND DUMP INTO ARRAY LIST
    }

    public void saveStudentData(){
        // DUMP ALL DATA FROM ARRAYLIST OF STUDENT OBJS AND STORE IN LOCAL STORAGE
    }


}

//@@author m-aslam-mj2
package seedu.address.model.student;

import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;




/**
 * Represents a student in the LMS.
 *
 */

public class Student2 extends Person {
    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param tags
     */

    private final String courseCode;
    private final String matricNo;



    public Student2(Name name, Phone phone, Email email, Address address,
                   Set<Tag> tags, String courseCode, String matricNo) {
        super(name, phone, email, address, tags, courseCode, matricNo);
        this.courseCode = courseCode;
        this.matricNo = matricNo;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getMatricNo() {
        return matricNo;
    }


}

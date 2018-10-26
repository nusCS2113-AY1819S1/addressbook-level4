package seedu.address.model.util;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.grade.Test;
import seedu.address.model.person.Person;

/**
 * this class is to form a list of students who has take certain test(each person may have several tests )
 */
public class FormStudentListForGrade {
    /**
     * this method is to form a list of students who has take certain test
     */
    public static ArrayList<Person> formStudentListForCalculation(List<Person> personList, String testName) {
        ArrayList<Person> list = new ArrayList<>(); // this is the list contains persons that take certain test(user defined)
        for (int i = 0; i < personList.size(); i++) {
            for (Test test : personList.get(i).getTests()) {
                if (test.getTestName().testName.equals(testName)) {
                    list.add(personList.get(i));
                }
            }
        }
        return list;
    }


}

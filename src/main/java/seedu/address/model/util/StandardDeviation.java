package seedu.address.model.util;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.grade.Marks;
import seedu.address.model.grade.PersonTest;
import seedu.address.model.grade.Test;
import seedu.address.model.grade.TestName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
/**
 * calculate the Standard Deviation values of student's scores.
 */
public class StandardDeviation {
    /**
     * calculate the Standard Deviation values of student's scores.
     */
    public static double calculateStandardDeviation(ObservableList<Person> personList, String testName) {
        PersonTest student;
        String name1;
        String testName1;
        String marks1;
        double sum = 0.0;
        double standardDeviation = 0.0;
        ArrayList<PersonTest> list = new ArrayList<>();
        // this is the list contains persons that take certain test(user defined)
        for (int i = 0; i < personList.size(); i++) {
            for (Test test: personList.get(i).getTests()) {
                if (test.getTestName().testName.equals(testName)) {
                    name1 = personList.get(i).getName().fullName;
                    testName1 = test.getTestName().testName;
                    marks1 = test.getMarks().value;
                    student = new PersonTest(new Name(name1), new TestName(testName1), new Marks(marks1));
                    list.add(student);
                }
            }
        }
        int size = list.size();
        double mean = Mean.calculateMean(personList, testName);

        for (int k = 0; k < size; k++) {
            standardDeviation += Math.pow(Double.parseDouble(list.get(k).getMarks().value) - mean, 2);
        }
        double sd = Math.sqrt(standardDeviation / size);
        return sd;
    }
}

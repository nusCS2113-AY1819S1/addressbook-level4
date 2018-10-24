package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * somthing
 */
public class FormStudentListForGrade {
    /**
     * something
     */
    public static ArrayList<Person> formStudentlistForCalculation(List<Person> personList, String testName) {
        ArrayList<Person> list = new ArrayList<>(); // this is the list contains persons that take certain test(user defined)
        for (int i = 0; i < personList.size(); i++) {
            Iterator it = personList.get(i).getTests().iterator();
            if (it.next().equals(testName)) {
                list.add(personList.get(i));
            }
        }
        return list;
    }
}



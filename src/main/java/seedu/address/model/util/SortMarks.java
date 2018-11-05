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
 * sort student in ascending order by scores.
 */
public class SortMarks { //from lowest to highest
    /**
     * sort student in ascending order by scores.
     */
    public static ArrayList<PersonTest> sortingFromLowestToHighest(ObservableList<Person> personList, String testName) {
        PersonTest student;
        String name1;
        String testName1;
        String marks1;
        ArrayList<PersonTest> listToSort = new ArrayList<PersonTest>();
        for (int i = 0; i < personList.size(); i++) {
            for (Test test: personList.get(i).getTests()) {
                if (test.getTestName().testName.equals(testName)) {
                    name1 = personList.get(i).getName().fullName;
                    testName1 = test.getTestName().testName;
                    marks1 = test.getMarks().value;
                    student = new PersonTest(new Name(name1), new TestName(testName1), new Marks(marks1));
                    listToSort.add(student);
                }
            }

        }

        ArrayList<PersonTest> pList = new ArrayList<>(bubbleSort(listToSort, testName, listToSort.size()));
        for (int q = 0; q < listToSort.size(); q++) {
            System.out.println(pList.get(q));
        }
        return pList;
    }

    /**
     * implement bubblesort function to sort students by test marks.
     * */
    public static ArrayList<PersonTest> bubbleSort(ArrayList<PersonTest> list, String testName, int n) {
        int i;
        int j;
        PersonTest temp;
        for (i = 0; i < n - 1; i++) {

            for (j = 0; j < n - i - 1; j++) {

                if (Double.parseDouble(list.get(j).getMarks().toString())
                        > Double.parseDouble(list.get(j + 1).getMarks().toString())) {
                    temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }

            }
        }
        return list;
    }
}

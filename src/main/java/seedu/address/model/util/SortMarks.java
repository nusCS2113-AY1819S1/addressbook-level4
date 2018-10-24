package seedu.address.model.util;

import java.util.ArrayList;

import javafx.collections.ObservableList;

import seedu.address.model.grade.Test;
import seedu.address.model.person.Person;

/**
 * sort student in ascending order by scores.
 */
public class SortMarks { //from lowest to highest

    /**
     * sort student in ascending order by their scores.
     */
    public static ArrayList<Person> sortingFromLowestToHighest(ObservableList<Person> personList, String testName) {
        ArrayList<Person> listToSort = new ArrayList<Person>();
        for (int i = 0; i < personList.size(); i++) {
            for (Test test: personList.get(i).getTests()) {
                if (test.getTestName().testName.equals(testName)) {
                    personList.get(i).setGrade(test.getGrade());
                    personList.get(i).setMarks(test.getMarks());
                    listToSort.add(personList.get(i));
                }
            }

        }
        ArrayList<Person> pList = new ArrayList<>(bubbleSort(listToSort, testName, listToSort.size()));
        //System.out.println(pList.toString());
        return pList;
    }

    /**
     * implement bubblesort function to sort students by test marks.
     * */
    public static ArrayList<Person> bubbleSort(ArrayList<Person> list, String testName, int n) {
        int i;
        int j;
        Person temp;

        for (i = 0; i < n - 1; i++) {
            //swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (Integer.parseInt(list.get(j).getTests().iterator().next().getMarks().toString())
                        > Integer.parseInt(list.get(j + 1).getTests().iterator().next().getMarks().toString())) {
                    temp = list.get(j);
                    list.set(j, list.get(j + 1));

                    list.set(j + 1, temp);
                }

            }
        }
        return list;
    }
}

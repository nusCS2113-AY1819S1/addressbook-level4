package seedu.address.model.util;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.grade.PersonTest;
import seedu.address.model.person.Person;
/**
 * find the last 25 percent student's list.
 */
public class LastTwentyFivePercen {
    /**
     * find the last 25 percent student's list.
     */
    public ArrayList<PersonTest> findLastTwentyFive(ObservableList<Person> personList, String testName) {
        ArrayList<PersonTest> sortedList =
                new ArrayList<>(SortMarks.sortingFromLowestToHighest(personList, testName));

        ArrayList<PersonTest> theList = new ArrayList<>();
        int getListSize = sortedList.size();
        double getLast = getListSize * 0.25;
        int personLast = (int) Math.floor(getLast);

        for (int i = 0; i < personLast; i++) {
            theList.add(sortedList.get(i));
        }

        return theList;
    }
}

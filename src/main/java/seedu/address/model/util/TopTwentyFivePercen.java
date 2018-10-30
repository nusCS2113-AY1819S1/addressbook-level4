package seedu.address.model.util;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.grade.PersonTest;
import seedu.address.model.person.Person;
/**
 * find the top 25 percent student's list
 */
public class TopTwentyFivePercen {
    /**
     * find the top 25 percent student's list
     */
    public ArrayList<PersonTest> findTopTwentyFive(ObservableList<Person> personList, String testName) {
        ArrayList<PersonTest> sortedList =
                new ArrayList<>(SortMarks.sortingFromLowestToHighest(personList, testName));

        ArrayList<PersonTest> theTopList = new ArrayList<>();
        int getTopSize = sortedList.size();
        double getTop = getTopSize * 0.75;
        int personTop = (int) Math.ceil(getTop);

        for (int i = personTop; i < getTopSize; i++) {
            theTopList.add(sortedList.get(i));
        }

        return theTopList;
    }
}

package seedu.address.model.util;

import static java.lang.Math.max;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
/**
 * calculate the values we used for gradesummary and scorefilter command.
 */
public class Calculator {

    /**
     * calculate the mean values of student's scores.
     */
    public int calculateMean(ObservableList<Person> personList) { //calculate the mean score of a test
        int sum = 0;
        for (int i = 0; i < personList.size(); i++) {

            sum += Integer.parseInt(personList.get(i).getGrade().value); //string to integer then calculate
        }
        int mean = sum / personList.size();
        return mean;
    }

    /**
     * find the highest score from student's scores.
     */
    public int findHighest(ObservableList<Person>  personList) {
        int maximum = 0;
        for (int i = 0; i < personList.size(); i++) {
            int currentVal = Integer.parseInt(personList.get(i).getGrade().value);
            maximum = max(currentVal, maximum);
        }


        return maximum;
    }
    /**
     * find the top 25 percent student's name and scores.
     */
    public ArrayList<Person> find25th(ObservableList<Person>  personList) {
        ArrayList<Person> sortedList =
                new ArrayList<Person>(SortGrade.bubbleSort(personList,
                        personList.size()));

        ArrayList<Person> theList = new ArrayList<Person>();
        int getListSize = sortedList.size();
        double get25th = getListSize * 0.75;
        int person25th = (int) Math.floor(get25th);

        System.out.println("25TH\n");
        System.out.println(sortedList.get(person25th));
        for (int i = person25th; i < sortedList.size(); i++) {
            theList.add(sortedList.get(i));
            System.out.println(sortedList.get(i).getName() + " " + sortedList.get(i).getGrade());

        }
        System.out.println("TheList");
        System.out.println(theList);
        return theList;

    }
    /**
     * find the last 25 percent student's name and scores.
     */
    public ArrayList<Person> find75th(ObservableList<Person>  personList) {
        ArrayList<Person> sortedList =
                new ArrayList<Person>(SortGrade.bubbleSort(personList, personList.size()));

        ArrayList<Person> theList = new ArrayList<Person>();
        int getListSize = sortedList.size();
        double get75th = getListSize * 0.25;
        int person75th = (int) Math.floor(get75th);

        System.out.println("75TH\n");
        System.out.println(sortedList.get(person75th));
        for (int i = 0; i < person75th; i++) {
            theList.add(sortedList.get(i));
            System.out.println(sortedList.get(i).getName() + " " + sortedList.get(i).getGrade());

        }
        System.out.println("TheList");
        System.out.println(theList);
        return theList;

    }
    /**
     * find the median of student's scores.
     */
    public int calculateMedian(ObservableList<Person>  personList) { //calculate the mean score of a test
        int median = 0;
        ArrayList<Person> sortedList = new ArrayList<Person>(SortGrade
                .bubbleSort(personList, personList.size()));

        int size = sortedList.size();
        int index = (size / 2) - 1;
        if (size % 2 == 0) {
            median = (Integer.parseInt(sortedList.get(index).getGrade().value)
                    + Integer.parseInt(sortedList.get(index + 1).getGrade().value)) / 2;
        } else {
            median = Integer.parseInt(sortedList.get(index + 1).getGrade().value);
        }
        System.out.println(median);
        return median;
    }


}

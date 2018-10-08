package seedu.address.model.util;

import static java.lang.Math.max;

import java.util.ArrayList;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

public class Calculator {



    public int CalculateMean(UniquePersonList personList) { //calculate the mean score of a test
        int sum =0;
        for(int i=0; i<personList.asUnmodifiableObservableList().size(); i++ ){

            sum += Integer.parseInt(personList.asUnmodifiableObservableList().get(i).getGrade().value); //string to integer then calculate
        }
        int mean=sum/personList.asUnmodifiableObservableList().size();
        return mean;
    }

    public int FindHighest(UniquePersonList personList) {  //find highest score of the test
        int maximum =0;
        for(int i=0; i<personList.asUnmodifiableObservableList().size(); i++ ){
            int currentVal = Integer.parseInt(personList.asUnmodifiableObservableList().get(i).getGrade().value);
           maximum = max(currentVal, maximum);
        }

        return maximum;
    }

    public ArrayList<Person> Find_25th(UniquePersonList personList){    // Find students in top 25th
        ArrayList<Person> sortedList =
                new ArrayList<Person>(SortGrade.bubbleSort(personList,personList.asUnmodifiableObservableList().size()));

        ArrayList<Person> theList = new ArrayList<Person>();
        int getListSize = sortedList.size();
        double get25th = getListSize*0.75;
        int person25th = (int)Math.floor(get25th);

        System.out.println("25TH\n");
        System.out.println(sortedList.get(person25th));
        for(int i=person25th;i<sortedList.size();i++){
            theList.add(sortedList.get(i));
            System.out.println(sortedList.get(i).getName()+" " + sortedList.get(i).getGrade());

        }
        System.out.println("TheList");
        System.out.println(theList);
        return theList;

    }

    public ArrayList<Person> Find_75th(UniquePersonList personList){    // Find students below 75th
        ArrayList<Person> sortedList =
                new ArrayList<Person>(SortGrade.bubbleSort(personList,personList.asUnmodifiableObservableList().size()));

        ArrayList<Person> theList = new ArrayList<Person>();
        int getListSize = sortedList.size();
        double get75th = getListSize*0.25;
        int person75th = (int)Math.floor(get75th);

        System.out.println("75TH\n");
        System.out.println(sortedList.get(person75th));
        for(int i=0;i<person75th;i++){
            theList.add(sortedList.get(i));
            System.out.println(sortedList.get(i).getName()+" " + sortedList.get(i).getGrade());

        }
        System.out.println("TheList");
        System.out.println(theList);
        return theList;

    }

    public int CalculateMedian(UniquePersonList personList) { //calculate the mean score of a test
        int sum =0;
        for(int i=0; i<personList.asUnmodifiableObservableList().size(); i++ ){

            sum += Integer.parseInt(personList.asUnmodifiableObservableList().get(i).getGrade().value); //string to integer then calculate
        }
        int mean=sum/personList.asUnmodifiableObservableList().size();
        return mean;
    }


}

package seedu.address.model.util;



import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

public class SortGrade { //from lowest to highest
    public SortGrade(){}
    public ArrayList<Person> sortingFromLowesttoHighest(UniquePersonList personList) {
            ArrayList<Person> pList = new ArrayList<>(bubbleSort(personList,personList.asUnmodifiableObservableList().size()));
            System.out.println(pList.toString());
            return pList;
    }

    static ArrayList<Person> bubbleSort(UniquePersonList list, int n)
    {
        ArrayList<Person> personList = new ArrayList<Person>();

        for(int i=0; list.asUnmodifiableObservableList().size()>i;i++){
            personList.add(list.asUnmodifiableObservableList().get(i));
        }


        int i, j;
        Person temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++)
        {
            swapped = false;
            for (j = 0; j < n - i - 1; j++)
            {
                if (Integer.parseInt(personList.get(j).getGrade().value) >
                        Integer.parseInt(personList.get(j+1).getGrade().value))

                {
                    // swap arr[j] and arr[j+1]
                    temp = personList.get(j);
                    personList.set(j,personList.get(j+1));

                    personList.set(j+1,temp);
                    swapped = true;
                }
            }


            if (swapped == false)
                break;
        }
        return personList;
    }

    private Person compareGrade(Person a, Person b){
        return Integer.parseInt(a.getGrade().value) < Integer.parseInt(b.getGrade().value) ? b : a;
    }


}




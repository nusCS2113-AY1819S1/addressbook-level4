package seedu.address.model.person;

import javafx.collections.ObservableList;
import seedu.address.commons.util.LevenshteinDistanceUtil;
import seedu.address.model.Model;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Comparator;

public class ClosestMatchList {
    private int lowestDist = Integer.MAX_VALUE;
    private ObservableList<Person> listToFilter;
    private List<String> approvedNames = new ArrayList<String>();


    static class Pair {
        public int dist;
        public String nameSegment;

        void Pair(int a, String b) {
            this.dist = a;
            this.nameSegment = b;
        }
    }

    Set <Pair> nameMap = new TreeSet<Pair>(new Comparator<Pair>() {
        @Override
        public int compare(Pair o1, Pair o2) {
            if(o1.dist - o2.dist == 0)
            {
                if(o1.dist == o2.dist)
                    return 1;
                else
                    return o1.nameSegment.compareTo(o2.nameSegment);
            }
            return o1.dist - o2.dist;
        }
    });


    public ClosestMatchList (Model model, String argument, String[] names) {
        this.listToFilter = model.getAddressBook().getPersonList();

        for (Person person: listToFilter) {

            if (argument == "NAME") {
                generateNameMapFromNames(names, person);
            }
            else if (argument == "PHONE") {
                // TODO: Add argument to filter by phone number as well
            }
        }


        addToApprovedNamesList();
    }

    private void addToApprovedNamesList() {
        for (Pair pair: nameMap) {
            if (pair.dist - lowestDist <= 1) {
                approvedNames.add(pair.nameSegment);
            }
        }
    }

    private void generateNameMapFromNames(String[] names, Person person) {
        String fullName = person.getName().fullName;
        String[] nameSplited = fullName.split("\\s+");

        for (String nameSegment: nameSplited) {

            for (String nameArg: names) {
                int dist = LevenshteinDistanceUtil.levenshteinDistance(nameArg.toLowerCase(),
                        nameSegment.toLowerCase());
                if (dist < lowestDist) {
                    lowestDist = dist;
                }
                System.out.println(nameSegment + " " + dist);
                Pair distNamePair = new Pair();
                distNamePair.dist = dist;
                distNamePair.nameSegment = nameSegment;

                nameMap.add(distNamePair);
            }

        }
    }



    public String[] getApprovedList () {
        String [] output = approvedNames.toArray(new String[approvedNames.size()]);
        return output;
    }

}

package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.collections.ObservableList;

import seedu.address.commons.util.LevenshteinDistanceUtil;
import seedu.address.model.Model;

/**
 * To generate a list of closest matches
 */
public class ClosestMatchList {
    private int lowestDist = Integer.MAX_VALUE;
    private ObservableList<Person> listToFilter;
    private List<String> approvedNames = new ArrayList<String>();
    private Map<String, Integer> discoveredNames = new TreeMap<String, Integer>();

    /**
     * Pair of integer and string
     */
    static class Pair {
        private int dist;
        private String nameSegment;

        public Pair(int a, String b) {
            this.dist = a;
            this.nameSegment = b;
        }

        public int getDist () {
            return this.dist;
        }

        public String getNameSegment () {
            return nameSegment;
        }
    }

    private Set <Pair> nameMap = new TreeSet<Pair>(new Comparator<Pair>() {
        @Override
        public int compare(Pair o1, Pair o2) {
            if (o1.getDist() - o2.getDist() == 0) {
                if (o1.getDist() == o2.getDist()) {
                    return 1;
                } else {
                    return o1.getNameSegment().compareTo(o2.getNameSegment());
                }
            }
            return o1.getDist() - o2.getDist();
        }
    });

    /**
     * Filters and generates maps from names from model
     * and arguments
     */
    public ClosestMatchList (Model model, String argument, String[] names) {
        this.listToFilter = model.getAddressBook().getPersonList();

        for (Person person: listToFilter) {

            if (argument == "NAME") {
                generateNameMapFromNames(names, person);
            } else if (argument == "PHONE") {
                // TODO: Add argument to filter by phone number as well
            }
        }


        addToApprovedNamesList();
    }

    /**
     * Add the contenst in the tree to a name list
     */
    private void addToApprovedNamesList() {
        for (Pair pair: nameMap) {
            if (pair.getDist() - lowestDist > 1) {
                // Break the loop when distances get too far
                return;
            }
            approvedNames.add(pair.getNameSegment());
        }
    }

    /**
     * Bulk of the computation
     * Runs thru model and generates a tree out of
     * similarity indexes using levensthein distances
     */
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

                Pair distNamePair = new Pair(dist, nameSegment);


                if (!discoveredNames.containsKey(nameSegment)) {
                    nameMap.add(distNamePair);
                    discoveredNames.put(nameSegment, dist);
                } else if (discoveredNames.get(nameSegment) > dist) {
                    discoveredNames.replace(nameSegment, dist); // Replace with the new dist
                    nameMap.add(distNamePair); // Check to see if this will replace
                }
            }

        }
    }


    /**
     * Gets the approved list
     */
    public String[] getApprovedList () {
        String [] output = approvedNames.toArray(new String[approvedNames.size()]);
        return output;
    }

}

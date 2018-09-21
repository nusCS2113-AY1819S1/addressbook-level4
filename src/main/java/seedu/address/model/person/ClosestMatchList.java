package seedu.address.model.person;

import javafx.collections.ObservableList;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import seedu.address.commons.util.LevenshteinDistanceUtil;
import seedu.address.model.Model;

public class ClosestMatchList {
    private int lowestDist = Integer.MAX_VALUE;
    private ObservableList<Person> listToFilter;
    private List<String> approvedNames = new ArrayList<String>();
    private Map<String, Integer> discoveredNames = new TreeMap<String, Integer>();


    static class pair {
        private int dist;
        private String nameSegment;

        void pair(int a, String b) {
            this.dist = a;
            this.nameSegment = b;
        }

        public int getDist () {
            return this.dist;
        }

        public String getNameSegment () {
            return nameSegment;
        }

        public void setNameSegment (String nameSegment) {
            this.nameSegment = nameSegment;
        }

        public void setDist (int dist) {
            this.dist = dist;
        }
    }

    Set <pair> nameMap = new TreeSet<pair>(new Comparator<pair>() {
        @Override
        public int compare(pair o1, pair o2) {
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
        for (ClosestMatchList.pair pair: nameMap) {
            if (pair.getDist() - lowestDist > 1) {
                // Break the loop when distances get too far
                return;
            }
            approvedNames.add(pair.getNameSegment());
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

                pair distNamePair = new pair();
                distNamePair.setDist(dist);
                distNamePair.setNameSegment(nameSegment);


                if (!discoveredNames.containsKey(nameSegment)) {
                    nameMap.add(distNamePair);
                    discoveredNames.put(nameSegment, dist);
                } else if (discoveredNames.get(nameSegment) > dist){
                    discoveredNames.replace(nameSegment, dist); // Replace with the new dist
                    nameMap.add(distNamePair); // Check to see if this will replace
                }
            }

        }
    }



    public String[] getApprovedList () {
        String [] output = approvedNames.toArray(new String[approvedNames.size()]);
        return output;
    }

}

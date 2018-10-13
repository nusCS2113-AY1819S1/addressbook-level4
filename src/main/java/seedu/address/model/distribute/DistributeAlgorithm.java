package seedu.address.model.distribute;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static seedu.address.model.person.Gender.VALID_GENDER_FEMALE;
import static seedu.address.model.person.Gender.VALID_GENDER_MALE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nationality;
import seedu.address.model.person.Person;

/**
 * DistributeAlgorithm class contains all the algorithm that is based on distinto command flags
 * This will contain 4 different types of Algorithm.
 * First - normalDistribution
 * Second - genderDistribution
 * Third - nationalityDistribution
 * Forth - Gender & Nationality Distribution
 */
public class DistributeAlgorithm {

    private static final String MESSAGE_INVALID_SIZE = "Number of Groups should not be more than Number of Persons";

    public DistributeAlgorithm(Model model, Distribute dist) throws CommandException {
        requireNonNull(dist);

        int index = dist.getIndex();
        String groupName = dist.getGroupName().toString();
        boolean genderFlag = dist.getGender();
        boolean nationalityFlag = dist.getNationality();

        ArrayList<ArrayList<Person>> groupArrayList = new ArrayList<ArrayList<Person>>();

        // Get all person data via ObservableList
        ObservableList<Person> allPerson = model.getFilteredPersonList();
        requireNonNull(allPerson);
        if (allPerson.size() < index) {
            throw new CommandException(MESSAGE_INVALID_SIZE);
        }

        //Converts into ArrayList to use Randomizer via Collections
        LinkedList<Person> allPersonArrayList = new LinkedList<>(allPerson);
        allPersonArrayList = randomizer(allPersonArrayList);

        if (!genderFlag && !nationalityFlag) {
            normalDistribution(index, groupArrayList, allPersonArrayList, groupName);
        } else if (!genderFlag && nationalityFlag) {
            nationalityDistribution(index, groupArrayList, allPersonArrayList, groupName);
        } else if (genderFlag && !nationalityFlag) {
            genderDistribution(index, groupArrayList, allPersonArrayList, groupName);
        } else {
            strictDistribution(index, groupArrayList, allPersonArrayList, groupName);
        }
    }

    /**
     */
    private void normalDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                    LinkedList<Person> allPersonArrayList, String groupName) {
        for (int i = index; i > 0; i--) { //number of groups to add into the groupArrayList
            ArrayList<Person> addPerson = new ArrayList<>();
            int paxInAGroup = allPersonArrayList.size() / i;
            while (paxInAGroup > 0) {
                addPerson.add(allPersonArrayList.getLast());
                allPersonArrayList.removeLast();
                paxInAGroup--;
            }

            groupArrayList.add(addPerson);
        }

        // TODO: Add function that iterate groupArrayList and addMemebrs into the group
        for (int i = 0; i < groupArrayList.size(); i++) {
            System.out.println(groupNameConcatenation(i, groupName));
            for (int j = 0; j < groupArrayList.get(i).size(); j++) {
                System.out.println(groupArrayList.get(i).get(j));
            }
        }
        groupArrayList.clear();
    }

    /**
     */
    private void nationalityDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                         LinkedList<Person> allPerson, String groupName) {
    //   LinkedList<Person> nationalityLinkList = CreateNationalityList(allPerson);
    //        int numOfDifferentNationality = numberOfDifferentNationality(allPerson);

    }

    /**
     */
    private void genderDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                    LinkedList<Person> allPerson, String groupName) {
        LinkedList<Person> maleLinkList = new LinkedList<>();
        LinkedList<Person> femaleLinkList = new LinkedList<>();
        int loopCounter = 0;
        int num = 0;

        maleLinkList = filterGender(allPerson, maleLinkList, VALID_GENDER_MALE);
        femaleLinkList = filterGender(allPerson, femaleLinkList, VALID_GENDER_FEMALE);

        while (maleLinkList.size() != 0 || femaleLinkList.size() != 0) {
            if (loopCounter % index == 0) {
                num = 0;
            }
            while (num < index) {
                if (maleLinkList.size() == 0 && femaleLinkList.size() == 0) {
                    break;
                }
                ArrayList<Person> temp = new ArrayList<>();
                if (maleLinkList.size() != 0) {
                    genderDistributionCheck(index, groupArrayList, maleLinkList, loopCounter, num, temp);
                    maleLinkList.removeLast();

                } else if (femaleLinkList.size() != 0) {
                    genderDistributionCheck(index, groupArrayList, femaleLinkList, loopCounter, num, temp);
                    femaleLinkList.removeLast();
                }
                num++;
                loopCounter++;
            }
        }

        // TODO: Add function that iterate groupArrayList and addMemebrs into the group
        for (int i = 0; i < groupArrayList.size(); i++) {
            System.out.println(groupNameConcatenation(i, groupName));
            for (int j = 0; j < groupArrayList.get(i).size(); j++) {
                System.out.println (groupArrayList.get(i).get(j));
            }
        }
        groupArrayList.clear();
    }

    /**
     */
    private void strictDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                    LinkedList<Person> allPersons, String groupName) {
        System.out.println("Gender & Nationality Distribution");
    }

    /* --- MODULAR METHODS --- **/

    //    private LinkedList<Person> CreateNationalityList(LinkedList<Person> allPerson) {
    //        //number of different nationality
    //        Map<Nationality, Long> counts = numberOfDifferentNationality(allPerson);
    //        Map<Nationality, Long> sortedCount = paxPerNationality(counts);
    //        LinkedList<Person> NationalityLinkList = new LinkedList<>();
    //
    //        System.out.println(counts.size());
    //        System.out.println(sortedCount);
    //
    //        for (int i = 0; i < counts.size(); i++) {
    //            String[] parseValue = sortedCount.entrySet().toArray()[i].toString().split("=");
    ////            int value = Integer.parseInt(parseValue[1]);
    //            System.out.print(sortedCount.keySet().toArray()[i] + " ");
    ////            System.out.println(value);
    //        }
    //        return NationalityLinkList;
    //    }

    /**
     * This function shuffles all the person inside the LinkedList.
     * @param person
     * @return
     */
    private LinkedList<Person> randomizer(LinkedList<Person> person) {
        requireNonNull(person);
        Collections.shuffle(person);
        return person;
    }


    /**
     * This function takes in the list of all person and calculate the total number of different nationalities.
     * Returns a integer value which represent the number of different nationalities
     */
    private Map<Nationality, Long> numberOfDifferentNationality(LinkedList<Person> allPerson) {
        Map<Nationality, Long> numberOfNationality =
                allPerson.stream().collect(Collectors.groupingBy(e -> e.getNationality(), Collectors.counting()));
        return numberOfNationality;
    }

    /**
     * This function will sort the map that holds the number of people with different nationalities.
     */
    private Map<Nationality, Long> paxPerNationality(Map<Nationality, Long> numberOfNationality) {
        Map<Nationality, Long> sortedNumOfNationality =
                numberOfNationality.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));
        return sortedNumOfNationality;
    }

    /**
     * This function conducts the checks to add Persons into the group.
     */
    private void genderDistributionCheck(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                         LinkedList<Person> genderLinkList,
                                         int loopCounter, int num, ArrayList<Person> temp) {
        if (loopCounter >= index) {
            temp = groupArrayList.get(num);
            temp.add(genderLinkList.getLast());
            groupArrayList.remove(num);
            groupArrayList.add(num, temp);
        } else {
            temp.add(genderLinkList.getLast());
            groupArrayList.add(num, temp);
        }
    }

    /**
     * This function runs through the allPerson list and add the specific gender required into an LinkedList
     * @param allPerson the list of allPerson in the addressbook
     */
    private LinkedList<Person> filterGender(LinkedList<Person> allPerson,
                                            LinkedList<Person> filteredGender, String gender) {
        for (Person p : allPerson) {
            if (p.getGender().toString().equals(gender)) {
                filteredGender.add(p);
            }
        }
        return filteredGender;
    }

    /**
     * This function concatenates the group index count behind the given group name.
     * Index shown to user will start from 1.
     */
    private String groupNameConcatenation (int index, String groupName) {
        int newIndex = index + 1;
        return groupName + String.valueOf(newIndex);
    }

}

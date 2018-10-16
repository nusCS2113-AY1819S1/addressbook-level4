package seedu.address.model.distribute;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static seedu.address.model.person.Gender.VALID_GENDER_FEMALE;
import static seedu.address.model.person.Gender.VALID_GENDER_MALE;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
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
    private static final String MESSAGE_DUPLICATE_GROUP = "There exist another group with the same name.";
    private static final String MESSAGE_SHUFFLE_ERROR = "There is a problem shuffling the people in the address book.";
    private static final String GROUP_LOCATION = "[UNKNOWN]";
    private Model model;

    public DistributeAlgorithm(){
    }

    public DistributeAlgorithm(Model model, Distribute dist) throws CommandException {
        requireNonNull(dist);
        this.model = model;
        int numOfGroups = dist.getIndex();
        String groupName = dist.getGroupName().toString();
        boolean genderFlag = dist.getGender();
        boolean nationalityFlag = dist.getNationality();

        ArrayList<ArrayList<Person>> groupArrayList = new ArrayList<ArrayList<Person>>();

        // Get all person data via ObservableList
        ObservableList<Person> allPerson = model.getFilteredPersonList();
        requireNonNull(allPerson);
        if (allPerson.size() < numOfGroups) {
            throw new CommandException(MESSAGE_INVALID_SIZE);
        }
        doesGroupNameExist(numOfGroups, groupName);


        //Converts into ArrayList to use Randomizer via Collections
        LinkedList<Person> allPersonArrayList = new LinkedList<>(allPerson);
        Instant instant = Instant.now();
        allPersonArrayList = shuffle(allPersonArrayList, new Random(instant.getEpochSecond()));

        if (!genderFlag && !nationalityFlag) {
            normalDistribution(numOfGroups, groupArrayList, allPersonArrayList, groupName);
        } else if (!genderFlag && nationalityFlag) {
            nationalityDistribution(numOfGroups, groupArrayList, allPersonArrayList, groupName);
        } else if (genderFlag && !nationalityFlag) {
            genderDistribution(numOfGroups, groupArrayList, allPersonArrayList, groupName);
        } else {
            strictDistribution(numOfGroups, groupArrayList, allPersonArrayList, groupName);
        }
    }

    /**
     * This Method distribute all students into n number of groups
     * Distribution is random.
     */
    private void normalDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                    LinkedList<Person> allPersonArrayList, String groupName) throws CommandException {
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
     * This Method distribute all students into n number of groups.
     * Distribution will try to achieve multi-national students in a group.
     */
    private void nationalityDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                         LinkedList<Person> allPerson, String groupName) {
    //   LinkedList<Person> nationalityLinkList = CreateNationalityList(allPerson);
    //        int numOfDifferentNationality = numberOfDifferentNationality(allPerson);

    }

    /**
     * This Method distribute all students into n number of groups.
     * Distribution will try to achieve an balance number of gender in a group.
     */
    private void genderDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                    LinkedList<Person> allPerson, String groupName) throws CommandException {
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
     * This Method distribute all students into n number of groups
     * Distribution will try to include balanced gender and include multi-national students.
     */
    private void strictDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList,
                                    LinkedList<Person> allPersons, String groupName) throws CommandException {
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
     * This function shuffles all the person inside the LinkedList, with a specific seed.
     * @param person
     * @param seed
     * @return
     */
    public LinkedList<Person> shuffle(LinkedList<Person> person, Random seed) {
        requireNonNull(person);
        try {
            Collections.shuffle(person, seed);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException(MESSAGE_SHUFFLE_ERROR);
        }
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
    public LinkedList<Person> filterGender(LinkedList<Person> allPerson,
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
    private String groupNameConcatenation (int index, String groupName) throws CommandException {
        int newIndex = index + 1;
        groupName = groupName + String.valueOf(newIndex);
        if (existDuplicateGroup(groupName)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }
        return groupName;
    }

    /**
     * This function checks if there is any other groups that have the same name.
     * @param groupName check if groupName exist
     * @return false if there is no existing group.
     */
    private boolean existDuplicateGroup (String groupName) {
        ObservableList<Group> allGroups = model.getFilteredGroupList();
        requireNonNull(allGroups);
        for (Group gN : allGroups) {
            if (gN.getGroupName().toString().equals(groupName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function will check all n number of groupName with the existing address book for existing groups.
     * @param index : number of groups the user desire.
     * @param groupName : the name of the group the user desire
     * @throws CommandException
     */
    private void doesGroupNameExist(int index, String groupName) throws CommandException {
        for (int i = index; i > 0; i--) {
            groupNameConcatenation(i, groupName);
        }
    }

}

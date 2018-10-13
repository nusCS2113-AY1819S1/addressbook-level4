package seedu.address.model.distribute;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

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

public class DistributeAlgorithm {

    private static final String MESSAGE_INVALID_SIZE = "Number of Groups should not be more than Number of Persons";
    private static final String VALID_GENDER_MALE = "MALE";
    private static final String VALID_GENDER_FEMALE = "FEMALE";

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
        if(allPerson.size() < index){
            throw new CommandException(MESSAGE_INVALID_SIZE);
        }

        //Converts into ArrayList to use Randomizer via Collections
        LinkedList<Person> allPersonArrayList = new LinkedList<>(allPerson);
        allPersonArrayList = Randomizer(allPersonArrayList);

        if(!genderFlag && !nationalityFlag){
            NormalDistribution(index, groupArrayList, allPersonArrayList);
        }else if(!genderFlag && nationalityFlag){
            NationalityDistribution(index, groupArrayList, allPersonArrayList);
        }else if(genderFlag && !nationalityFlag){
            GenderDistribution(index, groupArrayList, allPersonArrayList);
        }else{
            StrictDistribution(index, groupArrayList, allPersonArrayList);
        }
    }

    private LinkedList<Person> Randomizer(LinkedList<Person> person){
        requireNonNull(person);
        Collections.shuffle(person);
        return person;
    }

    private void NormalDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList, LinkedList<Person> allPersonArrayList) {
        for(int i=index; i>0; i--){                             //number of groups to add into the groupArrayList
            ArrayList<Person> addPerson = new ArrayList<>();
            int paxInAGroup = allPersonArrayList.size()/i;
            while(paxInAGroup > 0){
                addPerson.add(allPersonArrayList.getLast());
                allPersonArrayList.removeLast();
                paxInAGroup--;
            }

            groupArrayList.add(addPerson);
        }

        // TODO: Add function that iterate groupArrayList and addMemebrs into the group
        for(int i=0;i<groupArrayList.size();i++){
            System.out.println("Group : " + i);
            for(int j=0; j<groupArrayList.get(i).size();j++){
                System.out.println(groupArrayList.get(i).get(j));
            }
        }
        groupArrayList.clear();
    }

    private void NationalityDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList, LinkedList<Person> allPerson) {
    //   LinkedList<Person> nationalityLinkList = CreateNationalityList(allPerson);
    //        int numOfDifferentNationality = NumberOfDifferentNationality(allPerson);

    }

    private void GenderDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList, LinkedList<Person> allPerson) {
        LinkedList<Person> maleLinkList = new LinkedList<>();
        LinkedList<Person> femaleLinkList = new LinkedList<>();
        int loopCounter = 0;
        int num = 0;

        maleLinkList = FilterGender(allPerson, maleLinkList, VALID_GENDER_MALE);
        femaleLinkList = FilterGender(allPerson, femaleLinkList, VALID_GENDER_FEMALE);

        while(maleLinkList.size()!=0 || femaleLinkList.size()!=0) {
            if(loopCounter%index == 0) num =0;
            while (num < index) {
                if(maleLinkList.size()==0 && femaleLinkList.size()==0) break;
                ArrayList<Person> temp = new ArrayList<>();
                if(maleLinkList.size()!=0){
                    GenderDistributionCheck(index, groupArrayList, maleLinkList, loopCounter, num, temp);
                    maleLinkList.removeLast();

                }else if(femaleLinkList.size()!=0){
                    GenderDistributionCheck(index, groupArrayList, femaleLinkList, loopCounter, num, temp);
                    femaleLinkList.removeLast();
                }
                num++;
                loopCounter++;
            }
        }

        // TODO: Add function that iterate groupArrayList and addMemebrs into the group
        for(int i=0;i<groupArrayList.size();i++){
            System.out.println( "Group : " + i);
            for(int j=0;j<groupArrayList.get(i).size();j++){
                System.out.println(groupArrayList.get(i).get(j));
            }
        }
        groupArrayList.clear();
    }

    private void StrictDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList, LinkedList<Person> allPersonArrayList) {
        System.out.println("Gender & Nationality Distribution");
    }

    //--- MODULAR METHODS ---

    //    private LinkedList<Person> CreateNationalityList(LinkedList<Person> allPerson) {
    //        //number of different nationality
    //        Map<Nationality, Long> counts = NumberOfDifferentNationality(allPerson);
    //        Map<Nationality, Long> sortedCount = PaxPerNationality(counts);
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

    private Map<Nationality, Long> NumberOfDifferentNationality(LinkedList<Person> allPerson){
        Map<Nationality, Long> numberOfNationality =
                allPerson.stream().collect(Collectors.groupingBy(e -> e.getNationality(), Collectors.counting()));
        return numberOfNationality;
    }

    private Map<Nationality, Long> PaxPerNationality(Map<Nationality, Long> numberOfNationality){
        Map<Nationality, Long> sortedNumOfNationality =
                numberOfNationality.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));
        return sortedNumOfNationality;
    }

    /**
     *
     * @param index
     * @param groupArrayList
     * @param femaleLinkList
     * @param loopCounter
     * @param num
     * @param temp
     */
    private void GenderDistributionCheck(int index, ArrayList<ArrayList<Person>> groupArrayList, LinkedList<Person> femaleLinkList, int loopCounter, int num, ArrayList<Person> temp) {
        if(loopCounter>=index){
            temp = groupArrayList.get(num);
            temp.add(femaleLinkList.getLast());
            groupArrayList.remove(num);
            groupArrayList.add(num,temp);
        }else{
            temp.add(femaleLinkList.getLast());
            groupArrayList.add(num,temp);
        }
    }

    /**
     *
     * @param allPerson
     * @param filteredGender
     * @param gender
     * @return
     */
    private LinkedList<Person> FilterGender(LinkedList<Person> allPerson, LinkedList<Person> filteredGender, String gender){
        for( Person p : allPerson){
            if(p.getGender().toString().equals(gender)){
                filteredGender.add(p);
            }
        }
        return filteredGender;
    }

}

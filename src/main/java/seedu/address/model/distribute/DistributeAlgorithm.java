package seedu.address.model.distribute;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class DistributeAlgorithm {

    public static final String MESSAGE_INVALID_SIZE = "Number of Groups should not be more than Number of Persons";
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
            System.out.println(addPerson);
        }
        // TODO: Add function that iterate groupArrayList and addMemebrs into the group
    }

    private void NationalityDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList, LinkedList<Person> allPersonArrayList) {
        System.out.println("Nationality Distribution");
    }

    private void GenderDistribution(int index, ArrayList<ArrayList<Person>> groupArrayList, LinkedList<Person> allPersonArrayList) {
        LinkedList<Person> maleLinkList = new LinkedList<>();
        LinkedList<Person> femaleLinkList = new LinkedList<>();

        for( Person p : allPersonArrayList){
            if(p.getGender().toString().equals(VALID_GENDER_MALE)){
                maleLinkList.add(p);

            }else if(p.getGender().toString().equals(VALID_GENDER_FEMALE)){
                femaleLinkList.add(p);

            }
        }

        //creates 7 groups in this for loop
        //groupArrayList.ensureCapacity(index);
        int loopCounter = 0;
        int num = 0;
        while(maleLinkList.size()!=0 || femaleLinkList.size()!=0) {
            if(loopCounter%index == 0) num =0;
            while (num < index) {
                if(maleLinkList.size()==0 && femaleLinkList.size()==0) break;
                //iterate from 0 to index to add once
                ArrayList<Person> temp = new ArrayList<>();

                if(maleLinkList.size()!=0){
                    if(loopCounter>=index){
                        temp = groupArrayList.get(num);
                        temp.add(maleLinkList.getLast());
                        groupArrayList.remove(num);
                        groupArrayList.add(num,temp);
                    }else{
                        temp.add(maleLinkList.getLast());
                        groupArrayList.add(num,temp);
                    }
                    maleLinkList.removeLast();

                }else if(femaleLinkList.size()!=0){
                    if(loopCounter>=index){
                        temp = groupArrayList.get(num);
                        temp.add(femaleLinkList.getLast());
                        groupArrayList.remove(num);
                        groupArrayList.add(num,temp);
                    }else{
                        temp.add(femaleLinkList.getLast());
                        groupArrayList.add(num,temp);
                    }
                    femaleLinkList.removeLast();
                }
                num++;
                loopCounter++;
            }
        }

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

}

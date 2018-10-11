package seedu.address.model.distribute;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.group.Group;

public class DistributeAlgorithm {

    public DistributeAlgorithm(Distribute dist){
        requireNonNull(dist);
        int index = dist.getIndex();
        String groupName = dist.getGroupName().toString();
        boolean genderFlag = dist.getGender();
        boolean nationalityFlag = dist.getNationality();

        ArrayList<Group> groupArrayList = new ArrayList<Group>();
        groupArrayList.ensureCapacity(index);


        System.out.println(groupName);

        if(!genderFlag && !nationalityFlag){
            NormalDistribution(groupArrayList);
        }else if(!genderFlag && nationalityFlag){
            NationalityDistribution(groupArrayList);
        }else if(genderFlag && !nationalityFlag){
            GenderDistribution(groupArrayList);
        }else{
            StrictDistribution(groupArrayList);
        }

    }


    private void NormalDistribution(ArrayList<Group> groupArrayList) {
        System.out.println("Normal Distribution");
    }

    private void NationalityDistribution(ArrayList<Group> groupArrayList) {
        System.out.println("Nationality Distribution");
    }

    private void GenderDistribution(ArrayList<Group> groupArrayList) {
        System.out.println("Gender Distribution");
    }

    private void StrictDistribution(ArrayList<Group> groupArrayList) {
        System.out.println("Gender & Nationality Distribution");
    }

}

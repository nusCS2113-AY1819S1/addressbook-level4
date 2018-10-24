package seedu.address.testutil;

import static seedu.address.logic.parser.ParserUtil.parseIsFlagged;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.distribute.Distribute;
import seedu.address.model.group.GroupName;

/**
 *  This class helps to build a Distribute Command for Testing purposes.
 */
public class DistributeBuilder {

    public static final String DEFAULT_INDEX = "3";
    public static final String DEFAULT_GROUP_NAME = "CS2113-T13-";
    public static final String DEFAULT_GENDER_FLAG = "false";
    public static final String DEFAULT_NATIONALITY_FLAG = "false";

    private int index;
    private GroupName groupName;
    private boolean genderFlag;
    private boolean nationalityFlag;

    public DistributeBuilder() throws ParseException {
        this.index = Integer.parseInt(DEFAULT_INDEX);
        this.groupName = new GroupName(DEFAULT_GROUP_NAME);
        this.genderFlag = parseIsFlagged(DEFAULT_GENDER_FLAG);
        this.nationalityFlag = parseIsFlagged(DEFAULT_NATIONALITY_FLAG);
    }

    public DistributeBuilder(Distribute distribute) {
        index = distribute.getIndex();
        groupName = distribute.getGroupName();
        genderFlag = distribute.getGender();
        nationalityFlag = distribute.getNationality();
    }

    public DistributeBuilder setIndex (String indexValue) {
        this.index = Integer.parseInt(indexValue);
        return this;
    }

    public DistributeBuilder setGroupName (String groupName) {
        this.groupName = new GroupName(groupName);
        return this;
    }

    public DistributeBuilder setGenderFlag (String genderFlag) throws ParseException {
        this.genderFlag = parseIsFlagged(genderFlag);
        return this;
    }

    public DistributeBuilder setNationalityFlag (String nationalityFlag) throws ParseException {
        this.nationalityFlag = parseIsFlagged(nationalityFlag);
        return this;
    }

    public Distribute build() {
        return new Distribute(index, groupName, genderFlag, nationalityFlag);
    }


}

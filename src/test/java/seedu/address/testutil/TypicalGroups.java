package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_LAB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_TUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_LAB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_TUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIFFICULTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TUT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.group.Group;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final Group TUTORIAL = new GroupBuilder().withName("TUT[1]")
            .withLocation("COM1-B103")
            .withTags("CS")
            .build();

    //TAGLESS
    public static final Group LABORATORY = new GroupBuilder().withName("LAB[01]")
            .withLocation("E4-03-07")
            .build();

    public static final Group LECTURE = new GroupBuilder().withName("CS2113-LECTURE")
            .withLocation("LT15")
            .withTags("CS")
            .build();

    public static final Group MA1508E_TUT = new GroupBuilder().withName("TUT[TE3]")
            .withLocation("E1-06-04")
            .withTags("MA", "SU")
            .build();

    public static final Group GEQ1000_TUT = new GroupBuilder().withName("TUT[E22]")
            .withLocation("TP-SR6")
            .withTags("EvenWeeks")
            .withTags("GE")
            .build();

    public static final Group ST2334_LEC = new GroupBuilder().withName("LEC[1]")
            .withLocation("UT-AUD2")
            .withTags("Weekly", "ST")
            .build();

    // Manually added
    public static final Group IS3261_LEC = new GroupBuilder().withName("LEC[1]")
            .withLocation("COM1-VCRM")
            .withTags("Weekly", "IS")
            .build();

    public static final Group MA1508_EXAM = new GroupBuilder().withName("EXAM")
            .withLocation("MPSH1")
            .withTags("MPSH1A", "EXAM", "MIDTERM")
            .build();

    // Manually added - Group's details found in {@code CommandTestUtil}
    public static final Group TUT = new GroupBuilder().withName(VALID_GROUP_NAME_TUT)
            .withLocation(VALID_GROUP_LOCATION_TUT)
            .withTags(VALID_TAG_TUT)
            .build();

    public static final Group LAB = new GroupBuilder().withName(VALID_GROUP_NAME_LAB)
            .withLocation(VALID_GROUP_LOCATION_LAB)
            .withTags(VALID_TAG_LAB, VALID_TAG_DIFFICULTY)
            .build();

    //public static final String KEYWORD_MATCHING_MA1508E = "MA1508e"; // A keyword that matches MEIER

    private TypicalGroups() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Group group : getTypicalGroups()) {
            ab.createGroup(group);
        }
        return ab;
    }

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(TUTORIAL, LABORATORY, LECTURE, MA1508E_TUT, GEQ1000_TUT, ST2334_LEC));
    }
}

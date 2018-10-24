package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.classroom.Classroom;

/**
 * A utility class containing a list of {@code Classroom} objects to be used in tests.
 */
public class TypicalClassrooms {

    public static final Classroom T16 = new ClassroomBuilder().withClassName("T16")
            .withModuleCode("CG1111").withEnrollment("20").build();
    public static final Classroom T17 = new ClassroomBuilder().withClassName("T17")
            .withModuleCode("CG1111").withEnrollment("17").build();
    public static final Classroom T18 = new ClassroomBuilder().withClassName("T18")
            .withModuleCode("CG1112").withEnrollment("18").build();
    public static final Classroom D11 = new ClassroomBuilder().withClassName("D11")
            .withModuleCode("GEQ1000").withEnrollment("30").build();
    public static final Classroom ALL = new ClassroomBuilder().withClassName("ALL")
            .withModuleCode("CFG1000").withEnrollment("425").build();

    private TypicalClassrooms() {} // prevents instantiation

    public static List<Classroom> getTypicalClassrooms() {
        return new ArrayList<>(Arrays.asList(T16, T17, T18, D11, ALL));
    }
}

package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;

/**
 * A utility class containing a list of {@code Schedule} objects to be used in tests.
 */

public class TypicalSchedules {
    public static final Schedule CS1231 = new ScheduleBuilder().withTheDate("09092018").withStartTime("1230")
            .withEndTime("1400").withEventName("CS1231 Exam").build();

    // Manually added
    public static final Schedule GERQUIZ = new ScheduleBuilder().withTheDate("09092018").withStartTime("0900")
            .withEndTime("1000").withEventName("GER QUIZ").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    //    // Manually added - Person's details found in {@code CommandTestUtil}
    //    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
    //            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    //    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
    //         .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
    //            .build();

    private TypicalSchedules() {} // prevents instantiation

    public static List<Schedule> getTypicalSchedules() {
        return new ArrayList<>(Arrays.asList(CS1231));
    }
}

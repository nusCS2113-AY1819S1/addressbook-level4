package seedu.recruit.testutil;

import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_AGE_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EDUCATION_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EDUCATION_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_JOB_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.candidate.Candidate;

/**
 * A utility class containing a list of {@code Candidate} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Candidate ALICE = new PersonBuilder().withName("Alice Pauline").withGender("F").withAge("31")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com").withPhone("94351253")
            .withJob("Waiter").withEducation("O level").withSalary("1000").withTags("friends").build();
    public static final Candidate BENSON = new PersonBuilder().withName("Benson Meier").withGender("M").withAge("32")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withPhone("98765432")
            .withJob("Waiter").withEducation("O level").withSalary("1000").withTags("owesMoney", "friends").build();
    public static final Candidate CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563").withGender("M")
            .withAge("33").withEmail("heinz@example.com").withJob("Waiter").withEducation("O level").withSalary("1000")
            .withAddress("wall street").build();
    public static final Candidate DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withAge("34").withGender("M").withEmail("cornelia@example.com").withAddress("10th street")
            .withJob("Waiter").withEducation("O level").withSalary("1000").withTags("friends").build();
    public static final Candidate ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224").withAge("35")
            .withGender("F").withEmail("werner@example.com").withJob("Waiter").withEducation("O level")
            .withSalary("1000").withAddress("michegan ave").build();
    public static final Candidate FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427").withAge("36")
            .withJob("Waiter").withEducation("O level").withSalary("1000").withGender("F")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Candidate GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withAge("37").withJob("Waiter").withEducation("O level").withSalary("1000")
            .withGender("M").withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Candidate HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424").withAge("38")
            .withJob("Waiter").withEducation("O level").withSalary("1000")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Candidate IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131").withAge("39")
            .withJob("Waiter").withEducation("O level").withSalary("1000")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Candidate's details found in {@code CommandTestUtil}
    public static final Candidate AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withAge(VALID_AGE_AMY).withGender(VALID_GENDER_AMY).withJob(VALID_JOB_AMY)
            .withEducation(VALID_EDUCATION_AMY).withSalary(VALID_SALARY_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Candidate BOB = new PersonBuilder().withName(VALID_NAME_BOB).withGender(VALID_GENDER_BOB)
            .withAge(VALID_AGE_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withJob(VALID_JOB_BOB)
            .withEducation(VALID_EDUCATION_BOB).withSalary(VALID_SALARY_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code CandidateBook} with all the typical persons.
     */
    public static CandidateBook getTypicalAddressBook() {
        CandidateBook ab = new CandidateBook();
        for (Candidate candidate : getTypicalPersons()) {
            ab.addPerson(candidate);
        }
        return ab;
    }

    public static List<Candidate> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}

package seedu.address.model.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ClubBudgetElementsBook;
import seedu.address.model.FinalBudgetsBook;
import seedu.address.model.LoginBook;
import seedu.address.model.ReadOnlyAddressBook;

import seedu.address.model.ReadOnlyClubBudgetElementsBook;
import seedu.address.model.ReadOnlyFinalBudgetBook;
import seedu.address.model.ReadOnlyLoginBook;
import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.budgetelements.ClubName;
import seedu.address.model.budgetelements.ExpectedTurnout;
import seedu.address.model.budgetelements.NumberOfEvents;
import seedu.address.model.clubbudget.FinalClubBudget;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserPassword;
import seedu.address.model.login.UserRole;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skill;

import seedu.address.model.person.SkillLevel;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook}, {@code LoginBook} and {@code ClubBudgetElementsBook}
 * with sample data.
 */
public class SampleDataUtil {
    public static final Skill EMPTY_SKILL = new Skill("");
    public static final SkillLevel EMPTY_SKILLLEVEL = new SkillLevel(0);
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Skill("Photography"), new SkillLevel(90),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Skill("Videography"), new SkillLevel(50),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Skill("Design"), new SkillLevel(45),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Skill("Photoshop"), new SkillLevel(50),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Skill("Stage Managing"), new SkillLevel(30),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Skill("Public Speaking"), new SkillLevel(80),
                getTagSet("colleagues"))
        };
    }

    public static LoginDetails[] getSampleLoginDetails() {
        String encryptedLoginIdOne = null;
        String encryptedLoginPasswordOne = null;
        String encryptedLoginRoleOne = null;
        String encryptedLoginIdTwo = null;
        String encryptedLoginPasswordTwo = null;
        String encryptedLoginRoleTwo = null;
        String encryptedLoginIdThree = null;
        String encryptedLoginPasswordThree = null;
        String encryptedLoginRoleThree = null;
        try {
            encryptedLoginIdOne = Base64.getEncoder().encodeToString("A1234567M".getBytes("utf-8"));
            encryptedLoginPasswordOne = Base64.getEncoder().encodeToString("zaq1xsw2cde3".getBytes("utf-8"));
            encryptedLoginRoleOne = Base64.getEncoder().encodeToString("president".getBytes("utf-8"));
            encryptedLoginIdTwo = Base64.getEncoder().encodeToString("A1234568M".getBytes("utf-8"));
            encryptedLoginPasswordTwo = Base64.getEncoder().encodeToString("zaq1xsw2cde3".getBytes("utf-8"));
            encryptedLoginRoleTwo = Base64.getEncoder().encodeToString("treasurer".getBytes("utf-8"));
            encryptedLoginIdThree = Base64.getEncoder().encodeToString("A1234569M".getBytes("utf-8"));
            encryptedLoginPasswordThree = Base64.getEncoder().encodeToString("zaq1xsw2cde3".getBytes("utf-8"));
            encryptedLoginRoleThree = Base64.getEncoder().encodeToString("member".getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            return new LoginDetails[]{
                new LoginDetails(new UserId(encryptedLoginIdOne), new UserPassword(encryptedLoginPasswordOne),
                        new UserRole(encryptedLoginRoleOne)),
                new LoginDetails(new UserId(encryptedLoginIdTwo), new UserPassword(encryptedLoginPasswordTwo),
                        new UserRole(encryptedLoginRoleTwo)),
                new LoginDetails(new UserId(encryptedLoginIdThree), new UserPassword(encryptedLoginPasswordThree),
                        new UserRole(encryptedLoginRoleThree))
            };
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new LoginDetails[0];
    }

    public static ClubBudgetElements[] getSampleClubBudgetElements() {
        return new ClubBudgetElements[] {
            new ClubBudgetElements(new ClubName("Computing Club"), new ExpectedTurnout("200"), new NumberOfEvents("5")),
            new ClubBudgetElements(new ClubName("ECE Club"), new ExpectedTurnout("500"), new NumberOfEvents("2"))
        };
    }

    public static FinalClubBudget[] getSampleFinalClubBudget() {
        String allocatedBudget1 = "1000";
        String allocatedBudget2 = "1000";
        return new FinalClubBudget[] {
            new FinalClubBudget(new ClubName("Computing Club"), Integer.parseInt(allocatedBudget1)),
            new FinalClubBudget(new ClubName("ECE Club"), Integer.parseInt(allocatedBudget2))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyLoginBook getSampleLoginBook() {
        LoginBook sampleLb = new LoginBook();
        for (LoginDetails sampleAccount : getSampleLoginDetails()) {
            sampleLb.createAccount(sampleAccount);
        }
        return sampleLb;
    }

    public static ReadOnlyClubBudgetElementsBook getSampleClubBudgetElementsBook() {
        ClubBudgetElementsBook sampleCBb = new ClubBudgetElementsBook();
        /*for (ClubBudgetElements sampleClubBudgetElements : getSampleClubBudgetElements()) {
            sampleCBb.addClub(sampleClubBudgetElements);
        }*/
        return sampleCBb;
    }

    public static ReadOnlyFinalBudgetBook getSampleFinalBudgetsBook() {
        FinalBudgetsBook sampleFBb = new FinalBudgetsBook();
        /*for (FinalClubBudget sampleFinalClubBudget : getSampleFinalClubBudget()) {
            sampleFBb.addClubBudget(sampleFinalClubBudget);
        }*/
        return sampleFBb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

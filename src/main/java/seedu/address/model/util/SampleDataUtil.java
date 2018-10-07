package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.CandidateBook;
import seedu.address.model.JobBook;
import seedu.address.model.ReadOnlyCandidateBook;
import seedu.address.model.ReadOnlyJobBook;
import seedu.address.model.candidate.Age;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.Education;
import seedu.address.model.candidate.Gender;
import seedu.address.model.candidate.Name;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Email;
import seedu.address.model.commons.Phone;
import seedu.address.model.company.CompanyName;
import seedu.address.model.joboffer.AgeRange;
import seedu.address.model.joboffer.Job;
import seedu.address.model.joboffer.JobOffer;
import seedu.address.model.joboffer.Salary;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CandidateBook} with sample data.
 */


public class SampleDataUtil {
    public static Candidate[] getSampleCandidates() {
        return new Candidate[] {
            new Candidate(new Name("Alex Yeoh"), new Gender("M"), new Age ("20"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Job("Waiter"), new Education("O levels"), new Salary("1000"),
                    getTagSet("friends")),
            new Candidate(new Name("Bernice Yu"), new Gender("F"), new Age ("20"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Job("Waiter"), new Education("O levels"), new Salary("1000"),
                    getTagSet("colleagues", "friends")),
            new Candidate(new Name("Charlotte Oliveiro"), new Gender("F"), new Age ("20"),
                    new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Job("Waiter"),
                    new Education("O levels"), new Salary("1000"),
                getTagSet("neighbours")),
            new Candidate(new Name("David Li"), new Gender("M"), new Age ("20"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Job("Waiter"), new Education("O levels"), new Salary("1000"),
                    getTagSet("family")),
            new Candidate(new Name("Irfan Ibrahim"), new Gender("M"), new Age ("20"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Job("Waiter"), new Education("O levels"), new Salary("1000"),
                    getTagSet("classmates")),
            new Candidate(new Name("Roy Balakrishnan"), new Gender("M"), new Age ("20"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Job("Waiter"), new Education("O levels"), new Salary("1000"),
                    getTagSet("colleagues"))
        };
    }

    public static JobOffer[] getSampleJobOffers() {
        return new JobOffer[]{
            new JobOffer(new CompanyName("Mcdonalds"), new Job("Cashier"), new Gender("M"),
                new AgeRange("20-30"), new Education("O levels"), new Salary("1000")),
            new JobOffer(new CompanyName("KFC"), new Job("Cook"), new Gender("F"),
                    new AgeRange("20-30"), new Education("O levels"), new Salary("1000")),
        };
    }

    public static ReadOnlyCandidateBook getSampleCandidateBook() {
        CandidateBook sampleAb = new CandidateBook();
        for (Candidate sampleCandidate : getSampleCandidates()) {
            sampleAb.addPerson(sampleCandidate);
        }
        return sampleAb;
    }

    public static ReadOnlyJobBook getSampleJobBook() {
        JobBook sampleJb = new JobBook();
        for (JobOffer sampleJobOffer : getSampleJobOffers()) {
            sampleJb.addJobOffer(sampleJobOffer);
        }
        return sampleJb;
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

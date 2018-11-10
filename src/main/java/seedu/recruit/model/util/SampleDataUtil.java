package seedu.recruit.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.ReadOnlyCandidateBook;
import seedu.recruit.model.ReadOnlyCompanyBook;
import seedu.recruit.model.candidate.Age;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.candidate.Education;
import seedu.recruit.model.candidate.Gender;
import seedu.recruit.model.candidate.Name;
import seedu.recruit.model.candidate.UniqueCandidateList;
import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.AgeRange;
import seedu.recruit.model.joboffer.Job;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.model.joboffer.Salary;

import seedu.recruit.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CandidateBook} with sample data.
 */


public class SampleDataUtil {
    public static Candidate[] getSampleCandidates() {
        return new Candidate[] {
            new Candidate(new Name("Irfan Ali"), new Gender("M"), new Age ("26"), new Phone("87438807"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Job("IT"), new Education("BACHELOR"), new Salary("4000"),
                    getTagSet("Tattoo")),
            new Candidate(new Name("Bernice Yu"), new Gender("F"), new Age ("17"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Job("Waiter"), new Education("OLEVELS"), new Salary("1000"),
                    getTagSet("Student", "FlatFooted")),
            new Candidate(new Name("Charlotte Oliveiro"), new Gender("F"), new Age ("20"),
                    new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Job("Software Engineer"),
                    new Education("BACHELOR"), new Salary("3500")
                    , getTagSet("Referral")),
            new Candidate(new Name("David Li"), new Gender("M"), new Age ("18"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Job("Cleaner"), new Education("OLEVELS"), new Salary("1000"),
                    getTagSet("Diabetic")),
            new Candidate(new Name("Irfan Ibrahim"), new Gender("M"), new Age ("20"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Job("Waiter"), new Education("OLEVELS"), new Salary("1000"),
                    getTagSet("ExConvict")),
            new Candidate(new Name("Roy Balakrishnan"), new Gender("M"), new Age ("50"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Job("Waiter"), new Education("OLEVELS"), new Salary("1000")
                    , getTagSet("HighBlood", "Diabetic")),
            new Candidate(new Name("Tom Ong"), new Gender("M"), new Age ("21"), new Phone("98765432"),
                    new Email("tomong@example.com"), new Address("Blk 311, Clementi Ave 2, #02-25"),
                    new Job("Waiter"), new Education("OLEVELS"), new Salary("1000")
                    , getTagSet("excuseHeavyLoad", "skinCondition")),
            new Candidate(new Name("Ruth Lee"), new Gender("M"), new Age ("30"), new Phone("82884921"),
                    new Email("ruthlee@example.com"), new Address("Blk 123, Jurong West Street 52, #01-11"),
                    new Job("Accountant"), new Education("BACHELOR")
                    , new Salary("3500"), getTagSet("eczema")),
            new Candidate(new Name("Brad Bird"), new Gender("M"), new Age ("35"), new Phone("90001230"),
                    new Email("bradbird@example.com"), new Address("Blk 123, Teban Gardens, #01-23"),
                    new Job("Chemical Engineer"), new Education("BACHELOR")
                    , new Salary("3500"), getTagSet("Referral")),
            new Candidate(new Name("Eugene Yu"), new Gender("M"), new Age ("17"), new Phone("8121313"),
                    new Email("eugeneyu@example.com"), new Address("Blk 34, Marymount Road, #01-23"),
                    new Job("Cashier"), new Education("OLEVELS")
                    , new Salary("1000"), getTagSet("Student"))
        };
    }

    public static JobOffer[] getSampleJobOffers() {
        return new JobOffer[] {
        new JobOffer(new CompanyName("McDonalds"), new Job("Cashier"), new Gender("F"),
                new AgeRange("20-35"), new Education("OLEVELS"),
                new Salary("1000"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("McDonalds"), new Job("Cook"), new Gender("F"),
                new AgeRange("20-35"), new Education("OLEVELS"),
                new Salary("1000"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("KFC"), new Job("Cashier"), new Gender("M"),
                new AgeRange("20-40"), new Education("OLEVELS"),
                new Salary("1100"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("KFC"), new Job("Cook"), new Gender("M"),
                new AgeRange("20-40"), new Education("PRIMARY"),
                new Salary("1000"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("KFC"), new Job("Cleaner"), new Gender("F"),
                new AgeRange("20-60"), new Education("PRIMARY"),
                new Salary("1000"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("FaceBook"), new Job("Software Engineer"),
                new Gender("M"), new AgeRange("25-35"), new Education("BACHELOR"),
                new Salary("4000"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("FaceBook"), new Job("Designer"),
                new Gender("F"), new AgeRange("25-35"), new Education("BACHELOR"),
                new Salary("3500"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("FaceBook"), new Job("Receptionist"),
                new Gender("F"), new AgeRange("25-35"), new Education("OLEVELS"),
                new Salary("2500"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("Microsoft"), new Job("Software Engineer"),
                new Gender("F"), new AgeRange("25-35"), new Education("BACHELOR"),
                new Salary("3800"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("Microsoft"), new Job("Software Tester"),
                new Gender("M"), new AgeRange("25-45"), new Education("ALEVELS"),
                new Salary("2500"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("Microsoft"), new Job("Sales Representative"),
                new Gender("M"), new AgeRange("25-35"), new Education("DIPLOMA"),
                new Salary("2500"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("Microsoft"), new Job("Finance Executive"),
                new Gender("M"), new AgeRange("25-35"), new Education("DIPLOMA"),
                new Salary("2800"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("Microsoft"), new Job("Account Executive"),
                new Gender("F"), new AgeRange("25-35"), new Education("DIPLOMA"),
                new Salary("3000"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("Prudential"), new Job("Insurance Agent"),
                new Gender("M"), new AgeRange("25-35"), new Education("DIPLOMA"),
                new Salary("1800"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("Prudential"), new Job("Financial Consultant"),
                new Gender("F"), new AgeRange("25-35"), new Education("ALEVELS"),
                new Salary("1800"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("Prudential"), new Job("Accounts Executive"),
                new Gender("F"), new AgeRange("25-35"), new Education("DIPLOMA"),
                new Salary("2500"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("DBS"), new Job("Accounts Executive"),
                new Gender("M"), new AgeRange("25-35"), new Education("DIPLOMA"),
                new Salary("2800"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("DBS"), new Job("Bank Teller"),
                new Gender("M"), new AgeRange("25-35"), new Education("DIPLOMA"),
                new Salary("3000"), new UniqueCandidateList()),
        new JobOffer(new CompanyName("DBS"), new Job("Data Entry Assistan"),
                new Gender("M"), new AgeRange("17-50"), new Education("OLEVELS"),
                new Salary("1200"), new UniqueCandidateList())
        };
    }

    public static Company[] getSampleCompanies() {
        return new Company[]{
            new Company(new CompanyName("McDonalds"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            new Email("mcdonalds@example.com"), new Phone("61116222")),
            new Company(new CompanyName("KFC"), new Address("370 Alexandra Rd, #01-15/16 Anchorpoint"),
            new Email("kfc@example.com"), new Phone("61231222")),
            new Company(new CompanyName("DBS"),
                    new Address("12 Marina Boulevard, DBS Asia Central, Marina Bay Financial Centre Tower 3"),
                    new Email("dbs@example.com"), new Phone("65593873")),
            new Company(new CompanyName("Prudential"),
                    new Address("7 Straits View #06-01, Marina One East Tower"),
                    new Email("prudential@example.com"), new Phone("67892391")),
            new Company(new CompanyName("FaceBook"),
                    new Address("7 Straits View #15-01, Marina One East Tower"),
                    new Email("facebook@example.com"), new Phone("67771123")),
            new Company(new CompanyName("Microsoft"),
                    new Address("1 Marina Boulevard, #22-01"),
                    new Email("microsoft@example.com"), new Phone("65548556"))

        };
    }


    public static ReadOnlyCandidateBook getSampleCandidateBook() {
        CandidateBook sampleAb = new CandidateBook();
        for (Candidate sampleCandidate : getSampleCandidates()) {
            sampleAb.addCandidate(sampleCandidate);
        }
        return sampleAb;
    }

    public static ReadOnlyCompanyBook getSampleCompanyBook() {
        CompanyBook sampleCb = new CompanyBook();
        for (Company sampleCompany : getSampleCompanies()) {
            sampleCb.addCompany(sampleCompany);
        }
        for(JobOffer sampleJobOffer : getSampleJobOffers()) {
            sampleCb.addJobOffer(sampleJobOffer);
        }
        return sampleCb;
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

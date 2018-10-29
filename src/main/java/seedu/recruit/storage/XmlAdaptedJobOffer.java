package seedu.recruit.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.recruit.commons.exceptions.IllegalValueException;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.candidate.Education;
import seedu.recruit.model.candidate.Gender;
import seedu.recruit.model.candidate.UniqueCandidateList;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.AgeRange;
import seedu.recruit.model.joboffer.Job;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.model.joboffer.Salary;

/**
 * JAXB-friendly version of the JobOffer.
 */
public class XmlAdaptedJobOffer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job offer's %s field is missing!";

    @XmlElement(required = true)
    private String job;
    @XmlElement(required = true)
    private String companyName;
    @XmlElement(required = true)
    private String gender;
    @XmlElement(required = true)
    private String ageRange;
    @XmlElement(required = true)
    private String education;
    @XmlElement(required = true)
    private String salary;
    @XmlElement(required = true)
    private List<XmlAdaptedCandidate> candidateList;


    /**
     * Constructs an XmlAdaptedJobOffer.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedJobOffer() {}

    /**
     * Constructs an {@code XmlAdaptedJobOffer} with the given job offer details.
     */

    public XmlAdaptedJobOffer(String companyName, String job, String gender, String ageRange, String education,
                              String salary, List<XmlAdaptedCandidate> candidateList) {
        this.companyName = companyName;
        this.job = job;
        this.gender = gender;
        this.ageRange = ageRange;
        this.education = education;
        this.salary = salary;
        this.candidateList = candidateList;

    }

    /**
     * Converts a given JobOffer into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedJobOffer
     */

    public XmlAdaptedJobOffer(JobOffer source) {

        companyName = source.getCompanyName().value;
        job = source.getJob().value;
        gender = source.getGender().value;
        ageRange = source.getAgeRange().value;
        education = source.getEducation().value;
        salary = source.getSalary().value;
        candidateList = source.getUniqueCandidateList().asUnmodifiableObservableList()
                .stream().map(XmlAdaptedCandidate::new).collect(Collectors.toList());
    }


    /**
     * Converts this jaxb-friendly adapted job offer object into the model's JobOffer object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted job offer
     */

    public JobOffer toModelType() throws IllegalValueException {
        final List<Candidate> jobCandidateList = new ArrayList<>();
        if (candidateList != null) {
            for (XmlAdaptedCandidate candidate : candidateList) {
                jobCandidateList.add(candidate.toModelType());
            }
        }

        if (companyName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Job.class.getSimpleName()));
        }

        if (!CompanyName.isValidCompanyName(companyName)) {
            throw new IllegalValueException(CompanyName.MESSAGE_COMPANY_CONSTRAINTS);
        }

        final CompanyName modelCompanyName = new CompanyName(companyName);


        if (job == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Job.class.getSimpleName()));
        }

        if (!Job.isValidJob(job)) {
            throw new IllegalValueException(Job.MESSAGE_JOB_CONSTRAINTS);
        }

        final Job modelJob = new Job(job);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }

        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_GENDER_CONSTRAINTS);
        }

        final Gender modelGender = new Gender(gender);

        if (ageRange == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AgeRange.class.getSimpleName()));
        }

        if (!AgeRange.isValidAgeRange(ageRange)) {
            throw new IllegalValueException(AgeRange.MESSAGE_AGE_RANGE_CONSTRAINTS);
        }

        final AgeRange modelAgeRange = new AgeRange(ageRange);

        if (education == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Education.class.getSimpleName()));
        }

        if (!Education.isValidEducation(education)) {
            throw new IllegalValueException(Education.MESSAGE_EDUCATION_CONSTRAINTS);
        }

        final Education modelEducation = new Education(education);

        if (salary == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Salary.class.getSimpleName()));
        }

        if (!Salary.isValidSalary(salary)) {
            throw new IllegalValueException(Salary.MESSAGE_SALARY_CONSTRAINTS);
        }

        final Salary modelSalary = new Salary(salary);

        final UniqueCandidateList modelCandidateList = new UniqueCandidateList();

        modelCandidateList.setCandidates(jobCandidateList);


        return new JobOffer(modelCompanyName, modelJob, modelGender, modelAgeRange, modelEducation, modelSalary,
                modelCandidateList);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedJobOffer)) {
            return false;
        }

        XmlAdaptedJobOffer otherPerson = (XmlAdaptedJobOffer) other;
        return Objects.equals(companyName, otherPerson.companyName)
                && Objects.equals(job, otherPerson.job);
    }
}


package seedu.recruit.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.DESC_ALFA_JOB;
import static seedu.recruit.logic.commands.CommandTestUtil.DESC_BMW_JOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_AGE_RANGE_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EDUCATION_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_SALARY_BOB;

import org.junit.Test;

import seedu.recruit.logic.commands.EditJobDetailsCommand.EditJobOfferDescriptor;
import seedu.recruit.testutil.EditJobOfferDescriptorBuilder;

public class EditJobOfferDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditJobOfferDescriptor descriptorWithSameValues = new EditJobOfferDescriptor(DESC_ALFA_JOB);
        assertTrue(DESC_ALFA_JOB.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ALFA_JOB.equals(DESC_ALFA_JOB));

        // null -> returns false
        assertFalse(DESC_ALFA_JOB.equals(null));

        // different types -> returns false
        assertFalse(DESC_ALFA_JOB.equals(5));

        // different values -> returns false
        assertFalse(DESC_ALFA_JOB.equals(DESC_BMW_JOB));

        // different company name -> returns false
        EditJobOfferDescriptor editedAlfaJob = new EditJobOfferDescriptorBuilder(DESC_ALFA_JOB)
                .withCompanyName(VALID_NAME_BMW).build();
        assertFalse(DESC_ALFA_JOB.equals(editedAlfaJob));

        // different job -> returns false
        editedAlfaJob = new EditJobOfferDescriptorBuilder(DESC_ALFA_JOB).withJob(VALID_JOB_BOB).build();
        assertFalse(DESC_ALFA_JOB.equals(editedAlfaJob));

        // different gender -> returns false
        editedAlfaJob = new EditJobOfferDescriptorBuilder(DESC_ALFA_JOB).withGender(VALID_GENDER_BOB).build();
        assertFalse(DESC_ALFA_JOB.equals(editedAlfaJob));

        // different age range -> returns false
        editedAlfaJob = new EditJobOfferDescriptorBuilder(DESC_ALFA_JOB)
                .withAgeRange(VALID_AGE_RANGE_BMW).build();
        assertFalse(DESC_ALFA_JOB.equals(editedAlfaJob));

        // different education -> returns false
        editedAlfaJob = new EditJobOfferDescriptorBuilder(DESC_ALFA_JOB).withEducation(VALID_EDUCATION_BOB).build();
        assertFalse(DESC_ALFA_JOB.equals(editedAlfaJob));

        // different salary -> returns false
        editedAlfaJob = new EditJobOfferDescriptorBuilder(DESC_ALFA_JOB).withSalary(VALID_SALARY_BOB).build();
        assertFalse(DESC_ALFA_JOB.equals(editedAlfaJob));

    }
}

package seedu.address.testutil;

import seedu.address.logic.commands.EditHistCommand.EditHistDescriptor;
import seedu.address.model.medhistory.Allergy;
import seedu.address.model.medhistory.DischargeStatus;
import seedu.address.model.medhistory.MedHistDate;
import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.medhistory.PrevCountry;

//@@author xShaivan
/**
 * A utility class to help with building EditHistDescriptor objects.
 */
public class EditHistDescriptorBuilder {
    private EditHistDescriptor editHistDescriptor;

    public EditHistDescriptorBuilder() {
        editHistDescriptor = new EditHistDescriptor();
    }

    public EditHistDescriptorBuilder(EditHistDescriptor editHistDescriptor) {
        this.editHistDescriptor = new EditHistDescriptor(editHistDescriptor);
    }

    /**
     * Returns an {@code EditHistDescriptor} with fields containing {@code medHistory}'s details
     */
    public EditHistDescriptorBuilder(MedHistory medHistory) {
        editHistDescriptor = new EditHistDescriptor();
        editHistDescriptor.setAllergy(medHistory.getAllergy().orElse(new Allergy("")));
        editHistDescriptor.setDischargeStatus(medHistory.getDischargeStatus().orElse(new DischargeStatus("")));
        editHistDescriptor.setPrevCountry(medHistory.getPrevCountry().orElse(new PrevCountry("")));
        editHistDescriptor.setMedHistDate(medHistory.getMedHistDate());
    }

    /**
     * Sets the {@code MedHistDate} of the {@code EditHistDescriptor} that we are building.
     */
    public EditHistDescriptorBuilder withMedHistDate(String medHistDate) {
        editHistDescriptor.setMedHistDate(new MedHistDate(medHistDate));
        return this;
    }
    /**
     * Sets the {@code Allergy} of the {@code EditHistDescriptor} that we are building.
     */
    public EditHistDescriptorBuilder withAllergy(String allergy) {
        editHistDescriptor.setAllergy(new Allergy(allergy));
        return this;
    }
    /**
     * Sets the {@code PrevCountry} of the {@code EditHistDescriptor} that we are building.
     */
    public EditHistDescriptorBuilder withPrevCountry(String prevCountry) {
        editHistDescriptor.setPrevCountry(new PrevCountry(prevCountry));
        return this;
    }
    /**
     * Sets the {@code DischargeStatus} of the {@code EditHistDescriptor} that we are building.
     */
    public EditHistDescriptorBuilder withDischargeStatus(String dischargeStatus) {
        editHistDescriptor.setDischargeStatus(new DischargeStatus(dischargeStatus));
        return this;
    }

    public EditHistDescriptor build() {
        return editHistDescriptor;
    }
}

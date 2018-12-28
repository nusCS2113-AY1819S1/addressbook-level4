package seedu.address.testutil;

import seedu.address.logic.commands.EditMedicalReportCommand;
import seedu.address.logic.commands.EditMedicalReportCommand.EditReportDescriptor;
import seedu.address.model.medicalreport.Information;
import seedu.address.model.medicalreport.MedicalReport;
import seedu.address.model.medicalreport.ReportDate;
import seedu.address.model.medicalreport.Title;

//@@author chewkahmeng
/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditReportDescriptorBuilder {

    private EditMedicalReportCommand.EditReportDescriptor descriptor;

    public EditReportDescriptorBuilder() {
        descriptor = new EditMedicalReportCommand.EditReportDescriptor();
    }

    public EditReportDescriptorBuilder(EditReportDescriptor descriptor) {
        this.descriptor = new EditReportDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditReportDescriptor} with fields containing {@code report}'s details
     */
    public EditReportDescriptorBuilder(MedicalReport report) {
        descriptor = new EditReportDescriptor();
        descriptor.setTitle(report.getTitle());
        descriptor.setReportDate(report.getDate());
        descriptor.setInformation(report.getInformation());
    }

    /**
     * Sets the {@code Title} of the {@code EditReportDescriptor} that we are building.
     */
    public EditReportDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditReportDescriptor} that we are building.
     */
    public EditReportDescriptorBuilder withDate(String date) {
        descriptor.setReportDate(new ReportDate(date));
        return this;
    }

    /**
     * Sets the {@code Information} of the {@code EditReportDescriptor} that we are building.
     */
    public EditReportDescriptorBuilder withInfo(String info) {
        descriptor.setInformation(new Information(info));
        return this;
    }

    public EditReportDescriptor build() {
        return descriptor;
    }
}

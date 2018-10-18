package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import seedu.address.logic.commands.AddMedicalReportCommand;
import seedu.address.model.medicalreport.MedicalReport;

/**
 * A utility class for MedicalReport.
 */
public class ReportUtil {
    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddMedicalReportCommand(MedicalReport report) {
        return AddMedicalReportCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + getMedicalReportDetails(report);
    }

    /**
     * Returns the part of command string for the given {@code appt}'s details.
     */
    public static String getMedicalReportDetails(MedicalReport report) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + report.getTitle().fullTitle + " ");
        sb.append(PREFIX_DATE + report.getDate().fullDate + " ");
        sb.append(PREFIX_INFORMATION + report.getInformation().fullInformation + " ");
        return sb.toString();
    }
}

package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE3;

import seedu.address.model.medicalreport.MedicalReport;
/**
 * A utility class containing a list of {@code MedicalReport} objects to be used in tests.
 */
public class TypicalReports {
    public static final MedicalReport REPORT_EXAMPLE1 = new ReportBuilder().withTitle(VALID_TITLE1)
            .withDate(VALID_DATE1).withInformation(VALID_INFO1).build();
    public static final MedicalReport REPORT_EXAMPLE2 = new ReportBuilder().withTitle(VALID_TITLE2)
            .withDate(VALID_DATE2).withInformation(VALID_INFO2).build();
    public static final MedicalReport REPORT_EXAMPLE3 = new ReportBuilder().withTitle(VALID_TITLE3)
            .withDate(VALID_DATE3).withInformation(VALID_INFO3).build();
}

package systemtests;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.CHICKENRICE;
import static seedu.planner.testutil.TypicalRecords.INDO;
import static seedu.planner.testutil.TypicalRecords.MALA;
import static seedu.planner.testutil.TypicalRecords.WORK;
import static seedu.planner.testutil.TypicalRecords.ZT;

import org.junit.Test;

import seedu.planner.logic.commands.SummaryByDateCommand;
import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.model.Model;

public class SummaryCommandSystemTest extends FinancialPlannerSystemTest {

    private static final String INDO_DATE = INDO.getDate().value;
    private static final String CAIFAN_DATE = CAIFAN.getDate().value;
    private static final String WORK_DATE = WORK.getDate().value;
    private static final String ZT_DATE = ZT.getDate().value;
    private static final String MALA_DATE = MALA.getDate().value;
    private static final String CHICKEN_RICE_DATE = CHICKENRICE.getDate().value;

    private static final String START_DATE = "25-3-2018";
    private static final String END_DATE = "25-5-2018";

    @Test
    public void summary(){
        Model model = getModel();

        /* ------------------------ Check starting state of program ------------------------------------------------- */

        /* Case: Perform an initial summary operation -> Success */
        String command = "   " + SummaryCommand.COMMAND_WORD + "  " + SummaryByDateCommand.COMMAND_MODE_WORD
                + "  " + PREFIX_DATE + " " + START_DATE + " " + END_DATE;
    }

    /**
     * Executes the {@code SummaryCommand} that queries the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code SummaryCommand}.<br>
     * 4. {@code Storage} and {@code RecordListPanel} remains unchanged <br>
     * 5. Selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * 7. SummaryDisplay has been created and shows the correct table
     * Verifications 1, 3 and 4 are performed by
     * {@code FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see FinancialPlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
}

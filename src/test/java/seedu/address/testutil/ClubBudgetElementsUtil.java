package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_TURNOUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_EVENTS;

import seedu.address.logic.commands.BudgetCommand;
import seedu.address.model.budgetelements.ClubBudgetElements;


/**
 * A utility class for Person.
 */
public class ClubBudgetElementsUtil {

    /**
     * Returns a budget command string for adding the {@code club}.
     */
    public static String getBudgetCommand(ClubBudgetElements club) {
        return BudgetCommand.COMMAND_WORD + " " + getClubBudgetElementsDetails(club);
    }

    /**
     * Returns the part of command string for the given {@code club}'s details.
     */
    public static String getClubBudgetElementsDetails(ClubBudgetElements club) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CLUB_NAME + club.getClubName().toString() + " ");
        sb.append(PREFIX_EXPECTED_TURNOUT + club.getExpectedTurnout().value + " ");
        sb.append(PREFIX_NUMBER_OF_EVENTS + club.getNumberOfEvents().value + " ");
        return sb.toString();
    }
}

package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.logic.commands.MemberCommand.AddMemberCommand;
import seedu.address.model.ledger.Ledger;

/**
 * A utility class for Person.
 */
public class LedgerUtil {

    /**
     * Returns an add command string for adding the {@code member}.
     */
    public static String getAddLedgerCommand(Ledger ledger) {
        return AddMemberCommand.COMMAND_WORD + " " + getLedgerDetails(ledger);
    }

    /**
     * Returns the part of command string for the given {@code member}'s details.
     */
    public static String getLedgerDetails(Ledger ledger) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DATE + ledger.getDateLedger().getDate() + " ");
        return sb.toString();
    }
}

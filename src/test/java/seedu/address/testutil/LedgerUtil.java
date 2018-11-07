package seedu.address.testutil;

import seedu.address.logic.commands.MemberCommand.AddMemberCommand;
import seedu.address.logic.commands.MemberCommand.EditMemberCommand.EditPersonDescriptor;
import seedu.address.model.ledger.Ledger;
import seedu.address.model.member.Person;
import seedu.address.model.tag.Tag;

import java.util.Set;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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

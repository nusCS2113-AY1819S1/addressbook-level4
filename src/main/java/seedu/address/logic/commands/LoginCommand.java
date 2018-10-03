package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class LoginCommand extends Command {

    System.out.print(": ");
    String orgName = (new BufferedReader(new InputStreamReader(System.in))).readLine();

    System.out.print("Login name: ");
    String moduleName = (new BufferedReader(new InputStreamReader(System.in))).readLine();

    System.out.print("Login : ");
    String locale = (new BufferedReader(new InputStreamReader(System.in))).readLine();

    //Login login = new Login(moduleName, orgName, locale);
    //AuthContext lc = login.getAuthContext();
    //if (login.login(lc))
    //{
    //  login.logout(lc);
    //}
    //System.exit(0);
}

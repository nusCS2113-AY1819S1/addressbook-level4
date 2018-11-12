package seedu.address.session;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.password.Password;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;

//@@author jylee-git
/**
 * Public API for Session, which manages the user login into the application.
 */
public interface Session {
    //================================ LOGIN/LOGOUT =================================================================
    boolean isLoggedIn();

    /**
     * Attempts to log into the session using a user input NRIC and Password.
     * @throws CommandException if the login parameters are incorrect.
     */
    void loginToSession(Nric loginWithThisNric, Password loginWithThisPassword) throws CommandException;

    void logOutSession();


    //================================================ GETTING LOGGED IN DETAILS ====================================
    /**
     * Returns the {@code Nric} of the logged in person.
     * @throws CommandException if the app's not logged in.
     */
    Nric getLoggedInSessionNric() throws CommandException;

    /**
     * Returns the {@code Person} object whose NRIC matches the one that's currently logged in.
     */
    Person getLoggedInPersonDetails() throws CommandException;

    /**
     * Returns the {@code Priority} object whose NRIC matches the one that's currently logged in.
     */
    PriorityLevel getLoggedInPriorityLevel() throws CommandException;

    //================================== PRIORITY LEVEL CONCERNS ==================================================
    /**
     * Returns true if current session has at least the required priority level for the operation.
     * @throws CommandException if user's not logged in.
     */
    boolean hasSufficientPriorityLevelForThisSession(PriorityLevelEnum minimumPriorityLevel) throws CommandException;
    /**
     * Returns true if the logged in Priority Level matches any of the stated plvlEnums in the input parameter(s).
     */
    boolean containsAnyOfThesePriorityLevels(PriorityLevelEnum... plvlEnums);

    //================================= UPDATE/DELETE KEY IN HASHMAP ==============================================
    /**
     * Update a single key with the new values
     */
    void updatePersonsHashMap(Person toAmend);
    /**
     * Adds a new key into the hash map
     */
    void addIntoPersonsHashMap(Person toAdd);
    /**
     * Clears the hashmap and re-synchronizes the list of persons into the hashmap.
     * O(N) time complexity
     */
    void resyncPersonsHashMap(Model model);

    //================================= SESSION MODIFIED EVENT CALLER ============================================
    /**
     * Raises a new sessionChangedEvent, for the purpose of updating the logged in details in the status footer.
     */
    void raiseSessionModifiedEvent() throws CommandException;
}

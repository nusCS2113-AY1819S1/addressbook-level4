package seedu.address.session;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.SessionChangedEvent;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.password.Password;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;

//@@author jylee-git
/**
 * Stores the {@Code Nric} and {@code PriorityLevel} of the user who's logged in to the application.
 * Also manages the logging in and out of the current session.
 * This class is singleton class.
 */
public class SessionManager extends ComponentManager implements Session {
    public static final String NOT_LOGGED_IN = "This operation requires the user to be logged in!";
    private static final Logger logger = LogsCenter.getLogger(SessionManager.class);
    private static SessionManager singleInstance = null;

    private static Nric loggedInNric = null;
    private static PriorityLevel loggedInPriorityLevel = null;

    private HashMap<Nric, Person> allPersonsHashMap;

    // FOR TEST USE ONLY
    protected SessionManager() {
        logger.warning("Empty SessionManager constructor called. If this isn't a test, there is a bug in the"
                + "source code.");
    }

    private SessionManager(Model model) {
        allPersonsHashMap = new HashMap<>();
        List<Person> allPersonsList = model.getAddressBook().getPersonList();
        for (Person currPerson : allPersonsList) {
            allPersonsHashMap.put(currPerson.getNric(), currPerson);
        }
    }

    /**
     * Returns the one and only initialized instance of the Object
     */
    public static SessionManager getInstance(Model model) {
        if (singleInstance == null) {
            singleInstance = new SessionManager(model);
        }
        return singleInstance;
    }

    //================================ LOGIN/LOGOUT =================================================================

    /**
     * Returns true if user is logged in to the application.
     */
    @Override
    public boolean isLoggedIn() {
        return loggedInNric != null;
    }

    /**
     * Attempts to log into the session using a user input NRIC and Password.
     * @throws CommandException if the login parameters are incorrect.
     */
    @Override
    public void loginToSession(Nric loginWithThisNric, Password loginWithThisPassword) throws CommandException {
        if (isLoggedIn()) {
            throw new CommandException(LoginCommand.ALREADY_LOGGED_IN);
        }
        if (!isLoginCredentialsValid(loginWithThisNric, loginWithThisPassword)) {
            logger.info("Invalid userID and/or password");
            throw new CommandException(LoginCommand.INVALID_LOGIN_CREDENTIALS);
        } else {
            loggedInNric = loginWithThisNric;
            loggedInPriorityLevel = allPersonsHashMap.get(loginWithThisNric).getPriorityLevel();
            logger.info("Logged in as " + loggedInNric + " with priority level of "
                    + loggedInPriorityLevel.toString());
            raise(new SessionChangedEvent(getLoggedInPersonDetails()));
        }
    }

    private boolean isLoginCredentialsValid(Nric loginNric, Password loginPassword) {
        //Returns false if wrong NRIC and/or Password. Else return true.
        return (allPersonsHashMap.containsKey(loginNric)
                && allPersonsHashMap.get(loginNric).getPassword().equals(loginPassword));
    }

    /**
     * Logs out of the current session.
     */
    @Override
    public void logOutSession() {
        loggedInNric = null;
        loggedInPriorityLevel = null;
        raise(new SessionChangedEvent());
    }

    //================================================ GETTING LOGGED IN DETAILS ====================================

    /**
     * Returns the {@code Nric} of the logged in person.
     * @throws CommandException if the app's not logged in.
     */
    @Override
    public Nric getLoggedInSessionNric() throws CommandException {
        if (!isLoggedIn()) {
            throw new CommandException(NOT_LOGGED_IN);
        }
        return loggedInNric;
    }

    /**
     * Returns the {@code Person} object whose NRIC matches the one that's currently logged in.
     */
    @Override
    public Person getLoggedInPersonDetails() throws CommandException {
        if (!isLoggedIn()) {
            throw new CommandException(NOT_LOGGED_IN);
        }
        return allPersonsHashMap.get(loggedInNric);
    }

    /**
     * Returns the {@code Priority} object whose NRIC matches the one that's currently logged in.
     */
    @Override
    public PriorityLevel getLoggedInPriorityLevel() throws CommandException {
        if (!isLoggedIn()) {
            throw new CommandException(NOT_LOGGED_IN);
        }
        return loggedInPriorityLevel;
    }

    //================================== PRIORITY LEVEL CONCERNS ==================================================
    /**
     * Returns true if current session has at least the required priority level for the operation.
     * @throws CommandException if user's not logged in.
     */
    @Override
    public boolean hasSufficientPriorityLevelForThisSession(
            PriorityLevelEnum minimumPriorityLevel) throws CommandException {
        if (!isLoggedIn()) {
            throw new CommandException(NOT_LOGGED_IN);
        }
        return PriorityLevel.isPriorityLevelAtLeastOf(loggedInPriorityLevel, minimumPriorityLevel);
    }

    /**
     * Returns true if the logged in Priority Level matches any of the stated plvlEnums in the input parameter(s).
     */
    @Override
    public boolean containsAnyOfThesePriorityLevels(PriorityLevelEnum... plvlEnums) {
        for (PriorityLevelEnum plvlEnum : plvlEnums) {
            if (PriorityLevel.isPriorityLevelEqualTo(loggedInPriorityLevel, plvlEnum)) {
                return true;
            }
        }
        return false;
    }

    //================================= UPDATE/DELETE KEY IN HASHMAP ==============================================

    /**
     * Update a single key with the new values
     */
    @Override
    public void updatePersonsHashMap(Person toAmend) {
        requireNonNull(toAmend);
        allPersonsHashMap.replace(toAmend.getNric(), toAmend);
    }

    /**
     * Adds a new key into the hash map
     */
    public void addIntoPersonsHashMap(Person toAdd) {
        requireNonNull(toAdd);
        if (allPersonsHashMap.containsKey(toAdd.getNric())) {
            updatePersonsHashMap(toAdd);
        }
    }

    /**
     * Clears the hashmap and re-synchronizes the list of persons into the hashmap.
     * O(N) time complexity
     */
    public void resyncPersonsHashMap(Model model) {
        logger.fine("Synchronizing allPersonsHashMap");
        allPersonsHashMap.clear();
        List<Person> allPersonsList = model.getAddressBook().getPersonList();
        for (Person currPerson : allPersonsList) {
            allPersonsHashMap.put(currPerson.getNric(), currPerson);
        }
    }

    //================================= SESSION MODIFIED EVENT CALLER ============================================

    /**
     * Raises a new sessionChangedEvent, for the purpose of updating the logged in details in the status footer.
     */
    public void raiseSessionModifiedEvent() throws CommandException {
        if (isLoggedIn()) {
            raise(new SessionChangedEvent(getLoggedInPersonDetails()));
        } else {
            raise(new SessionChangedEvent());
        }
    }


    //================================= FOR TEST USE ONLY =========================================================
    /**
     * For test use only. Logs in with a defined priority level, which may be necessary for operations
     * requiring admin rights.
     */

    protected static void forceLoginWith(String nric, int priorityLevel) {
        loggedInNric = new Nric(nric);
        loggedInPriorityLevel = new PriorityLevel(priorityLevel);
    }
    protected static void forceLogout() {
        loggedInNric = null;
        loggedInPriorityLevel = null;
    }

    /**
     * Forcefully destroys the instance. This should only be used in testing.
     */
    public void destroy() {
        singleInstance = null;
    }
}

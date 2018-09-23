package seedu.address.logic.autocomplete;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.trie.Trie;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Completes the command for the user by predicting the possible substrings
 */
public class CommandCompleter {

    /** Constants for Trie matching */
    public static final String COMPLETE_ADDRESS = "address";
    public static final String COMPLETE_COMMAND = "command";
    public static final String COMPLETE_EMAIL = "email";
    public static final String COMPLETE_NAME = "name";
    public static final String COMPLETE_PHONE = "phone";

    /** Model instance to access data */
    private Model model;

    private AutoCompleteParser parser;

    /**
     * Trie instances for various commands and arguments.
     */
    private Trie commandTrie;
    private Trie nameTrie;
    private Trie phoneTrie;
    private Trie emailTrie;
    private Trie addressTrie;

    /**
     * Word lists of strings used to instantiate Trie objects.
     */
    private ArrayList<String> commandList;
    private ArrayList<String> nameList;
    private ArrayList<String> phoneList;
    private ArrayList<String> emailList;
    private ArrayList<String> addressList;

    /**
     * Creates a command completer with the {@code model} data.
     * @param model the data represented in the address book.
     */
    public CommandCompleter(Model model) {
        this.model = model;
        this.parser = new AutoCompleteParser();
        this.commandList = new ArrayList<>();
        this.nameList = new ArrayList<>();
        this.phoneList = new ArrayList<>();
        this.emailList = new ArrayList<>();
        this.addressList = new ArrayList<>();
        initLists();
        initTries();
    }

    /**
     * Initialises all words lists.
     */
    private void initLists() {
        initCommandsList();
        initAttributesLists();
    }

    /**
     * Initialises command words list with keywords in {@code CliSyntax}.
     */
    private void initCommandsList() {
        commandList.add(CliSyntax.COMMAND_ADD);
        commandList.add(CliSyntax.COMMAND_CLEAR);
        commandList.add(CliSyntax.COMMAND_DELETE);
        commandList.add(CliSyntax.COMMAND_EDIT);
        commandList.add(CliSyntax.COMMAND_EXIT);
        commandList.add(CliSyntax.COMMAND_FIND);
        commandList.add(CliSyntax.COMMAND_HELP);
        commandList.add(CliSyntax.COMMAND_HISTORY);
        commandList.add(CliSyntax.COMMAND_LIST);
        commandList.add(CliSyntax.COMMAND_REDO);
        commandList.add(CliSyntax.COMMAND_SELECT);
        commandList.add(CliSyntax.COMMAND_UNDO);
    }

    /**
     * Initialises attributes words lists with attribute value in each {@code Person}.
     */
    private void initAttributesLists() {
        ObservableList<Person> list = model.getAddressBook().getPersonList();
        for (Person item : list) {
            nameList.add(item.getName().fullName);
            phoneList.add(item.getPhone().value);
            emailList.add(item.getEmail().value);
            addressList.add(item.getAddress().value);
        }
    }

    /**
     * Initialises all Trie instances using the words lists.
     */
    private void initTries() {
        commandTrie = new Trie(commandList);
        nameTrie = new Trie(nameList);
        phoneTrie = new Trie(phoneList);
        emailTrie = new Trie(emailList);
        addressTrie = new Trie(addressList);
    }

    /**
     * TODO: Mimic bash behaviour of auto completing some text first
     * Predict the next possible list of text
     * @param textInput the string to be parsed
     * @return predicted list of text
     */
    public ArrayList<String> predictText(String textInput) {
        String out;
        try {
            out = parser.parseCommand(textInput);
            System.out.println("out = " + out);
        } catch (ParseException e) {
            System.out.print("Wrong command format");
        }
        return commandTrie.getPredictList(textInput);
    }
}

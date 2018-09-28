//@@author lekoook
package seedu.address.model.autocomplete;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.trie.Trie;

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
    public static final String COMPLETE_INVALID = "invalid";

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
        commandList.add(CliSyntax.COMMAND_PASSWORD);
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
     * Predict the next possible list of text
     * @param textInput the string to be parsed
     * @return predicted list of text
     */
    public ArrayList<String> predictText(String textInput) {
        AutoCompleteParserPair pair = parser.parseCommand(textInput);
        switch (pair.parseType) {
        case COMPLETE_COMMAND:
            return commandTrie.getPredictList(pair.parseValue);
        case COMPLETE_NAME:
            return nameTrie.getPredictList(pair.parseValue);
        default:
            return new ArrayList<>();
        }
    }

    /**
     * Adds a Person's attributes to the respective Trie instances for auto complete
     * @param person the person to add
     */
    public void addPersonToTrie(Person person) {
        nameTrie.insert(person.getName().fullName);
        phoneTrie.insert(person.getPhone().value);
        emailTrie.insert(person.getEmail().value);
        addressTrie.insert(person.getAddress().value);
    }

    /**
     * Deletes a Person's attributes from the respective Trie instances for auto complete
     * @param person the person to delete
     */
    public void deletePersonFromTrie(Person person) {
        nameTrie.remove(person.getName().fullName);
        phoneTrie.remove(person.getPhone().value);
        emailTrie.remove(person.getEmail().value);
        addressTrie.remove(person.getAddress().value);
    }

    /**
     * Removes all entries in all Trie instances
     */
    public void clearAllTries() {
        nameTrie.clear();
        phoneTrie.clear();
        emailTrie.clear();
        addressTrie.clear();
    }

    /**
     * Edits a Person's attributes in each respective Trie instances for auto complete.
     * Compares the differences of attributes and only update the Trie instances for attributes that were changed.
     * @param personToEdit the original person.
     * @param editedPerson the new person.
     */
    public void editPersonInTrie(Person personToEdit, Person editedPerson) {
        if (!personToEdit.getName().equals(editedPerson.getName())) {
            nameTrie.remove(personToEdit.getName().fullName);
            nameTrie.insert(editedPerson.getName().fullName);
        }
        if (!personToEdit.getEmail().equals(editedPerson.getEmail())) {
            emailTrie.remove(personToEdit.getEmail().value);
            emailTrie.insert(editedPerson.getEmail().value);
        }
        if (!personToEdit.getPhone().equals(editedPerson.getPhone())) {
            phoneTrie.remove(personToEdit.getPhone().value);
            phoneTrie.insert(editedPerson.getPhone().value);
        }
        if (!personToEdit.getAddress().equals(editedPerson.getAddress())) {
            addressTrie.remove(personToEdit.getAddress().value);
            addressTrie.insert(editedPerson.getAddress().value);
        }
    }
}

package seedu.address.logic.autocomplete;

import java.util.ArrayList;

import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.trie.Trie;

public class CommandCompleter {

    /**
     * Trie instances for various commanda and arguments
     */
    private Trie commandTrie;
    private Trie nameTrie;
    private Trie phoneTrie;
    private Trie emailTrie;
    private Trie addressTrie;

    /**
     * Lists of strings used to instantiate Trie objects
     */
    private ArrayList<String> commandList;
    private ArrayList<String> nameList;
    private ArrayList<String> phoneList;
    private ArrayList<String> emailList;
    private ArrayList<String> addressList;

    public CommandCompleter() {
        initLists();
        initTries();
    }

    private void initLists() {
        // TODO: A command to retrieve all list from Person
        initCommands();
    }

    private void initCommands() {
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
     * @param prefix
     * @return predicted list of text
     */
    public ArrayList<String> predictText(String prefix) {
        return commandTrie.getPredictList(prefix);
    }
}
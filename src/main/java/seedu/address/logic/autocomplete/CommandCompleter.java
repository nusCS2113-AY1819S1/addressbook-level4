package seedu.address.logic.autocomplete;

import java.util.ArrayList;

import seedu.address.logic.trie.Trie;

public class CommandCompleter {

    /**
     * Trie instances for various commanda and arguments
     */
    Trie commandTrie;
    Trie nameTrie;
    Trie phoneTrie;
    Trie emailTrie;
    Trie addressTrie;

    /**
     * Lists of strings used to instantiate Trie objects
     */
    ArrayList<String> commandList;
    ArrayList<String> nameList;
    ArrayList<String> phoneList;
    ArrayList<String> emailList;
    ArrayList<String> addressList;

    public CommandCompleter() {
        initLists();
        initTries();
    }

    private void initLists() {
        // TODO: A command to retrieve all list from Person
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
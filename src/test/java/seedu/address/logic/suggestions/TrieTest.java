package seedu.address.logic.suggestions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportAllCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.commands.UndoCommand;

public class TrieTest {
    private Trie dictionary;

    @Before
    public void setUp() {
        dictionary = new Trie();
    }

    @Test
    public void insert() {
        dictionary.insert(AddCommand.COMMAND_WORD);
    }

    @Test
    public void searchChar() {
        dictionary.insert(AddCommand.COMMAND_WORD);

        // check if 'add' exists
        assertTrue(dictionary.search('a'));
        assertTrue(dictionary.search('d'));
        assertTrue(dictionary.search('d'));

        // check if 's' exists after 'add'
        assertFalse(dictionary.search('s'));
    }

    @Test
    public void searchString() {
        dictionary.insert(AddCommand.COMMAND_WORD);

        // check if 'add' exists
        assertTrue(dictionary.search("add"));
    }

    @Test
    public void moveUpCrawler() {
        dictionary.insert(AddCommand.COMMAND_WORD);

        //check if first character 'a' is valid
        assertTrue(dictionary.search('a'));
        dictionary.moveSearchCrawlerToParent();

        //check if first character 'a' is still valid after using moveSearchCrawlerToParent
        assertTrue(dictionary.search('a'));
        dictionary.moveSearchCrawlerToParent();
        dictionary.moveSearchCrawlerToParent();

        //check if moving back to parent once after it is already at the root causes issues
        assertTrue(dictionary.search('a'));
    }

    @Test
    public void searchStringMultipleValuesInTrie() {
        dictionary.insert(AddCommand.COMMAND_WORD);
        dictionary.insert(HistoryCommand.COMMAND_WORD);
        dictionary.insert(HelpCommand.COMMAND_WORD);

        assertTrue(dictionary.search("add"));
        assertTrue(dictionary.search("history"));
        assertTrue(dictionary.search("help"));
    }

    @Test
    public void searchCharMultipleValuesInTrie() {
        dictionary.insert(AddCommand.COMMAND_WORD);
        dictionary.insert(HistoryCommand.COMMAND_WORD);
        dictionary.insert(HelpCommand.COMMAND_WORD);

        assertTrue(dictionary.search('a'));
        assertTrue(dictionary.search('d'));
        assertFalse(dictionary.search('h'));
        dictionary.moveSearchCrawlerToParent();
        dictionary.moveSearchCrawlerToParent();

        assertTrue(dictionary.search('h'));
        assertTrue(dictionary.search('i'));
        assertFalse(dictionary.search('e')); //'hie' does not exist
        assertTrue(dictionary.search('s')); //'his' exists
        dictionary.moveSearchCrawlerToParent(); //'hi'
        dictionary.moveSearchCrawlerToParent(); //'h'
        assertTrue(dictionary.search('e')); //'he' exists

    }

    @Test
    public void getListOfWords() {
        dictionary.insert(AddCommand.COMMAND_WORD);
        dictionary.insert(ClearCommand.COMMAND_WORD);
        dictionary.insert(DeleteCommand.COMMAND_WORD);
        dictionary.insert(EditCommand.COMMAND_WORD);
        dictionary.insert(ExitCommand.COMMAND_WORD);
        dictionary.insert(ExportAllCommand.COMMAND_WORD);
        dictionary.insert(FindCommand.COMMAND_WORD);
        dictionary.insert(HelpCommand.COMMAND_WORD);
        dictionary.insert(HistoryCommand.COMMAND_WORD);
        dictionary.insert(ListCommand.COMMAND_WORD);
        dictionary.insert(RedoCommand.COMMAND_WORD);
        dictionary.insert(ScheduleCommand.COMMAND_WORD);
        dictionary.insert(SelectCommand.COMMAND_WORD);
        dictionary.insert(TodoCommand.COMMAND_WORD);
        dictionary.insert(UndoCommand.COMMAND_WORD);

        List<String> eWordList = new ArrayList<>();
        eWordList.add(EditCommand.COMMAND_WORD);
        eWordList.add(ExitCommand.COMMAND_WORD);
        eWordList.add(ExportAllCommand.COMMAND_WORD);

        assertTrue(eWordList.containsAll(dictionary.getListOfWords("e")));
        assertFalse(eWordList.containsAll(dictionary.getListOfWords("a")));

        List<String> exWordList = new ArrayList<>();
        exWordList.add(ExitCommand.COMMAND_WORD);
        exWordList.add(ExportAllCommand.COMMAND_WORD);
        assertTrue(exWordList.containsAll(dictionary.getListOfWords("ex")));
        assertFalse(exWordList.containsAll(dictionary.getListOfWords("e")));

    }

}

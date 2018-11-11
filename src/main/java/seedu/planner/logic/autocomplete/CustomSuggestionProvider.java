package seedu.planner.logic.autocomplete;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.planner.model.autocomplete.DefaultTags.getSampleTagsForSuggestion;
import static seedu.planner.ui.SuggestionClass.newCreate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import seedu.planner.logic.commands.AddCommand;
import seedu.planner.logic.commands.AddLimitCommand;
import seedu.planner.logic.commands.AddMonthlyLimitCommand;
import seedu.planner.logic.commands.ArchiveCommand;
import seedu.planner.logic.commands.CheckLimitCommand;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.DeleteByDateCommand;
import seedu.planner.logic.commands.DeleteCommand;
import seedu.planner.logic.commands.DeleteLimitCommand;
import seedu.planner.logic.commands.DeleteMonthlyLimitCommand;
import seedu.planner.logic.commands.EditCommand;
import seedu.planner.logic.commands.EditLimitCommand;
import seedu.planner.logic.commands.EditMonthlyLimitCommand;
import seedu.planner.logic.commands.ExitCommand;
import seedu.planner.logic.commands.ExportExcelCommand;
import seedu.planner.logic.commands.FindCommand;
import seedu.planner.logic.commands.FindTagCommand;
import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.HistoryCommand;
import seedu.planner.logic.commands.ImportExcelCommand;
import seedu.planner.logic.commands.ListCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.SelectCommand;
import seedu.planner.logic.commands.SortCommand;
import seedu.planner.logic.commands.StatisticCommand;
import seedu.planner.logic.commands.SummaryByCategoryCommand;
import seedu.planner.logic.commands.SummaryByDateCommand;
import seedu.planner.logic.commands.SummaryByMonthCommand;
import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.model.autocomplete.RecordMap;
import seedu.planner.model.record.Date;

//@author tztzt

/**
 * This object is responsible for the logic behind the {@code AutoCompleteBox} that decides the range of possible
 * suggested texts that are to appear whenever a user enters a new word.
 */
public class CustomSuggestionProvider {

    /** Pattern that checks if a String begins with n/ d/ t/ m/ **/
    private static final String PREFIX_PATTERN = "^([ndtm]/).*$";

    private static final Set<String> commandKeywordsSet =
            new HashSet<>(Arrays.asList(
                    AddCommand.COMMAND_WORD, AddLimitCommand.COMMAND_WORD,
                    AddMonthlyLimitCommand.COMMAND_WORD, ArchiveCommand.COMMAND_WORD,
                    CheckLimitCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
                    DeleteByDateCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
                    DeleteLimitCommand.COMMAND_WORD, DeleteMonthlyLimitCommand.COMMAND_WORD,
                    EditCommand.COMMAND_WORD, EditLimitCommand.COMMAND_WORD,
                    EditMonthlyLimitCommand.COMMAND_WORD,
                    ExitCommand.COMMAND_WORD, ExportExcelCommand.COMMAND_WORD,
                    FindCommand.COMMAND_WORD, FindTagCommand.COMMAND_WORD,
                    HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD,
                    ImportExcelCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
                    RedoCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD,
                    SortCommand.COMMAND_WORD, StatisticCommand.COMMAND_WORD,
                    SummaryCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD));

    private static final Set<String> summaryKeywordsSet = new HashSet<>(Arrays.asList(
            SummaryByMonthCommand.COMMAND_MODE_WORD, SummaryByDateCommand.COMMAND_MODE_WORD,
            SummaryByCategoryCommand.COMMAND_MODE_WORD));
    private static final Set<String> dateInputSummarySet = Set.of(SummaryByCategoryCommand.COMMAND_MODE_WORD,
            SummaryByDateCommand.COMMAND_MODE_WORD);
    private static final Set<String> defaultMonthSummarySet = new HashSet<>(Arrays.asList(
            "jun-2018", "jul-2018", "aug-2018", "sep-2018", "oct-2018", "nov-2018", "dec-2018"));

    private static final Set<String> sortKeywordsSet = Stream.concat(SortCommand.ORDER_SET.stream(),
            SortCommand.CATEGORY_SET.stream()).collect(Collectors.toSet());

    private static final Set<String> emptySet = new HashSet<>();
    private static RecordMap recordsMap = new RecordMap();

    private static final Set<String> defaultDateSet = new HashSet<>(Arrays.asList(Date.DATE_INPUT_TODAY,
            Date.DATE_INPUT_YESTERDAY));
    private static final Set<String> defaultTagsSet = getSampleTagsForSuggestion();

    private static Set<String> nameSuggestionSet = new HashSet<>();
    private static Set<String> dateSuggestionSet = new HashSet<>();
    private static Set<String> tagsSuggestionSet = new HashSet<>();

    private static HashMap<String, Integer> limitsDateMap = new HashMap<>();
    private static Set<String> limitsDateSuggestionSet = new HashSet<>();

    private SuggestionProvider<String> suggestionProvider = newCreate(emptySet);

    public SuggestionProvider<String> getSuggestions() {
        return suggestionProvider;
    }

    /**
     * Clears the set of suggestions
     */
    private void clearSuggestions() {
        suggestionProvider.clearSuggestions();
    }

    /**
     * Clears the current set of suggestions and insert the new set of suggestions
     *
     * @param suggestions is the complete list of strings that can be suggested to the user
     */
    private void updateSuggestions(Set<String> suggestions) {
        suggestionProvider.clearSuggestions();
        suggestionProvider.addPossibleSuggestions(suggestions);
    }

    /**
     * This method parses the user Input to find out which command is being executed
     * and which word is being completed to provide the appropriate suggestions.
     * @param userInput is the entire input of text in the command box.
     * @param prefix    is the prefix of the word to be completed, if any.
     * @param wordInput is the word to be completed, without the prefix.
     */
    public void updateSuggestions(String userInput, String prefix, String wordInput) {
        String[] inputs = userInput.trim().split("\\s+");
        wordInput = prefix + wordInput;
        int strIndex = 0;
        for (; strIndex < inputs.length && !inputs[strIndex].equals(wordInput); strIndex++) ;

        if (strIndex == 0) {
            expectingCommandWord(inputs, strIndex);
        } else {
            switch (inputs[0]) {

            case AddCommand.COMMAND_WORD:
            case EditCommand.COMMAND_WORD:
                addEditCommandKeyword(inputs, wordInput);
                return;

            case AddLimitCommand.COMMAND_WORD:
            case ArchiveCommand.COMMAND_WORD:
            case ExportExcelCommand.COMMAND_WORD:
                dateParametersCommand(inputs, strIndex);
                return;

            case DeleteByDateCommand.COMMAND_WORD:
                deleteDateCommandKeyword(inputs);
                return;

            case FindCommand.COMMAND_WORD:
                updateSuggestions(nameSuggestionSet);
                return;

            case FindTagCommand.COMMAND_WORD:
                updateSuggestions(tagsSuggestionSet);
                return;

            case ListCommand.COMMAND_WORD:
            case DeleteLimitCommand.COMMAND_WORD:
            case EditLimitCommand.COMMAND_WORD:
                datesCommandKeyword(inputs, strIndex);
                return;
                
            case SortCommand.COMMAND_WORD:
                sortCommandKeyword(inputs, strIndex);
                return;

            case SummaryCommand.COMMAND_WORD:
                summaryCommandKeyword(inputs, strIndex);
                return;

            default:
                clearSuggestions();
            }
        }
    }

    /**
     * Function that handles when the word to be completed is found to be at index 0 and expects only commands.
     * @param inputs is an array of all words found in the user input delimited by whitespaces.
     * @param strIndex is the index of the word to be completed in the entire string of input.
     */
    private void expectingCommandWord(String[] inputs, int strIndex) {
        if (inputs[strIndex].matches(PREFIX_PATTERN)) {
            clearSuggestions();
        } else if (!commandKeywordsSet.contains(inputs[strIndex])) {
            updateSuggestions(commandKeywordsSet);
        } else {
            clearSuggestions();
        }
    }

    /**
     * Function that handles when the command word is found to be either add or edit and there
     * is a possibility of completion for names, dates and tags.
     * @param inputs is an array of all words found in the user input delimited by whitespaces.
     * @param wordInput is the word to be completed.
     */
    private void addEditCommandKeyword(String[] inputs, String wordInput) {
        int strIndex = 0;
        int indexWithNamePrefix = 0;
        int otherPrefixIndex = 0;
        Boolean namePrefixPresent = false;
        Boolean prefixAfterNamePrefix = false;

        for (; strIndex < inputs.length && !inputs[strIndex].equals(wordInput); strIndex++) {
            if (namePrefixPresent && !prefixAfterNamePrefix && (inputs[strIndex].matches(PREFIX_PATTERN))) {
                otherPrefixIndex = strIndex;
                prefixAfterNamePrefix = true;
            }
            if (inputs[strIndex].startsWith(PREFIX_NAME.getPrefix())) {
                indexWithNamePrefix = strIndex;
                namePrefixPresent = true;
            }
        }

        if (inputs.length > 1 && strIndex < inputs.length) {
            if (inputs[strIndex].matches(PREFIX_PATTERN)) {
                if (inputs[strIndex].startsWith(PREFIX_TAG.getPrefix())) {
                    updateSuggestions(tagsSuggestionSet);
                } else if (inputs[strIndex].startsWith(PREFIX_DATE.getPrefix())) {
                    updateSuggestions(dateSuggestionSet);
                } else if (inputs[strIndex].startsWith(PREFIX_NAME.getPrefix())) {
                    updateSuggestions(nameSuggestionSet);
                } else {
                    clearSuggestions();
                }
            } else if (namePrefixPresent) {
                if (!prefixAfterNamePrefix) {
                    updateSuggestions(nameSuggestionSet);
                } else {
                    if (strIndex > indexWithNamePrefix && strIndex < otherPrefixIndex) {
                        updateSuggestions(nameSuggestionSet);
                    } else {
                        clearSuggestions();
                    }
                }
            } else {
                clearSuggestions();
            }
        } else {
            clearSuggestions();
        }
    }

    /**
     * Function that handles when the command word is detected to be addlimit, exportexcel or archive and
     * there expected at most 3 arguments with 2 dates and 1 directory or moneyflow.
     * @param inputs is an array of all words found in the user input delimited by whitespaces.
     * @param strIndex is the index of the word to be completed in the entire string of input.
     */
    private void dateParametersCommand(String[] inputs, int strIndex) {
        Set<String> suggestions;

        if (inputs[0].equals(AddLimitCommand.COMMAND_WORD)) {
            suggestions = limitsDateSuggestionSet;
        } else {
            suggestions = dateSuggestionSet;
        }

        if (inputs.length == 2) {
            if (strIndex == 1) {
                if (inputs[strIndex].startsWith(PREFIX_DATE.getPrefix())) {
                    updateSuggestions(suggestions);
                } else {
                    clearSuggestions();
                }
            }
        } else if (strIndex < inputs.length && (inputs.length == 3 || inputs.length == 4)) {
            if (inputs[strIndex].startsWith(PREFIX_DATE.getPrefix())) {
                updateSuggestions(suggestions);
            } else if (strIndex > 1) {
                if (inputs[strIndex - 1].startsWith(PREFIX_DATE.getPrefix())) {
                    updateSuggestions(suggestions);
                } else {
                    clearSuggestions();
                }
            }
        } else {
            clearSuggestions();
        }
    }

    /**
     * Function that handles when the arguments to be completed are only date parameters, for commands
     * ListCommand, DeleteLimitCommand, EditLimitCommand
     * @param inputs is an array of all words found in the user input delimited by whitespaces.
     * @param strIndex is the index of the word to be completed in the entire string of input.
     */
    private void datesCommandKeyword(String[] inputs, int strIndex) {
        Set<String> suggestions;
        if (inputs[0].equals(ListCommand.COMMAND_WORD) || inputs[0].equals(AddLimitCommand.COMMAND_WORD)) {
            suggestions = dateSuggestionSet;
        } else {
            suggestions = limitsDateSuggestionSet;
        }

        if (inputs.length == 2) {
            if (strIndex == 1 && inputs[strIndex].startsWith(PREFIX_DATE.getPrefix())) {
                updateSuggestions(suggestions);
            } else {
                clearSuggestions();
            }
        } else if (inputs.length == 3 && (strIndex == 1 || strIndex == 2)) {
            if (strIndex == 1) {
                if (inputs[strIndex].startsWith(PREFIX_DATE.getPrefix())) {
                    updateSuggestions(suggestions);
                } else {
                    clearSuggestions();
                }
            } else {
                if (!inputs[1].startsWith(PREFIX_DATE.getPrefix())) {
                    clearSuggestions();
                } else {
                    updateSuggestions(suggestions);
                }
            }
        } else {
            clearSuggestions();
        }

    }

    /**
     * Function that handles when the command word is detected to be deletedate and expects only a single
     * date input.
     * @param inputs is an array of all words found in the user input delimited by whitespaces.
     */
    private void deleteDateCommandKeyword(String[] inputs) {
        if (inputs.length == 2) {
            updateSuggestions(dateSuggestionSet);
        } else {
            clearSuggestions();
        }
    }

    /**
     * Function that handles when the command word is detected to be sort and expects either a category
     * or an order or both.
     * @param inputs is an array of all words found in the user input delimited by whitespaces.
     * @param strIndex is the index of the word to be completed in the entire string of input.
     */
    private void sortCommandKeyword(String[] inputs, int strIndex) {

        if (inputs.length == 2) {
            if (strIndex == 1) {
                if (inputs[strIndex].matches(PREFIX_PATTERN)) {
                    clearSuggestions();
                } else if (sortKeywordsSet.contains(inputs[strIndex])) {
                    clearSuggestions();
                } else {
                    updateSuggestions(sortKeywordsSet);
                }
            } else {
                clearSuggestions();
            }
        } else if (inputs.length == 3 && (strIndex == 1 || strIndex == 2)) {
            if (strIndex == 1) {
                if (sortKeywordsSet.contains(inputs[strIndex])) {
                    clearSuggestions();
                } else {
                    if (SortCommand.ORDER_SET.contains(inputs[2])) {
                        updateSuggestions(SortCommand.CATEGORY_SET);
                    } else if (SortCommand.CATEGORY_SET.contains(inputs[2])) {
                        updateSuggestions(SortCommand.ORDER_SET);
                    } else {
                        updateSuggestions(sortKeywordsSet);
                    }
                }
            } else { // strIndex = 2
                if (sortKeywordsSet.contains(inputs[strIndex])) {
                    clearSuggestions();
                } else {
                    if (SortCommand.ORDER_SET.contains(inputs[1])) {
                        updateSuggestions(SortCommand.CATEGORY_SET);
                    } else if (SortCommand.CATEGORY_SET.contains(inputs[1])) {
                        updateSuggestions(SortCommand.ORDER_SET);
                    } else {
                        clearSuggestions();
                    }
                }
            }
        } else {
            clearSuggestions();
        }
    }

    /**
     * Function that handles when the command word is detected to be summary and expects a secondary keyword
     * and 2 date inputs or 2 month inputs.
     * @param inputs is an array of all words found in the user input delimited by whitespaces.
     * @param strIndex is the index of the word to be completed in the entire string of input.
     */
    private void summaryCommandKeyword(String[] inputs, int strIndex) {

        if (inputs.length == 2) {
            if (strIndex == 1) {
                if (inputs[strIndex].matches(PREFIX_PATTERN)) {
                    clearSuggestions();
                } else {
                    updateSuggestions(summaryKeywordsSet);
                }
            } else {
                clearSuggestions();
            }
        } else if (inputs.length == 3 || inputs.length == 4) {
            if (!summaryKeywordsSet.contains(inputs[1])) {
                clearSuggestions();
            } else {
                if (strIndex == 2 || strIndex == 3) {
                    if (!inputs[2].startsWith(PREFIX_DATE.getPrefix())) {
                        clearSuggestions();
                    } else {
                        if (dateInputSummarySet.contains(inputs[1])) {
                            updateSuggestions(dateSuggestionSet);
                        } else if (inputs[1].equals(SummaryByMonthCommand.COMMAND_MODE_WORD)) {
                            updateSuggestions(defaultMonthSummarySet);
                        } else {
                            clearSuggestions();
                        }
                    }
                }
            }
        } else {
            clearSuggestions();
        }
    }

    /**
     * Updates the RecordMap in this class whenever the RecordMap in the Model is updated.
     *
     * @param newRecordMap is the new TagMap object that is to replace the old TagMap
     */
    public static void updateRecordMap(RecordMap newRecordMap) {
        recordsMap = newRecordMap;
        updateRecordSets();
    }

    /**
     * Updates the RecordMap in this class whenever the RecordMap in the Model is updated.
     *
     * @param dataBasedLimitList is the new HashMap object that is to replace the old LimitDateMap
     */
    public static void updateLimitDateMap(HashMap<String, Integer> dataBasedLimitList) {
        limitsDateMap = dataBasedLimitList;
        updateLimitDateSet();
    }

    /**
     * Updates the Sets of names, dates and tags that are obtained from the Record Map
     */
    private static void updateRecordSets() {
        nameSuggestionSet = recordsMap.getAsReadOnlyNameMap().getAsReadOnlyNameMap().keySet();
        Set<String> tempDateSuggestionSet = new HashSet<>(defaultDateSet);
        tempDateSuggestionSet.addAll(recordsMap.getAsReadOnlyDateMap().getAsReadOnlyDateMap().keySet());
        dateSuggestionSet = tempDateSuggestionSet;

        Set<String> tempTagsSuggestionSet = new HashSet<>(defaultTagsSet);
        tempTagsSuggestionSet.addAll(recordsMap.getAsReadOnlyTagMap().getAsReadOnlyTagMap().keySet());
        tagsSuggestionSet = tempTagsSuggestionSet;
    }

    /**
     * Updates the Map of dates that are tied to limits
     */
    private static void updateLimitDateSet() {
        Set<String> tempLimitsDateSuggestionSet = new HashSet<>(defaultDateSet);
        tempLimitsDateSuggestionSet.addAll(limitsDateMap.keySet());
        limitsDateSuggestionSet = tempLimitsDateSuggestionSet;
    }

}

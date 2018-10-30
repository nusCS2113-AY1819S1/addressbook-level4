# gaoqikai
###### \java\seedu\address\logic\commands\AddTagCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.tag.Tag;




/**
 * Add the given tags to selected item by index.
 */

public class AddTagCommand extends Command {
    public static final String COMMAND_WORD = "addTag";

    public static final String MESSAGE_SUCCESS = "Added tags to the selected item.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add the inputted tags to the selected item "
            + "by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG [MORE_TAGS]"
            + " Example: " + COMMAND_WORD + " 1 " + PREFIX_TAG + " Lab1";

    private final AddTagDescriptor addTagDescriptor;
    private final Index index;

    public AddTagCommand(Index index, AddTagDescriptor addTagDescriptor) {
        requireNonNull(index);
        requireNonNull(addTagDescriptor);

        this.index = index;
        this.addTagDescriptor = new AddTagDescriptor(addTagDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();


        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToEdit = lastShownList.get(index.getZeroBased());
        Item editedItem = createEditedItem(itemToEdit, addTagDescriptor);

        model.updateItem(itemToEdit, editedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitStockList();
        return new CommandResult(MESSAGE_SUCCESS);

    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToEdit}
     * edited with {@code addTagDescriptor}.
     */
    private static Item createEditedItem(Item itemToEdit, AddTagCommand.AddTagDescriptor addTagDescriptor) {
        assert itemToEdit != null;

        Name updatedName = itemToEdit.getName();
        Quantity updatedQuantity = itemToEdit.getQuantity();
        Quantity updatedMinQuantity = itemToEdit.getMinQuantity();
        Set<Tag> updatedTags = addTagDescriptor.getTags();
        updatedTags.addAll(itemToEdit.getTags());
        return new Item(updatedName, updatedQuantity, updatedMinQuantity, updatedTags);
    }

    /**
     * Temporarily stores the tags to be added.
     */
    public static class AddTagDescriptor {
        private Set<Tag> tags;

        public AddTagDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public AddTagDescriptor(AddTagDescriptor toCopy) {
            setTags(toCopy.tags);
        }

        /**
         * Returns true if there is tag to add.
         */
        public boolean haveTag() {
            return CollectionUtil.isAnyNonNull(tags);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        public Set<Tag> getTags() {
            return tags;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddTagDescriptor)) {
                return false;
            }

            // state check
            AddTagDescriptor e = (AddTagDescriptor) other;

            return getTags().equals(e.getTags());
        }
    }
}
```
###### \java\seedu\address\logic\commands\TagCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.item.TagContainsKeywordsPredicate;

/**
 * Find and show items under specific tags
 *
 */
public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_SUCCESS = "Listed all items for your tag(s).";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all items whose tag matches "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]..."
            + " Example: " + COMMAND_WORD + " Lab1";

    private final TagContainsKeywordsPredicate predicate;

    public TagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredItemListByTag(predicate);
        return new CommandResult(
             String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredItemList().size()));
    }
}
```
###### \java\seedu\address\logic\parser\AddTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.AddTagCommand.AddTagDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    /**
     * Parsers the given (@code String) of arguments in the context of the AddTagCommand and
     * returns an AddTagCommand object for execution.
     * @Throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Index index;

        AddTagDescriptor addTagDescriptor = new AddTagCommand.AddTagDescriptor();

        parseTagsToAdd(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(addTagDescriptor::setTags);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE), pe);
        }

        if (!addTagDescriptor.haveTag()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        return new AddTagCommand(index, addTagDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsToAdd(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
```
###### \java\seedu\address\logic\parser\TagCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new TagCommand(new TagContainsKeywordsPredicate (Arrays.asList(tagKeywords)));
    }
}
```
###### \java\seedu\address\model\item\TagContainsKeywordsPredicate.java
``` java
package seedu.address.model.item;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Item}'s {@code Tags} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Item> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate (List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Item item) {
        return keywords.stream()
                .anyMatch(keyword -> item.getTags().toString().toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Updates the tag filter of the filtered item list to filter by the given tag: {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredItemListByTag(Predicate<Item> predicate);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    //update the stock list that is filtered by tag
    public void updateFilteredItemListByTag(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }
```

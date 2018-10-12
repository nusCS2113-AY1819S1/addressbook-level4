package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;

import java.util.HashSet;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MERGE;

/**
 * Merges the timetables of multiple people
 */

public class MergeCommand extends Command {

    public static final String COMMAND_WORD = "merge";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Merges the timetables of selected people"
            + "by the index number used in the last person listing.\n "
            + "Parameters: INDEX (must be positive integer )"
            + PREFIX_MERGE + "[INDEX]"
            + "for all timetables you want to merge.\n"
            + "Example: " + "COMMAND_WORD" + "1" + "COMMAND_WORD" + "2";

    public static final String MESSAGE_MERGE_TIMETABLE_SUCCESS = "Timetables Merged";
    public static final String MESSAGE_NOT_MERGED = "At least two people to merge must be provided";

    private final List<String> indices;

    public MergeCommand(List<String> indices) {
        requireNonNull(indices);

        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();


        Person[] personsToMerge = new Person[lastShownList.size()];


        for (String index : indices) {
            if (Integer.parseInt(index) >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        int i = 0;
        for (String it : indices) {
            int index;
            try {
                index = Integer.parseInt(it) - 1;
            } catch (NumberFormatException nfe) {
                throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MergeCommand.MESSAGE_USAGE), nfe);
            }
            personsToMerge[i] = lastShownList.get(index);
            i++;
        }
        for (int j = 0; j < i - 1; j++) {
            personsToMerge[j + 1] = mergeTimetables(personsToMerge[j], personsToMerge[j + 1]);
        }
        model.addPerson(personsToMerge[i - 1]);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_MERGE_TIMETABLE_SUCCESS);

    }


    public Person mergeTimetables(Person person1, Person person2) {
        Name mergedName = new Name(person1.getName().toString() + " and " + person2.getName().toString());
        Phone phone = new Phone("999");
        Email email = new Email("notimportant@no");
        Address address = new Address("here");
        Set<Tag> mergedTags = mergeTimeSlots(person1.getTags(), person2.getTags());


        return new Person(mergedName, phone, email, address, mergedTags);


    }

    public Set<Tag> mergeTimeSlots(Set<Tag> tags1, Set<Tag> tags2) {
        Tag[] array1 = tags1.toArray(new Tag[0]);
        Tag[] array2 = tags2.toArray(new Tag[0]);
        Set<Tag> finalTags = new HashSet<>();
        for (int i = 0; i < tags1.size(); i++) {
            if ((array1[i].toString().charAt(6) == 'm' || array1[i].toString().charAt(6) == 'a')
                    && (array2[i].toString().charAt(6) == 'm' || array2[i].toString().charAt(6) == 'a')) {
                finalTags.add(new Tag("free"));
            } else {
                finalTags.add(new Tag("busy"));
            }
        }
        return finalTags;
    }

}




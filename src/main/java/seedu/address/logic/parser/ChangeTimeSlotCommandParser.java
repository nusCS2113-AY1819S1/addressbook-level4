package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.ChangeTimeSlotCommand.MESSAGE_USAGE;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeTimeSlotCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.enrolledClass.EnrolledClass;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ChangeTimeSlotCommandParser implements Parser<ChangeTimeSlotCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeTimeSlotCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs =args.trim();
        String[] actions = trimmedArgs.split("\\s+");
        if(actions.length<4){
            throw new ParseException(MESSAGE_USAGE);
        }
        if (actions.length%3!=1){
            throw new ParseException(MESSAGE_USAGE);
        }
        Index index = Index.fromOneBased(Integer.parseInt(actions[0]));
        for(int i=1; i<actions.length;i++){
            if(i%3==1){
                if(!actions[i].equalsIgnoreCase("mon")&&actions[i].equalsIgnoreCase("tue")&&
                        actions[i].equalsIgnoreCase("wed")&&actions[i].equalsIgnoreCase("thu")&&
                        actions[i].equalsIgnoreCase("fri")){
                    throw new ParseException(MESSAGE_USAGE);
                }
            }
            if(i%3==2){
                if(!actions[i].equalsIgnoreCase("8am")&&!actions[i].equalsIgnoreCase("9am")
                        &&!actions[i].equalsIgnoreCase("10am")
                        &&!actions[i].equalsIgnoreCase("11am")
                        &&!actions[i].equalsIgnoreCase("12am")
                        &&!actions[i].equalsIgnoreCase("1pm")
                        &&!actions[i].equalsIgnoreCase("2pm")
                        &&!actions[i].equalsIgnoreCase("3pm")
                        &&!actions[i].equalsIgnoreCase("4pm")
                        &&!actions[i].equalsIgnoreCase("5pm")
                        &&!actions[i].equalsIgnoreCase("6pm")
                        &&!actions[i].equalsIgnoreCase("7pm")){
                    throw new ParseException(MESSAGE_USAGE);
                }
            }
        }

        return new ChangeTimeSlotCommand(index, actions);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses {@code Collection<String> enrolledClasses} into a {@code Map<String, EnrolledClass>} if {@code tags}
     * is non-empty.
     * If {@code enrolledClasses} contain only one element which is an empty string, it will be parsed into a
     * {@code Map<String, EnrolledClass>} containing zero enrolledClasses.
     */
    private Optional<Map<String, EnrolledClass>> parseEnrolledClassesForEdit(Collection<String> enrolledClasses)
            throws ParseException {

        assert enrolledClasses != null;

        if (enrolledClasses.isEmpty()) {
            return Optional.empty();
        }

        Collection<String> enrolledClassesMap;
        if (enrolledClasses.size() == 1 && enrolledClasses.contains("")){
            enrolledClassesMap = Collections.emptySet();
        } else {
            enrolledClassesMap = enrolledClasses;
        }

        return Optional.of(ParserUtil.parseEnrolledClasses(enrolledClassesMap));
    }

}

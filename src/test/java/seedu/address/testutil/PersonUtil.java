package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCandidateCommand;
import seedu.address.logic.commands.EditCandidateCommand.EditPersonDescriptor;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Candidate.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code candidate}.
     */
    public static String getAddCandidateCommand(Candidate candidate) {
        return AddCandidateCommand.COMMAND_WORD + " " + getPersonDetails(candidate);
    }

    /**
     * Returns the part of command string for the given {@code candidate}'s details.
     */
    public static String getPersonDetails(Candidate candidate) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + candidate.getName().fullName + " ");
        sb.append(PREFIX_GENDER + candidate.getGender().value + " ");
        sb.append(PREFIX_AGE + candidate.getAge().value + " ");
        sb.append(PREFIX_PHONE + candidate.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + candidate.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + candidate.getAddress().value + " ");
        sb.append(PREFIX_JOB + candidate.getJob().value + " ");
        sb.append(PREFIX_EDUCATION + candidate.getEducation().value + " ");
        sb.append(PREFIX_SALARY + candidate.getSalary().value + " ");
        candidate.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}

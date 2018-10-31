package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAXENROLLMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;
import seedu.address.model.classroom.Enrollment;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleManager;

/**
 * Edits the details of an existing classroom in Trajectory
 */
public class ClassEditCommand extends Command {

    public static final String COMMAND_WORD = "class edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an existing class with an updated field"
            + " for the system. "
            + "Parameters: "
            + PREFIX_CLASS_NAME + "CLASS_NAME "
            + PREFIX_MODULE_CODE + "MODULE_CODE "
            + PREFIX_MAXENROLLMENT + "ENROLLMENT_SIZE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLASS_NAME + "T16 "
            + PREFIX_MODULE_CODE + "CG1111 "
            + PREFIX_MAXENROLLMENT + "69";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    private static final String MESSAGE_EDIT_CLASSROOM_SUCCESS = "Edited Class: %1$s, %2$s";
    private static final String MESSAGE_FAIL = "Class belonging to module not found!";
    private static final String MESSAGE_MODULE_CODE_INVALID = "Module code does not exist";

    private final String className;
    private final String moduleCode;
    private final EditClassDescriptor editModuleDescriptor;

    public ClassEditCommand(String className, String moduleCode, EditClassDescriptor editClassroomDescriptor) {
        requireNonNull(className, moduleCode);
        requireNonNull(editClassroomDescriptor);

        this.className = className;
        this.moduleCode = moduleCode;
        this.editModuleDescriptor = new EditClassDescriptor(editClassroomDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ClassroomManager classroomManager = ClassroomManager.getInstance();
        ModuleManager moduleManager = ModuleManager.getInstance();

        if (!moduleManager.doesModuleExist(moduleCode)) {
            throw new CommandException(MESSAGE_MODULE_CODE_INVALID);
        }

        Classroom classtoEdit = classroomManager.findClassroom(className, moduleCode);
        if (classtoEdit == null) {
            throw new CommandException(MESSAGE_FAIL);
        }

        Classroom editedClass = createEditedClassroom(classtoEdit, editModuleDescriptor);
        classroomManager.updateClassroom(classtoEdit, editedClass);
        classroomManager.saveClassroomList();
        return new CommandResult(String.format(MESSAGE_EDIT_CLASSROOM_SUCCESS,
                classtoEdit.getClassName(), classtoEdit.getModuleCode()));
    }

    /**
     * Creates and returns a {@code Classroom} with the details of {@code classToEdit}
     * edited with {@code EditClassDescriptor}.
     */
    private static Classroom createEditedClassroom(Classroom classToEdit,
                                                   EditClassDescriptor editClassroomDescriptor) {
        assert classToEdit != null;

        ClassName className = classToEdit.getClassName();
        ModuleCode moduleCode = classToEdit.getModuleCode();
        Enrollment maxEnrollment = editClassroomDescriptor.getMaxEnrollment().orElse(classToEdit.getMaxEnrollment());

        return new Classroom(className, moduleCode, maxEnrollment);
    }

    /**
     * Stores the details to edit the classroom with. Each non-empty field value (other than the
     * classname and module code)
     * will replace the corresponding field value of the classroom.
     */
    public static class EditClassDescriptor {
        private ClassName className;
        private ModuleCode moduleCode;
        private Enrollment maxEnrollment;

        public EditClassDescriptor() {
        }

        /**
         * Copy constructor
         */
        public EditClassDescriptor(EditClassDescriptor toCopy) {
            setClassName(toCopy.className);
            setModuleCode(toCopy.moduleCode);
            setEnrollment(toCopy.maxEnrollment);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(maxEnrollment);
        }

        public ClassName getClassName() {
            return className;
        }

        public void setClassName(ClassName className) {
            this.className = className;
        }

        public ModuleCode getModuleCode() {
            return moduleCode;
        }

        public void setModuleCode(ModuleCode moduleCode) {
            this.moduleCode = moduleCode;
        }

        public Optional<Enrollment> getMaxEnrollment() {
            return Optional.ofNullable(maxEnrollment);
        }

        public void setEnrollment(Enrollment enrollment) {
            this.maxEnrollment = enrollment;
        }

    }
}

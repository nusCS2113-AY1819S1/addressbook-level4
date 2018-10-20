package seedu.address.model.module;

/**
 * Represents a module in Trajectory
 */
public class Module {
    private String name;
    private String code;

    private ModuleCode moduleCode;

    public Module(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Module(ModuleCode code) {
        this.moduleCode = code;
    }

    public String getModuleName() {
        return this.name;
    }

    public String getModuleCode() {
        return this.code;
    }

    public ModuleCode getCode() {
        return this.moduleCode;
    }

    /**
     * Returns true if both modules have the same module code and name.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
                && otherModule.getModuleCode().equals(getModuleCode())
                && otherModule.getModuleName().equals(getModuleName());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Module Code: ")
                .append(getModuleCode())
                .append(" Module Name: ")
                .append(getModuleName());
        return builder.toString();
    }

    /**
     * Returns true if both modules have the same module code and data fields.
     * This defines a stronger notion of equality between two modules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getModuleCode().equals((getModuleCode()))
                && otherModule.getModuleName().equals(getModuleName());
    }
}

package seedu.address.model.module;

/**
 * Represents a module in Trajectory
 */
public class Module {
    private static final String MODULE_CODE_VALIDATION_REGEX = "^[A-Z]{2,3}[1-9]{1}[0-9]{3}[A-Z]{0,1}$";
    private String name;
    private String code;

    public Module(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getModuleName() {
        return this.name;
    }

    public String getModuleCode() {
        return this.code;
    }

    /**
     * Returns true if both modules have the same module code and name.
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
}

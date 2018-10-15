package seedu.address.model;

/**
 * Represents a module in Trajectory
 */
public class Module {
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
}

package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.stream.Collectors;

import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedModule;

/**
 * This module manager stores modules for Trajectory.
 */
public class ModuleManager {

    private ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        readModuleList();
    }

    /**
     * Adds a new module to the in-memory array list
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Replaces the given module {@code target} with {@code editedModule}
     * {@code target} must already exist in Trajectory
     */
    public void updateModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        int targetIndex = modules.indexOf(target);

        modules.set(targetIndex, editedModule);
    }

    /**
     * Gets the module list from storage and converts it to a Module array list
     */
    private void readModuleList() {
        ArrayList<XmlAdaptedModule> xmlModuleList = StorageController.getModuleStorage();
        for (XmlAdaptedModule xmlModule : xmlModuleList) {
            modules.add(xmlModule.toModelType());
        }
    }

    /**
     * Converts the Module array list and invokes the StorageController to save the current module list to file
     */
    public void saveModuleList() {
        ArrayList<XmlAdaptedModule> xmlAdaptedModules =
                modules.stream().map(XmlAdaptedModule::new).collect(Collectors.toCollection(ArrayList::new));
        StorageController.setModuleStorage(xmlAdaptedModules);
        StorageController.storeData();
    }

    public Module getModuleByModuleCode(String moduleCode) {
        return this.modules.stream()
                .filter(module -> module.getModuleCode().equals(moduleCode))
                .findAny()
                .orElse(null);
    }

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }
}

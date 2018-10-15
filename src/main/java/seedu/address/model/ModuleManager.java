package seedu.address.model;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }
}

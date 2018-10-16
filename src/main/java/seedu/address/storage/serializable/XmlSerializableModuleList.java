package seedu.address.storage.serializable;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.module.Module;
import seedu.address.storage.adapter.XmlAdaptedModule;

/**
 * Represents a list of modules that is serializable to XML format
 */
@XmlRootElement(namespace = "seedu.address.storage.serializable")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlSerializableModuleList {

    @XmlElementWrapper(name = "modules")
    @XmlElement(name = "module")
    private ArrayList<XmlAdaptedModule> modules;

    /**
     * Initializes an empty list of modules
     */
    public XmlSerializableModuleList() {
        modules = new ArrayList<>();
    }

    /**
     * Converts an array list of Modules into this class' format
     */
    public XmlSerializableModuleList(ArrayList<Module> src) {
        this();
        modules.addAll(src.stream().map(XmlAdaptedModule::new).collect(Collectors.toList()));
    }

    public ArrayList<XmlAdaptedModule> getModules() {
        return modules;
    }

    public void setModules(ArrayList<XmlAdaptedModule> modules) {
        this.modules = modules;
    }
}

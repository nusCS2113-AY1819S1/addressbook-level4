//@@author Meowzz95
package com.t13g2.forum.model;

import java.util.List;

import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 * Provides APIs to manipulate {@link Module}
 */
public interface IModuleRepository {
    /**
     * Adds an {@link Module} into database
     *
     * @param module
     * @return object Id added
     */
    int addModule(Module module);

    /**
     * Removes an {@link Module}
     * @param module
     */
    void removeModule(Module module);

    /**
     * Removes an {@link Module}
     * @param moduleId
     */
    void removeModule(int moduleId);

    /**
     * Updates an {@link Module}
     * @param module
     */
    void updateModule(Module module);

    /**
     * Gets a module by its Id
     * @param moduleId
     * @return module queried
     * @throws EntityDoesNotExistException
     */
    Module getModule(int moduleId) throws EntityDoesNotExistException;

    /**
     * Gets a module by its module code
     * @param moduleCode
     * @return module queried
     * @throws EntityDoesNotExistException
     */
    Module getModuleByCode(String moduleCode) throws EntityDoesNotExistException;

    /**
     * Gets all modules in the database
     * @return a list of modules
     */
    List<Module> getAllModule();
}

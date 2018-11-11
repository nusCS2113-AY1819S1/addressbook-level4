//@@author Meowzz95
package com.t13g2.forum.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import com.sun.istack.NotNull;
import com.t13g2.forum.commons.util.Extensions;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;
import com.t13g2.forum.storage.forum.IForumBookStorage;

/**
 * Provides APIs to manipulate {@link Module}
 */
public class ModuleRepository extends BaseRepository implements IModuleRepository {
    /**
     * Creates a new BaseRepository instance.
     *
     * @param forumBookStorage of type IForumBookStorage
     */
    public ModuleRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }


    /**
     * Adds an {@link Module} into database
     *
     * @param module
     * @return object Id added
     */
    @Override
    public int addModule(@NotNull Module module) {
        Objects.requireNonNull(module, "module can't be null");

        forumBookStorage.getModules().getList().add(module);
        forumBookStorage.getModules().setDirty();
        return module.getId();
    }

    /**
     * Removes an {@link Module}
     * @param module
     */
    @Override
    public void removeModule(@NotNull Module module) {
        Objects.requireNonNull(module, "module can't be null");

        this.removeModule(module.getId());
    }

    /**
     * Removes an {@link Module}
     * @param moduleId
     */
    @Override
    public void removeModule(int moduleId) {
        forumBookStorage.getModules().getList().removeIf(module -> module.getId() == moduleId);
        forumBookStorage.getModules().setDirty();
    }

    /**
     * Updates an {@link Module}
     * @param module
     */
    @Override
    public void updateModule(@NotNull Module module) {
        Objects.requireNonNull(module, "module can't be null");
        Extensions.updateObjectInList(forumBookStorage.getModules().getList(), module);
        forumBookStorage.getModules().setDirty();
    }

    /**
     * Gets a module by its Id
     * @param moduleId
     * @return module queried
     * @throws EntityDoesNotExistException
     */
    @Override
    public Module getModule(int moduleId) throws EntityDoesNotExistException {
        return getModuleBy(module -> module.getId() == moduleId);
    }

    /**
     * Gets a module by its module code
     * @param moduleCode
     * @return module queried
     * @throws EntityDoesNotExistException
     */
    @Override
    public Module getModuleByCode(String moduleCode) throws EntityDoesNotExistException {
        return getModuleBy(module -> module.getModuleCode().equals(moduleCode));
    }

    /**
     * Gets all modules in the database
     * @return a list of modules
     */
    @Override
    public List<Module> getAllModule() {
        return forumBookStorage.getModules().getList();
    }

    /**
     * Helper method to get a module by a predicate
     * @param predicate
     * @return module queried
     * @throws EntityDoesNotExistException
     */
    private Module getModuleBy(Predicate<Module> predicate) throws EntityDoesNotExistException {
        Optional<Module> module = forumBookStorage.getModules().getList().stream().filter(predicate).findFirst();
        if (module.isPresent()) {
            return module.get();
        }
        throw new EntityDoesNotExistException("Module dose not exist");
    }
}

package com.t13g2.forum.storage.forum;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.t13g2.forum.model.forum.Module;

/**
 *
 */
public class ModuleRepository extends BaseRepository implements IModuleRepository {
    public ModuleRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }


    @Override
    public int addModule(Module module) {
        forumBookStorage.getModules().getList().add(module);
        forumBookStorage.getModules().setDirty();
        return module.getId();
    }

    @Override
    public void removeModule(Module module) {
        this.removeModule(module.getId());
    }

    @Override
    public void removeModule(int moduleId) {
        forumBookStorage.getModules().getList().removeIf(module -> module.getId() == moduleId);
        forumBookStorage.getModules().setDirty();
    }

    @Override
    public Module getModule(int moduleId) throws EntityDoesNotExistException {
        return getModuleBy(module -> module.getId() == moduleId);
    }

    @Override
    public Module getModuleByCode(String moduleCode) throws EntityDoesNotExistException {
        return getModuleBy(module -> module.getModuleCode().equals(moduleCode));
    }

    @Override
    public List<Module> getAllModule() {
        return forumBookStorage.getModules().getList();
    }

    private Module getModuleBy(Predicate<Module> predicate) throws EntityDoesNotExistException {
        Optional<Module> module = forumBookStorage.getModules().getList().stream().filter(predicate).findFirst();
        if (module.isPresent()) {
            return module.get();
        }
        throw new EntityDoesNotExistException("Module dose now exist");
    }
}

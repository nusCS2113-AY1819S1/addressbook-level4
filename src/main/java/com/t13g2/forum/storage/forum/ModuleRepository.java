package com.t13g2.forum.storage.forum;

import java.util.List;

import com.t13g2.forum.model.forum.Module;

/**
 *
 */
public class ModuleRepository extends BaseRepository implements IModuleRepository {
    public ModuleRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public int addModule(Module module) {
        return 0;
    }

    @Override
    public void removeModule(Module module) {

    }

    @Override
    public void removeModule(int moduleId) {

    }

    @Override
    public Module getModule(int moduleId) {
        return null;
    }

    @Override
    public Module getModuleByCode(String moduleCode) {
        return null;
    }

    @Override
    public List<Module> getAllModule() {
        return null;
    }
}

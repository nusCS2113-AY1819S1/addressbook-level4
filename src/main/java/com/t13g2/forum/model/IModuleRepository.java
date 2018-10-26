//@@Meowzz95
package com.t13g2.forum.model;

import java.util.List;

import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 *
 */
public interface IModuleRepository {
    int addModule(Module module);

    void removeModule(Module module);

    void removeModule(int moduleId);

    Module getModule(int moduleId) throws EntityDoesNotExistException;

    Module getModuleByCode(String moduleCode) throws EntityDoesNotExistException;

    List<Module> getAllModule();
}

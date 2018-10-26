//@@Meowzz95
package com.t13g2.forum.model;

/**
 *
 */
public interface IUnitOfWork {
    void commit();

    void rollBack();
}

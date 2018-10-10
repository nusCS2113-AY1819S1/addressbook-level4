package com.t13g2.forum.storage.forum;

/**
 *
 */
public interface IUnitOfWork {
    void commit();

    void rollBack();
}

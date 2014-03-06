package com.ews.enterprise.datacollection.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.google.inject.Inject;
import com.google.inject.Provider;

public final class EntityManagerProvider implements Provider<EntityManager> {

    private static final ThreadLocal<EntityManager> THREAD_CONTEXT =
        new ThreadLocal<EntityManager>();

    private final Provider<EntityManagerFactory> emfProvider;

    @Inject
    public EntityManagerProvider(final Provider<EntityManagerFactory> emfProvider) {
        this.emfProvider = emfProvider;
    }

    // TODO This probably isn't right, needs to be in thread context
    private List<Boolean> areNestedTransactions = new LinkedList<Boolean>();

    public EntityManager get() {
        return get(true);
    }

    public EntityManager get(final boolean create) {
        EntityManager em = THREAD_CONTEXT.get();
        if ((em == null || !em.isOpen()) && create) {
            EntityManagerFactory emf = emfProvider.get();
            em = emf.createEntityManager();
            THREAD_CONTEXT.set(em);
        }
        return em;
    }

    public void startTransaction() {
        EntityTransaction eTx = get().getTransaction();

        if (eTx.isActive()) {
            areNestedTransactions.add(true);
            return;
        } else {
            areNestedTransactions = new ArrayList<Boolean>();
            eTx.begin();
        }
    }

    public void endTransaction() {
        EntityTransaction eTx = get().getTransaction();
        if (!areNestedTransactions.isEmpty()) {
            areNestedTransactions.remove(true);
            return;
        } else {
            if (eTx.isActive()) {
                eTx.commit();
            }
        }

        if (eTx.isActive()) {
            eTx.rollback();
        }
    }

    public void rollbackTransaction() {
        EntityTransaction eTx = get().getTransaction();
        if (eTx.isActive()) {
            eTx.rollback();
        }
    }

}
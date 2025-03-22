package com.yzy.emsp.infrastructure.repository;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.util.Date;

abstract class HibernateSupport<T> {

    private EntityManager entityManager;

    HibernateSupport(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void save(T object) {
        entityManager.persist(object);
        entityManager.flush();
    }

    public void update(T object) {
        entityManager.merge(object);
        entityManager.flush();
    }


}

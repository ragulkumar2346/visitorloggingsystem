package com.example.visitorlogging.dao;

import com.example.visitorlogging.entity.Visitor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class VisitorDAO {
    private SessionFactory sessionFactory;

    public VisitorDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addVisitor(Visitor visitor) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(visitor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Visitor> getAllVisitors() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Visitor", Visitor.class).list();
        }
    }
}

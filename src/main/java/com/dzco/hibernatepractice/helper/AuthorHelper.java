package com.dzco.hibernatepractice.helper;

import com.dzco.hibernatepractice.util.HibernateUtil;
import com.dzco.hibernatepractice.entity.Author;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.logging.LogManager;

import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class AuthorHelper {

    private SessionFactory sessionFactory;

    public AuthorHelper() {
        LogManager.getLogManager().reset();
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public boolean createAuthor(String name) {
        Session session = sessionFactory.openSession();
        try {
            session.getTransaction().begin();
            Author a = new Author();
            a.setName(name);
            session.persist(a);
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
            return false;
        }
        finally {
            session.close();
        }
        return true;
    }

    public Author getAuthor(int id) {
        Session session = sessionFactory.openSession();
        Query<Author> query= session.createQuery("from Author where id =: id", Author.class);
        query.setParameter("id", id);
        Author author;
        try {
            author = query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            session.close();
        }
        return author;
    }

    public Author getAuthor(String name) {
        Session session = sessionFactory.openSession();
        Query<Author> query= session.createQuery("from Author where name =: name", Author.class);
        query.setParameter("name", name);
        Author author;
        try {
            author = query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            session.close();
        }
        return author;
    }

    public boolean updateAuthor(int id, String name) {
        Session session = sessionFactory.openSession();
        int isUpdated = 0;
        try {
            session.getTransaction().begin();
            MutationQuery query= session.createMutationQuery("update Author set name =: name where id =: id");
            query.setParameter("id", id);
            query.setParameter("name", name);
            isUpdated = query.executeUpdate();
            if (isUpdated == 0) {
                throw new NoResultException();
            }
            session.getTransaction().commit();
        } catch (NoResultException ex) {
            return false;
        } catch (Exception ex) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    public boolean deleteAuthor(int id) {
        Session session = sessionFactory.openSession();
        int isUpdated = 0;
        try {
            session.getTransaction().begin();
            MutationQuery query= session.createMutationQuery("delete Author where id =: id");
            query.setParameter("id", id);
            isUpdated = query.executeUpdate();
            if (isUpdated == 0) {
                throw new NoResultException();
            }
            session.getTransaction().commit();
        } catch (NoResultException ex) {
            return false;
        } catch (Exception ex) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    public List<Author> getAuthorList() {
        Session session = sessionFactory.openSession();
        Query<Author> query= session.createQuery("from Author", Author.class);
        List<Author> authorList;
        try{
            authorList = query.getResultList();
        } catch (NoResultException ex) {
            return null;
        } finally {
            session.close();
        }
        return authorList;
    }
}

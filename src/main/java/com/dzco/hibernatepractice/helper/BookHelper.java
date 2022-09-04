package com.dzco.hibernatepractice.helper;

import com.dzco.hibernatepractice.entity.Author;
import com.dzco.hibernatepractice.util.HibernateUtil;
import com.dzco.hibernatepractice.entity.Book;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;
import java.util.logging.LogManager;

public class BookHelper {
    private SessionFactory sessionFactory;

    public BookHelper() {
        //LogManager.getLogManager().reset();
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public boolean createBook(String name, Author author) {
        if (author == null) {
            return false;
        }
        Session session = sessionFactory.openSession();
        try {
            session.getTransaction().begin();
            Book book = new Book();
            book.setName(name);
            book.setAuthor(author);
            session.persist(book);
            session.getTransaction().commit();
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

    public Book getBook(int id) {
        Session session = sessionFactory.openSession();
        Query<Book> query = session.createQuery("from  Book where id =: id", Book.class);
        query.setParameter("id", id);
        Book book;
        try {
            book = query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            session.close();
        }
        return book;
    }

    public boolean updateBook(int id, String bookName, String authorName) {
        Book book = getBook(id);
        if (book == null) {
            return false;
        }
        if (!authorName.isEmpty()) {
            Author author = new AuthorHelper().getAuthor(authorName);
            if (author == null) {
                if (!(new AuthorHelper().createAuthor(authorName))) {
                    return false;
                }
                author  = new AuthorHelper().getAuthor(authorName);
            }
            book.setAuthor(author);
        }
        if (!bookName.isEmpty()) {
            book.setName(bookName);
        }
        Session session = sessionFactory.openSession();
        try {

            session.getTransaction().begin();
            session.merge(book);
            session.getTransaction().commit();
        } catch (NoResultException ex) {
            return false;
        } catch (Exception ex) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    public boolean deleteBook(int id) {
        Session session = sessionFactory.openSession();
        int isUpdated = 0;
        try {
            session.getTransaction().begin();
            MutationQuery query= session.createMutationQuery("delete Book where id =: id");
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

    public List<Book> getBookList() {
        Session session = sessionFactory.openSession();
        Query<Book> query = session.createQuery("from Book", Book.class);
        List<Book> bookList;
        try {
            bookList = query.getResultList();
        } catch (NoResultException ex) {
            return null;
        } finally {
            session.close();
        }
        session.close();
        return bookList;
    }

}

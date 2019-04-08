package com.vasiliska.ORMLibrary.repository.books;

import com.vasiliska.ORMLibrary.domain.Author;
import com.vasiliska.ORMLibrary.domain.Book;
import com.vasiliska.ORMLibrary.domain.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
@Repository
public class BookDaoImp implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> getAllBooks() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    public List<Book> getBookByAuthor(String authorName) {
        TypedQuery<Author> query = em.createQuery("SELECT g FROM Author g WHERE g.authorName = :authorName", Author.class);
        query.setParameter("authorName", authorName);
        Author author = (Author) query.getSingleResult();
        Long authorId = author.getAuthorId();

        TypedQuery<Book> queryListBook = em.createQuery("SELECT b FROM Book b JOIN b.author a WHERE a.authorId = :authorId", Book.class);
        queryListBook.setParameter("authorId", authorId);
        return queryListBook.getResultList();
    }

    @Override
    public List<Book> getBookByGenre(String genreName) {
        TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g WHERE g.genreName = :genreName", Genre.class);
        query.setParameter("genreName", genreName);
        Genre genre = (Genre) query.getSingleResult();
        Long genreId = genre.getGenreId();

        TypedQuery<Book> queryListBook = em.createQuery("SELECT b FROM Book b JOIN b.genre a WHERE a.genreId = :genreId", Book.class);
        queryListBook.setParameter("genreId", genreId);
        return queryListBook.getResultList();
    }

    @Override
    public List<Book> getBookByName(String bookName) {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.bookName =: bookName", Book.class);
        query.setParameter("bookName", bookName);
        return query.getResultList();
    }

    @Override
    public boolean insertBook(Book book) {
        try {
            em.persist(book);
        } catch (Exception ex) {
            log.info("Don't add new book!");
            return false;
        }
        return true;
    }

    @Override
    public boolean delBook(String bookName) {
        Book book = null;
        try
        {
            book = getBookByName(bookName).get(0);
        }
        catch (Exception ex)
        {
            return false;
        }
        if (book != null) {
            em.remove(book);
            return true;
        }
        return false;
    }


}

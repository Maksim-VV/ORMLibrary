package com.vasiliska.ORMLibrary.repository.books;


import com.vasiliska.ORMLibrary.domain.Author;
import com.vasiliska.ORMLibrary.domain.Book;
import com.vasiliska.ORMLibrary.domain.Genre;
import com.vasiliska.ORMLibrary.repository.authors.AuthorDaoImp;
import com.vasiliska.ORMLibrary.repository.genres.GenreDaoImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DirtiesContext
@DataJpaTest
@Import({BookDaoImp.class, AuthorDaoImp.class, GenreDaoImp.class})
public class BookDaoImpTest {

    @Autowired
    private BookDaoImp bookDao;

    @Autowired
    private AuthorDaoImp authorDao;

    @Autowired
    private GenreDaoImp genreDao;

    private final String TEST_BOOK_NAME1 = "Му-му";
    private final String TEST_BOOK_NAME2 = "Собачье сердце";
    private final String TEST_AUTHOR = "Тургенев";
    private final String TEST_GENRE = "Драма";

    @Test
    public void testContext() {
        assertNotNull(bookDao);
    }

    @Test
    public void getAllBooks() {

        assertEquals(bookDao.getAllBooks().size(), 0);
        insertBook();
        assertEquals(bookDao.getAllBooks().size(), 1);
    }

    @Test
    public void insertBookTest() {
        insertBook();
        assertEquals(bookDao.getAllBooks().size(), 1);
        assertEquals(bookDao.getAllBooks().get(0).getBookName(), TEST_BOOK_NAME1);
    }

    @Test
    public void delBook() {
        assertFalse(bookDao.delBook(TEST_BOOK_NAME2));
        insertBook();
        assertTrue(bookDao.delBook(TEST_BOOK_NAME1));
    }

    public void insertBook() {
        Author author = new Author(TEST_AUTHOR);
        authorDao.insertAuthor(author);

        Genre genre = new Genre(TEST_GENRE);
        genreDao.insertGenre(genre);

        Book book = new Book(TEST_BOOK_NAME1, author, genre);
        bookDao.insertBook(book);
    }

}
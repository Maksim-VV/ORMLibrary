package com.vasiliska.ORMLibrary.shell;

import com.vasiliska.ORMLibrary.domain.Author;
import com.vasiliska.ORMLibrary.domain.Book;
import com.vasiliska.ORMLibrary.domain.Comment;
import com.vasiliska.ORMLibrary.domain.Genre;
import com.vasiliska.ORMLibrary.repository.authors.AuthorDaoImp;
import com.vasiliska.ORMLibrary.repository.books.BookDaoImp;
import com.vasiliska.ORMLibrary.repository.comments.CommentDaoImp;
import com.vasiliska.ORMLibrary.repository.genres.GenreDaoImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CommandsTest {

    @Autowired
    private Shell shell;

    @Autowired
    private BookDaoImp bookDao;

    @Autowired
    private AuthorDaoImp authorDao;

    @Autowired
    private GenreDaoImp genreDao;

    @Autowired
    private CommentDaoImp commentDao;

    @Autowired
    Commands commands;

    private final String TEST_BOOK_NAME1 = "Aйвенго";
    private final String TEST_BOOK_AUTHOR1 = "В.Скотт";
    private final String TEST_BOOK_GENRE1 = "Роман";

    private final String TEST_BOOK_NAME2 = "Я вижу: человек сидит на стуле и стул кусает его за ногу";
    private final String TEST_BOOK_AUTHOR2 = "Р.Шекли";
    private final String TEST_BOOK_GENRE2 = "Фантастика";

    private final String TEST_COMMENT1 = "Жесть!";
    private final String TEST_COMMENT2 = "Буду читать вновь";

    @Test
    public void findbook() {
        insertTestBook(TEST_BOOK_NAME1, TEST_BOOK_AUTHOR1, TEST_BOOK_GENRE1);
        Object textTest = shell.evaluate(() -> "findbook Aйвенго");
        assertTrue(textTest.toString().contains(TEST_BOOK_NAME1));
    }

    @Test
    public void addbook() {
        Object textTest = shell.evaluate(() -> "addbook Aйвенго В.Скотт Роман");
        assertTrue(textTest.toString().contains(TEST_BOOK_NAME1));
    }

    @Test
    public void delbook() {
        insertTestBook(TEST_BOOK_NAME1, TEST_BOOK_AUTHOR1, TEST_BOOK_GENRE1);
        Object textTest = shell.evaluate(() -> "delbook Aйвенго");
        assertTrue(textTest.toString().contains(TEST_BOOK_NAME1));
    }

    @Test
    public void allbooks() {
        insertTestBook(TEST_BOOK_NAME2, TEST_BOOK_AUTHOR2, TEST_BOOK_GENRE2);
        Object textTest = shell.evaluate(() -> "allbooks");

        assertTrue(textTest.toString().contains(TEST_BOOK_NAME2));
        assertTrue(textTest.toString().contains(TEST_BOOK_AUTHOR2));
        assertTrue(textTest.toString().contains(TEST_BOOK_GENRE2));
    }

    @Test
    public void byauthor() {
        insertTestBook(TEST_BOOK_NAME1, TEST_BOOK_AUTHOR1, TEST_BOOK_GENRE1);
        Object textTest = shell.evaluate(() -> "byauthor В.Скотт");
        assertTrue(textTest.toString().contains(TEST_BOOK_NAME1));
    }

    @Test
    public void bygenre() {
        insertTestBook(TEST_BOOK_NAME1, TEST_BOOK_AUTHOR1, TEST_BOOK_GENRE1);
        Object textTest = shell.evaluate(() -> "bygenre Роман");
        assertTrue(textTest.toString().contains(TEST_BOOK_NAME1));
    }

    @Test
    public void addcomment() {
        Book book = insertTestBook(TEST_BOOK_NAME1, TEST_BOOK_AUTHOR1, TEST_BOOK_GENRE1);
        shell.evaluate(() -> "addcomment " + TEST_COMMENT1 + " " + TEST_BOOK_NAME1);
        List<Comment> result = commentDao.getCommentByBook(book);
        assertThat(result).isNotNull().hasSize(1).toString().contains(TEST_COMMENT1);
    }

    @Test
    public void getcomment() {
        Book book = insertTestBook(TEST_BOOK_NAME1, TEST_BOOK_AUTHOR1, TEST_BOOK_GENRE1);
        commentDao.addComment(TEST_COMMENT1, book);
        commentDao.addComment(TEST_COMMENT2, book);
        shell.evaluate(() -> "getcomment " + TEST_BOOK_NAME1);
        List<Comment> result = commentDao.getCommentByBook(book);
        assertThat(result).isNotNull().hasSize(2).toString().contains("Буду");
    }

    private Book insertTestBook(String bookName, String authorName, String genreName) {
        insertTestAuthor(authorName);
        insertTestGenre(genreName);
        Book book = new Book(bookName, authorDao.getAuthorByName(authorName), genreDao.getGenreByName(genreName));
        bookDao.insertBook(book);
        return book;
    }

    private Author insertTestAuthor(String authorName) {
        Author author = new Author(authorName);
        authorDao.insertAuthor(author);
        return author;
    }

    private Genre insertTestGenre(String genreName) {
        Genre genre = new Genre(genreName);
        genreDao.insertGenre(genre);
        return genre;
    }
}
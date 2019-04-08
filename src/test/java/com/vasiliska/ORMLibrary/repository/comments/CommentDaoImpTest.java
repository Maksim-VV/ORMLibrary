package com.vasiliska.ORMLibrary.repository.comments;

import com.vasiliska.ORMLibrary.domain.Author;
import com.vasiliska.ORMLibrary.domain.Book;
import com.vasiliska.ORMLibrary.domain.Comment;
import com.vasiliska.ORMLibrary.domain.Genre;
import com.vasiliska.ORMLibrary.repository.authors.AuthorDaoImp;
import com.vasiliska.ORMLibrary.repository.books.BookDaoImp;
import com.vasiliska.ORMLibrary.repository.genres.GenreDaoImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
@Import({BookDaoImp.class, AuthorDaoImp.class, GenreDaoImp.class, CommentDaoImp.class})
public class CommentDaoImpTest {

    @Autowired
    private CommentDaoImp commentDao;

    @Autowired
    private BookDaoImp bookDao;

    @Autowired
    private AuthorDaoImp authorDao;

    @Autowired
    private GenreDaoImp genreDao;

    private final String TEST_BOOK_NAME1 = "Му-му";
    private final String TEST_AUTHOR = "Тургенев";
    private final String TEST_GENRE = "Драма";
    private final String TEST_COMMENT = "Тестовый комментарий";

    @Test
    public void getCommentByBook() {
        Book book = insertBook();
        commentDao.addComment("TEST_COMMENT", book);
        List<Comment> comments = commentDao.getCommentByBook(book);
        assertThat(comments.get(0).getCommentText().contains(TEST_COMMENT));
    }

    @Test
    public void addComment() {
        Book book = insertBook();
        assertTrue(commentDao.addComment("TEST_COMMENT", book));
    }

    public Book insertBook() {
        Author author = new Author(TEST_AUTHOR);
        authorDao.insertAuthor(author);

        Genre genre = new Genre(TEST_GENRE);
        genreDao.insertGenre(genre);

        Book book = new Book(TEST_BOOK_NAME1, author, genre);
        bookDao.insertBook(book);
        return book;
    }

}
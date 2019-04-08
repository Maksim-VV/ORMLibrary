package com.vasiliska.ORMLibrary.service;


import com.vasiliska.ORMLibrary.repository.books.BookDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
})
public class ShellServiceImplTest {

    @MockBean
    private BookDao bookDao;

    @Autowired
    ShellServiceImpl shellService;

    private final String TEST_BOOK_NAME = "Айвенго";
    private final String TEST_AUTHOR = "В.Скотт";
    private final String TEST_GENRE = "Роман";
    private final String TEST_COMMENT = "Лучше книги нет!";

    @Test
    public void addNewBook() {
        assertTrue(shellService.addNewBook(TEST_BOOK_NAME, TEST_AUTHOR, TEST_GENRE).contains(TEST_BOOK_NAME));
    }

    @Test
    public void bookByGenre() {
        shellService.bookByGenre(TEST_GENRE);
        verify(bookDao).getBookByGenre(TEST_GENRE);
    }

    @Test
    public void bookByName() {
        shellService.bookByName(TEST_BOOK_NAME);
        verify(bookDao).getBookByName(TEST_BOOK_NAME);
    }

    @Test
    public void bookByAuthor() {
        shellService.bookByAuthor(TEST_AUTHOR);
        verify(bookDao).getBookByAuthor(TEST_AUTHOR);
    }

    @Test
    public void showAllBooks() {
        shellService.showAllBooks();
        verify(bookDao).getAllBooks();
    }

    @Test
    public void addComment() {
        shellService.addComment(TEST_COMMENT, TEST_BOOK_NAME);
        verify(bookDao).getBookByName(TEST_BOOK_NAME);
    }

    @Test
    public void getCommentByBook() {
        shellService.getCommentsByBook(TEST_BOOK_NAME);
        verify(bookDao).getBookByName(TEST_BOOK_NAME);
    }

}
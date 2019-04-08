package com.vasiliska.ORMLibrary.service;


import com.vasiliska.ORMLibrary.domain.Author;
import com.vasiliska.ORMLibrary.domain.Book;
import com.vasiliska.ORMLibrary.domain.Comment;
import com.vasiliska.ORMLibrary.domain.Genre;
import com.vasiliska.ORMLibrary.repository.authors.AuthorDao;
import com.vasiliska.ORMLibrary.repository.books.BookDao;
import com.vasiliska.ORMLibrary.repository.comments.CommentDao;
import com.vasiliska.ORMLibrary.repository.genres.GenreDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ShellServiceImpl implements ShellService {

    private BookDao bookDao;
    private GenreDao genreDao;
    private AuthorDao authorDao;
    private CommentDao commentDao;

    private final String MSG_DONT_FIND = "Объект не найден!";
    private final String MSG_ADD_NEW_BOOK = "Книга \"%s\" добавлена";
    private final String MSG_ADD_NEW_COMMENT = "Комментарий на книгу \"%s\" добавлен";
    private final String MSG_DELETE_BOOK = "Книга \"%s\" удалена из библиотеки.";
    private final String MSG_DONT_ADD_BOOK = "Не удалось добавить книгу \"%s\"";

    @Autowired
    public ShellServiceImpl(BookDao bookDao, GenreDao genreDao, AuthorDao authorDao, CommentDao commentDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
        this.commentDao = commentDao;
    }

    public ShellServiceImpl() {
    }

    @Override
    @Transactional
    public String addNewBook(String bookName, String authorName, String genreName) {
        Genre genre = genreDao.getGenreByName(genreName);
        if (genre == null) {
            Genre genreNew = new Genre(genreName);
            genre = genreDao.insertGenre(genreNew);
        }

        Book book = new Book();
        book.setBookName(bookName);
        book.setGenre(genre);

        Author author = authorDao.getAuthorByName(authorName);
        if (author == null) {
            Author authorNew = new Author(authorName);
            author = authorDao.insertAuthor(authorNew);
        }
        book.setAuthor(author);
        if (!bookDao.insertBook(book)) {
            return String.format(MSG_DONT_ADD_BOOK, bookName);
        }
        return String.format(MSG_ADD_NEW_BOOK, bookName);
    }

    @Override
    @Transactional
    public String delBook(String bookName) {
        List<Book> listBooks = bookDao.getBookByName(bookName);
        if (listBooks == null || listBooks.isEmpty()) {
                   return MSG_DONT_FIND;
               }
        String authorName = listBooks.get(0).getAuthor().getAuthorName();
        List<Book> listBookByAuthor = bookDao.getBookByAuthor(authorName);
        if (listBookByAuthor.size() == 1) {
            authorDao.delAuthor(authorName);
        }

        String genreName = listBooks.get(0).getGenre().getGenreName();
        List<Book> listBookByGenre = bookDao.getBookByGenre(genreName);
        if (listBookByGenre.size() == 1) {
            genreDao.delGenre(genreName);
        }

        bookDao.delBook(bookName);
        return String.format(MSG_DELETE_BOOK, bookName);
    }

    @Override
    @Transactional(readOnly = true)
    public String bookByGenre(String genreName) {
        return showBooks(bookDao.getBookByGenre(genreName));
    }

    @Override
    @Transactional(readOnly = true)
    public String bookByName(String bookName) {
        return showBooks(bookDao.getBookByName(bookName));
    }

    @Override
    @Transactional(readOnly = true)
    public String bookByAuthor(String authorName) {
        return showBooks(bookDao.getBookByAuthor(authorName));
    }

    @Override
    @Transactional(readOnly = true)
    public String showAllBooks() {
        return showBooks(bookDao.getAllBooks());
    }

    @Override
    @Transactional
    public String addComment(String commentText, String bookName) {
        List<Book> listBooks = bookDao.getBookByName(bookName);
        if (listBooks == null || listBooks.isEmpty()) {
            return MSG_DONT_FIND;
        }
        Book book = listBooks.get(0);
        commentDao.addComment(commentText, book);
        return String.format(MSG_ADD_NEW_COMMENT, bookName);
    }

    @Override
    @Transactional(readOnly = true)
    public String getCommentsByBook(String bookName) {
        List<Book> listBooks = bookDao.getBookByName(bookName);
        if (listBooks == null || listBooks.isEmpty()) {
            return MSG_DONT_FIND;
        }
        Book book = listBooks.get(0);
        return showComments(commentDao.getCommentByBook(book));
    }

    private String showBooks(List<Book> listBooks) {
        if (listBooks == null || listBooks.isEmpty()) {
            return MSG_DONT_FIND;
        }
        StringBuffer stringBuffer = new StringBuffer();
        listBooks.forEach(stringBuffer::append);
        return stringBuffer.toString();
    }

    private String showComments(List<Comment> listComments) {
        if (listComments == null || listComments.isEmpty()) {
            return MSG_DONT_FIND;
        }
        StringBuffer stringBuffer = new StringBuffer();
        listComments.forEach(stringBuffer::append);
        return stringBuffer.toString();
    }
}



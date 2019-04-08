package com.vasiliska.ORMLibrary.repository.books;


import com.vasiliska.ORMLibrary.domain.Book;

import java.util.List;

public interface BookDao {

    List<Book> getAllBooks();

    List<Book> getBookByAuthor(String name);

    List<Book> getBookByGenre(String genreId);

    List<Book> getBookByName(String name);

    boolean insertBook(Book book);

    boolean delBook(String name);
}

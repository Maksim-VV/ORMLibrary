package com.vasiliska.ORMLibrary.repository.authors;


import com.vasiliska.ORMLibrary.domain.Author;

public interface AuthorDao {

    boolean delAuthor(String authorName);

    Author getAuthorByName(String name);

    Author insertAuthor(Author authorName);
}

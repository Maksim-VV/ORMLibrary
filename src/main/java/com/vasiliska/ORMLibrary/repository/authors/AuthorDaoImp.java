package com.vasiliska.ORMLibrary.repository.authors;


import com.vasiliska.ORMLibrary.domain.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Slf4j
@Repository
public class AuthorDaoImp implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    private final String SEARCH_AUTHOR_BY_NAME = "SELECT a FROM Author a WHERE a.authorName = :authorName";

    @Override
    public boolean delAuthor(String authorName) {
        Author author = getAuthorByName(authorName);
        em.remove(author);
        return true;
    }

    @Override
    public Author getAuthorByName(String authorName) {
        TypedQuery<Author> query = em.createQuery(SEARCH_AUTHOR_BY_NAME, Author.class);
        query.setParameter("authorName", authorName);
        try {
            return query.getSingleResult();
        } catch (Exception e) {

            Author author = new Author();
            author.setAuthorName(authorName);
            insertAuthor(author);
            return author;
        }
    }

    @Override
    public Author insertAuthor(Author author) {
        em.persist(author);
        return author;
    }


}

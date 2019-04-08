package com.vasiliska.ORMLibrary.repository.genres;

import com.vasiliska.ORMLibrary.domain.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Slf4j
@Repository
public class GenreDaoImp implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    private final String SELECT_GENRE_ID_BY_NAME = "SELECT g FROM Genre g WHERE g.genreName = :genreName";

    @Override
    public boolean delGenre(String genreName) {
        Genre genre = getGenreByName(genreName);
        em.remove(genre);
        return true;
    }

    @Override
    public Genre getGenreByName(String genreName) {
        TypedQuery<Genre> query = em.createQuery(SELECT_GENRE_ID_BY_NAME, Genre.class);
        query.setParameter("genreName", genreName);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            Genre genre = new Genre();
            genre.setGenreName(genreName);
            insertGenre(genre);
            return genre;
        }
    }

    @Override
    public Genre insertGenre(Genre genre) {
        em.persist(genre);
        return genre;
    }
}

package com.vasiliska.ORMLibrary.repository.genres;


import com.vasiliska.ORMLibrary.domain.Genre;

public interface GenreDao {

    boolean delGenre(String genreName);

    Genre getGenreByName(String genreName);

    Genre insertGenre(Genre genre);
}

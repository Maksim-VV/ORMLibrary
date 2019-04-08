package com.vasiliska.ORMLibrary.repository.genres;

import com.vasiliska.ORMLibrary.domain.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
@Import({GenreDaoImp.class})
public class GenreDaoImpTest {

    @Autowired
    private GenreDaoImp genreDao;

    private static final String TEST_GENRE_NAME1 = "Драма";
    private static final String TEST_GENRE_NAME2 = "Фантастика";

    @Test
    public void testContext() {
        assertNotNull(genreDao);
    }

    @Test
    public void delGenre() {
    }

    @Test
    public void getGenreByName() {
        Genre genre = genreDao.getGenreByName(TEST_GENRE_NAME1);
        assertEquals(genre.getGenreName(), TEST_GENRE_NAME1);
    }

    @Test
    public void insertGenre() {
        Genre genre = new Genre(TEST_GENRE_NAME2);
        genreDao.insertGenre(genre);
        assertEquals(genre.getGenreName(), TEST_GENRE_NAME2);
    }

}
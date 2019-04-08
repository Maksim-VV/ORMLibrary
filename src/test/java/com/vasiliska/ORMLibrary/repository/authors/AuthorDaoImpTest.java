package com.vasiliska.ORMLibrary.repository.authors;

import com.vasiliska.ORMLibrary.domain.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
@Import({AuthorDaoImp.class})
public class AuthorDaoImpTest {

    private static final String TEST_AUTHOR_NAME1 = "Булгаков";
    private static final String TEST_AUTHOR_NAME2 = "Толкиен";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuthorDaoImp authorDao;


    @Test
    public void testContext() {
        assertNotNull(authorDao);
    }

    @Test
    public void getAuthorByNameTest() {
        insertTestAuthor(TEST_AUTHOR_NAME1);
        Author author = authorDao.getAuthorByName(TEST_AUTHOR_NAME1);
        Long authorId = author.getAuthorId();
        assertEquals(authorId.longValue(), 1L);
    }

    @Test
    public void delAuthorTest() {
        insertTestAuthor(TEST_AUTHOR_NAME1);
        assertTrue(authorDao.delAuthor(TEST_AUTHOR_NAME2));
    }

    private void insertTestAuthor(String testName) {
        Author author = new Author();
        author.setAuthorName(testName);
        entityManager.persist(author);
    }
}
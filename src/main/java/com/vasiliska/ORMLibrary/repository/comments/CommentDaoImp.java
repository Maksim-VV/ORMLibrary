package com.vasiliska.ORMLibrary.repository.comments;

import com.vasiliska.ORMLibrary.domain.Book;
import com.vasiliska.ORMLibrary.domain.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class CommentDaoImp implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> getCommentByBook(Book book) {
        TypedQuery<Comment> queryListCommets = em.createQuery("SELECT b FROM Comment b JOIN b.book a WHERE a.bookId = :bookId", Comment.class);
        Long bookId = book.getBookId();
        queryListCommets.setParameter("bookId", bookId);

        List<Comment> listComment = queryListCommets.getResultList();

        return listComment;
    }

    @Override
    public boolean addComment(String commentText, Book book) {
        Comment comment = new Comment(commentText, book);
        em.persist(comment);
        return true;
    }
}

package com.vasiliska.ORMLibrary.repository.comments;


import com.vasiliska.ORMLibrary.domain.Book;
import com.vasiliska.ORMLibrary.domain.Comment;

import java.util.List;

public interface CommentDao {

    List<Comment> getCommentByBook(Book book);

    boolean addComment(String commentText, Book book);
}

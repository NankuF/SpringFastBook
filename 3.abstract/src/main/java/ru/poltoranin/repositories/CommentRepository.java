package ru.poltoranin.repositories;

import ru.poltoranin.model.Comment;

public interface CommentRepository {
    void storeComment(Comment comment);
}

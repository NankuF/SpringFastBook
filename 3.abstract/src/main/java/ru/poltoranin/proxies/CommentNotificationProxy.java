package ru.poltoranin.proxies;

import ru.poltoranin.model.Comment;

public interface CommentNotificationProxy {
    void sendComment(Comment comment);
}

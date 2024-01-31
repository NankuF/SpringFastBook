package ru.poltoranin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.poltoranin.model.Comment;
import ru.poltoranin.proxies.CommentNotificationProxy;
import ru.poltoranin.repositories.CommentRepository;

@Service
public class CommentService {
    private final CommentNotificationProxy emailCommentNotificationProxy;
    private final CommentNotificationProxy pushCommentNotificationProxy;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(
        @Qualifier("EMAIL")CommentNotificationProxy eCommentNotificationProxy,
        @Qualifier("PUSH")CommentNotificationProxy pCommentNotificationProxy,
            CommentRepository commentRepository) {
        this.emailCommentNotificationProxy = eCommentNotificationProxy;
        this.pushCommentNotificationProxy = pCommentNotificationProxy;
        this.commentRepository = commentRepository;
    }

    public void publishEmailComment(Comment comment) {
        commentRepository.storeComment(comment);
        emailCommentNotificationProxy.sendComment(comment);
    }

    public void publishPushComment(Comment comment) {
        commentRepository.storeComment(comment);
        pushCommentNotificationProxy.sendComment(comment);
    }
}

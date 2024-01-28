package ru.poltoranin.services;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import ru.poltoranin.aspects.ToLog;
import ru.poltoranin.model.Comment;

@Service
public class CommentServices {
    private Logger logger = Logger.getLogger(CommentServices.class.getName());

    public void publishComment(Comment comment) {
        logger.info("Publishing comment:" + comment.getText());
    }

    @ToLog
    public String deleteComment(Comment comment) {
        logger.info("Deleting comment:" + comment.getText());
        return "Edited";
    }

    public void editComment(Comment comment) {
        logger.info("Editing comment:" + comment.getText());
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}

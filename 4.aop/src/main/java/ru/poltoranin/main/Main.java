package ru.poltoranin.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.poltoranin.config.ProjectConfig;
import ru.poltoranin.model.Comment;
import ru.poltoranin.services.CommentServices;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var service = context.getBean(CommentServices.class);

        Comment comment = new Comment();
        comment.setText("Demo comment");
        comment.setAuthor("Natasha");

        service.publishComment(comment);
        service.deleteComment(comment);
        service.editComment(comment);

        context.close();
    }
}
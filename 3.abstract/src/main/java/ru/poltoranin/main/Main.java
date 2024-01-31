package ru.poltoranin.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.poltoranin.config.ProjectConfig;
import ru.poltoranin.model.Comment;
import ru.poltoranin.services.CommentService;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var commentService = context.getBean(CommentService.class);
        var comment = new Comment();
        comment.setText("Hello world");
        comment.setAuthor("VP");
        commentService.publishEmailComment(comment);
        commentService.publishPushComment(comment);

        context.close();
    }
}

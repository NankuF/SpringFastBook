package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.poltoranin.config.ProjectConfig;
import ru.poltoranin.main.Parrot;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ProjectConfig.class })
public class AppTests {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("Test that a Parrot instance " +
            "with the attribute name having the value Miki " +
            "has been added to the Spring context.")
    public void testMikiIsInTheSpringContext() {
        Parrot p = context.getBean("miki",Parrot.class);

        assertEquals("Miki", p.getName());
    }
    @Test
    @DisplayName("Test that the Parrot instance parrot1 named Koko is primary")
    public void testParrot1IsPrimary() {
        Parrot p = context.getBean(Parrot.class);

        assertEquals("Koko", p.getName());
    }
}

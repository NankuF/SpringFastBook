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
@ContextConfiguration(classes = {ProjectConfig.class})
public class AppTests {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("any")
    public void testSetAbuAfterInitObject() {
        Parrot p = context.getBean(Parrot.class);
        assertEquals("Abu", p.getName());
    }
}

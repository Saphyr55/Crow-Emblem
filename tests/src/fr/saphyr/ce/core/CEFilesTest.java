package fr.saphyr.ce.core;

import com.badlogic.gdx.Gdx;
import fr.saphyr.ce.CETest;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CEFilesTest extends CETest {

    @Test
    public void testFound() {
        assertTrue(CEFiles.found("assets/tests")
                        .contains("assets/tests/test.txt"),
                "Impossible to find assets folder");
    }

    @Test
    public void testFoundInternal() {
        assertTrue(CEFiles.foundInternal("assets/tests")
                        .contains("assets/tests/test.txt"),
                "Impossible to find assets folder");
    }

}

package fr.saphyr.ce.core;

import fr.saphyr.ce.CEFiles;
import fr.saphyr.ce.CETest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

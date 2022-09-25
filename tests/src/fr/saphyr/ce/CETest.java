package fr.saphyr.ce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Files;
import fr.saphyr.ce.core.MockGL;
import fr.saphyr.ce.core.register.Resources;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class CETest {

    @BeforeAll
    public static void beforeAll() {
        Gdx.app = new HeadlessApplication(new ApplicationAdapter() { });

        Resources.load();
    }

    @AfterAll
    public static void afterAll() {
        Gdx.app.exit();
    }

}
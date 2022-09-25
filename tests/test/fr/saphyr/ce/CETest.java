package fr.saphyr.ce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import fr.saphyr.ce.core.register.Registry;
import fr.saphyr.ce.core.register.Resources;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static fr.saphyr.ce.core.register.Resources.MANAGER;

public class CETest {

    @BeforeAll
    public static void beforeAll() {
        Gdx.app = new HeadlessApplication(new ApplicationAdapter() { });
        Gdx.gl = new MockGL();
        Resources.load();
        while (!MANAGER.update()) {
        }
        Registry.registers();
    }

    @AfterAll
    public static void afterAll() {
        Gdx.app.exit();
    }

}
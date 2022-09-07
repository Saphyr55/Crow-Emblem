package fr.saphyr.ce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


public class CETest {

    @BeforeAll
    public static void setup() {
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        Gdx.app = new HeadlessApplication(new ApplicationAdapter() {}, conf);
    }

    @AfterAll
    public static void teardown() {
        Gdx.app.exit();
    }
}

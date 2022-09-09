package fr.saphyr.ce.scenes;

import com.badlogic.gdx.utils.ArrayMap;
import fr.saphyr.ce.core.Renderer;

public final class SceneManager {

    private Scene currentScene;
    private Renderer renderer;
    private ArrayMap<String, Scene> scenes;

    public SceneManager(Renderer renderer) {
        this.renderer = renderer;
        this.scenes = new ArrayMap<>();
    }

    public void init() {
        if (!currentScene.isInit())
            currentScene.init();
    }

    public void update(float dt) {
        currentScene.update(dt);
    }

    public void render(Renderer renderer) {
        renderer.begin();
        currentScene.render(renderer);
        renderer.end();
    }

    public final void add(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public final Scene get(String name) {
        return scenes.get(name);
    }

    public void switchTo(String name) {
        if (currentScene != null)
            currentScene.setActive(false);

        currentScene = get(name);
        currentScene.setActive(true);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void dispose() {
        scenes.forEach(stringEntry -> stringEntry.value.dispose());
    }
}

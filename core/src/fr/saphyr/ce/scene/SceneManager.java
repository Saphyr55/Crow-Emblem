package fr.saphyr.ce.scene;

import com.badlogic.gdx.utils.ArrayMap;
import fr.saphyr.ce.core.Renderer;

public final class SceneManager {

    private IScene currentScene;
    private Renderer renderer;
    private final ArrayMap<String, IScene> scenes;

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

    public void add(String name, IScene scene) {
        scenes.put(name, scene);
    }

    public IScene get(String name) {
        return scenes.get(name);
    }

    public void switchTo(String name) {
        if (currentScene != null) currentScene.setActive(false);
        currentScene = get(name);
        currentScene.setActive(true);
    }

    public IScene getCurrentScene() {
        return currentScene;
    }

    public void dispose() {
        scenes.forEach(stringEntry -> stringEntry.value.dispose());
    }
}

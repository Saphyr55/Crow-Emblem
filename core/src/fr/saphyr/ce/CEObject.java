package fr.saphyr.ce;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface CEObject {

    void update(final float dt);
    
    void render(Renderer renderer);

}

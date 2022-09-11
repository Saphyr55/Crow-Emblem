package fr.saphyr.ce;

import fr.saphyr.ce.core.Renderer;

public interface CEObject {
    
    void update(final float dt);
    
    void render(Renderer renderer);

}

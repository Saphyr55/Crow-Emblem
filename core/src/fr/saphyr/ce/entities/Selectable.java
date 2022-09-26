package fr.saphyr.ce.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.world.IWorld;
import fr.saphyr.ce.world.WorldPos;

import java.util.function.Consumer;

public interface Selectable {

    default void selectOnClick(int key, WorldPos worldPos, boolean accept, Runnable ifRunnable, Runnable elseRunnable) {
        final Consumer<Boolean> consumer = aBoolean -> {
            if (isClickOnFrame(key, worldPos.getWorld(), worldPos.getPos())) {
                if (aBoolean) ifRunnable.run();
                else elseRunnable.run();
            }
        };
        consumer.accept(accept);
    }



    default boolean isJustPressOnFrame(int key, IWorld world, float posX, float posY) {
        return Gdx.input.isKeyPressed(key) &&
                (int) world.getMouseWorldPos().getPos().x == (int) posX &&
                (int) world.getMouseWorldPos().getPos().y == (int) posY;
    }

    default boolean isClickOnFrame(int key, WorldPos worldPos) {
        return isClickOnFrame(key, worldPos.getWorld(), worldPos.getPos());
    }

    default boolean isClickOnFrame(int key, IWorld world, Vector3 pos) {
        return isClickOnFrame(key, world, pos.x, pos.y);
    }

    default boolean isClickOnFrame(int key, IWorld world, float posX, float posY) {
        return Gdx.input.isButtonJustPressed(key) &&
                (int) world.getMouseWorldPos().getPos().x == (int) posX &&
                (int) world.getMouseWorldPos().getPos().y == (int) posY;
    }

}

package fr.saphyr.ce.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import fr.saphyr.ce.area.Area;
import fr.saphyr.ce.worlds.World;

import java.util.function.Consumer;

public interface Selectable {

    default void selectOnClick(int key, World world, Vector2 pos, boolean accept, Runnable ifRunnable, Runnable elseRunnable) {
        Consumer<Boolean> consumer = aBoolean -> {
            if (isClickOnFrame(key, world, pos)) {
                if (aBoolean) ifRunnable.run();
                else elseRunnable.run();
            }
        };
        consumer.accept(accept);
    }

    default boolean isClickOnFrame(int key, World world, float posX, float posY) {
        return Gdx.input.isButtonJustPressed(key) &&
                (int) world.getMousePos().x == (int) posX &&
                (int) world.getMousePos().y == (int) posY;
    }

    default boolean isJustPressOnFrame(World world, int key, float posX, float posY) {
        return Gdx.input.isKeyPressed(key) &&
                (int) world.getMousePos().x == (int) posX &&
                (int) world.getMousePos().y == (int) posY;
    }

    default boolean isClickOnFrame(int key, World world, Vector2 pos) {
        return isClickOnFrame(key, world, pos.x, pos.y);
    }

    Area getAreaSelect();

}

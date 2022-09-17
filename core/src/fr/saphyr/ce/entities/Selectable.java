package fr.saphyr.ce.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import fr.saphyr.ce.area.Area;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.worlds.World;
import fr.saphyr.ce.worlds.WorldPos;

import java.util.Optional;
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



    default boolean isJustPressOnFrame(int key, World world, float posX, float posY) {
        return Gdx.input.isKeyPressed(key) &&
                (int) world.getMouseWorldPos().getPos().x == (int) posX &&
                (int) world.getMouseWorldPos().getPos().y == (int) posY;
    }

    default boolean isClickOnFrame(int key, WorldPos worldPos) {
        return isClickOnFrame(key, worldPos.getWorld(), worldPos.getPos());
    }

    default boolean isClickOnFrame(int key, World world, Vector3 pos) {
        return isClickOnFrame(key, world, pos.x, pos.y);
    }

    default boolean isClickOnFrame(int key, World world, float posX, float posY) {
        return Gdx.input.isButtonJustPressed(key) &&
                (int) world.getMouseWorldPos().getPos().x == (int) posX &&
                (int) world.getMouseWorldPos().getPos().y == (int) posY;
    }

    Optional<Area> getAreaSelect();

}

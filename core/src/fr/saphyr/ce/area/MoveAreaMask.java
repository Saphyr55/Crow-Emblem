package fr.saphyr.ce.area;

import java.util.function.Consumer;

public interface MoveAreaMask extends Consumer<Area> {

    void accept(Area area);

}

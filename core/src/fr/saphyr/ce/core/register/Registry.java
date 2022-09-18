package fr.saphyr.ce.core.register;

import fr.saphyr.ce.area.MoveAreaAttributes;
import fr.saphyr.ce.entities.EntityType;

import java.util.function.Supplier;

public class Registry {

    public static <T> T register(Supplier<T> supplier) {
        return supplier.get();
    }

    public static void registers() {
        MoveAreaAttributes.registers();
        EntityType.registers();
    }
    
}

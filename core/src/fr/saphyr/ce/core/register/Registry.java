package fr.saphyr.ce.core.register;

import fr.saphyr.ce.entities.area.MoveAreaAttributes;
import fr.saphyr.ce.entities.EntityType;

import java.util.function.Supplier;

public final class Registry {

    public static <T> T register(Supplier<T> supplier) {
        return supplier.get();
    }
    private static boolean isRegister = false;

    public static void registers() {
        if (!isRegister) {
            MoveAreaAttributes.registers();
            EntityType.registers();
            isRegister = true;
        }
    }
    
}

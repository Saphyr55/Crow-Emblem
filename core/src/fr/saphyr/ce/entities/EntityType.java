package fr.saphyr.ce.entities;

import fr.saphyr.ce.world.area.MoveAreaAttribute;
import fr.saphyr.ce.world.area.MoveAreaAttributes;
import fr.saphyr.ce.entities.enemies.Slime;
import fr.saphyr.ce.entities.players.BladeLord;
import fr.saphyr.ce.world.WorldPos;

public final class EntityType<T extends Entity> {

    public static final EntityType<BladeLord> BLADE_LORD = register("lord", EntityBuilder.of(BladeLord::new).withMoveAreaAttribute(MoveAreaAttributes.LARGE));
    public static final EntityType<Slime> SLIME = register("slime", EntityBuilder.of(Slime::new).withMoveAreaAttribute(MoveAreaAttributes.DEFAULT));
    public static void registers() {

    }

    public static <T extends Entity> EntityType<T> register(String key, EntityBuilder<T> builder) {
        return new EntityType<>(key, builder);
    }

    private final EntityBuilder<T> builder;
    private final String key;

    private EntityType(String key, EntityBuilder<T> builder) {
        this.key = key;
        this.builder = builder;
    }

    public Entity create() {
        return builder
                .withMoveAreaAttribute(MoveAreaAttributes.DEFAULT)
                .withTileNotExplorable(new int[0])
                .build();
    }

    public EntityBuilder<T> construct() {
        return builder;
    }

    public String getKey() {
        return key;
    }

    public final static class EntityBuilder<T extends Entity> {

        private WorldPos worldPos;
        private int[] tileNotExplorable;
        private MoveAreaAttribute moveAreaAttribute;
        private final EntityType.EntityFactory<T> entityFactory;

        private EntityBuilder(EntityFactory<T> factory) {
            this.entityFactory = factory;
        }

        public static <T extends Entity> EntityBuilder<T> of(EntityFactory<T> entityFactory) {
            return new EntityBuilder<>(entityFactory);
        }

        public WorldPos getWorldPos() {
            return worldPos;
        }

        public EntityBuilder<T> withWorldPos(WorldPos worldPos) {
            this.worldPos = worldPos;
            return this;
        }

        public int[] getTileNotExplorable() {
            return tileNotExplorable;
        }

        public EntityBuilder<T> withTileNotExplorable(int[] tileNotExplorable) {
            this.tileNotExplorable = tileNotExplorable;
            return this;
        }

        public MoveAreaAttribute getMoveAreaAttribute() {
            return moveAreaAttribute;
        }

        public EntityBuilder<T> withMoveAreaAttribute(MoveAreaAttribute moveAreaAttribute) {
            this.moveAreaAttribute = moveAreaAttribute;
            return this;
        }

        public Entity build() {
            return entityFactory.create(this.worldPos, this.tileNotExplorable, this.moveAreaAttribute);
        }

    }

    @FunctionalInterface
    public interface EntityFactory<T extends Entity> {
        T create(WorldPos worldPos, int[] tileNotExplorable, MoveAreaAttribute moveAreaAttribute);
    }

}

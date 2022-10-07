package fr.saphyr.ce.entities.area;

import com.badlogic.gdx.utils.ObjectSet;
import fr.saphyr.ce.CEObject;
import fr.saphyr.ce.core.Renderer;
import fr.saphyr.ce.entities.IEntity;
import fr.saphyr.ce.entities.area.cell.AttackCell;
import fr.saphyr.ce.utils.Pair;
import fr.saphyr.ce.world.IWorld;
import fr.saphyr.ce.world.area.AbstractArea;
import fr.saphyr.ce.world.area.cell.AbstractCell;

public class AttackArea extends AbstractArea<AttackCell> implements CEObject {

    private final ObjectSet<Pair<AttackCell, IEntity>> entities;

    public AttackArea(IWorld world) {
        super(world);
        entities = new ObjectSet<>();
        setEntities();
    }

    public boolean haveMinimumOne() {
        return entities.size != 0;
    }

    public ObjectSet<Pair<AttackCell, IEntity>> getEntities() {
        return entities;
    }

    public void setEntities() {
        entities.clear();
        handle.forEach(optionals -> optionals.forEach(optional -> optional
                .flatMap(AbstractCell::getContentEntity)
                .ifPresent(entity -> entities.get(Pair.of(optional.get(), entity)))));
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(Renderer renderer) {
        for (final var optionals : handle) {
            for (final var optional : optionals) {
                optional.ifPresent(attackCell -> attackCell.render(renderer));
            }
        }
    }
}

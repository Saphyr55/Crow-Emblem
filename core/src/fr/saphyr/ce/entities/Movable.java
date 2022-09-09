package fr.saphyr.ce.entities;

public interface Movable {

    void move(float velocity);

    void whenMoveRight();
    void whenMoveUp();
    void whenMoveBottom();
    void whenMoveLeft();

}

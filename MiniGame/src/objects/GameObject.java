package objects;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

public class GameObject {

    private Polygon object;
    private Point2D movement;

    public GameObject(Polygon objectShape, Color color, int x, int y) {
        this.object = objectShape;
        this.object.setFill(color);
        this.object.setTranslateX(x);
        this.object.setTranslateY(y);
        this.movement = new Point2D(0, 0);
    }

    public Polygon getGameObject() {
        return object;
    }
    
    public void setGameObject(Polygon object) {
        this.object = object;
    }

    public void move(int x, int y) {
        this.object.setTranslateX(this.object.getTranslateX() + x);
        this.object.setTranslateY(this.object.getTranslateY() + y);
    }

    public boolean collide(GameObject other) {
        Shape collision = Shape.intersect(this.object, other.getGameObject());
        return collision.getBoundsInLocal().getWidth() != -1;
    }

}

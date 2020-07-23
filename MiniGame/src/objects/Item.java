package objects;

import javafx.scene.shape.Polygon;

public class Item extends GameObject {

    public Item(int x, int y) {
        super(new Polygon(0, 0, 10, 0, 10, 10, 0, 10), javafx.scene.paint.Color.GREEN, x, y);
    }
}

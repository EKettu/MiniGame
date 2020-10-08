package objects;

import javafx.scene.shape.Polygon;

public class Item extends GameObject {

    public Item(int x, int y) {
        super(new Polygon(0, 0, 20, 0, 20, 20, 0, 20), javafx.scene.paint.Color.GREEN, x, y);
    }
}

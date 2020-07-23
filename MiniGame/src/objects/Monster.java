package objects;

import javafx.scene.shape.Polygon;

public class Monster extends GameObject {
    
    public Monster(int x, int y) {
        super(new Polygon(0, 0, 10, 0, 10, 10, 0, 10),javafx.scene.paint.Color.RED, x, y);
    }
}


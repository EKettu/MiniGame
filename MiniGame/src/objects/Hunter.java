package objects;

import javafx.scene.shape.Polygon;

public class Hunter extends GameObject {

    private Polygon target;

    public Hunter(int x, int y) {
        super(new Polygon(0, 0, 20, 0, 20, 20, 0, 20), javafx.scene.paint.Color.BLUEVIOLET, x, y);
    }

    public Polygon getTarget() {
        return target;
    }

    public void setTarget(Polygon object) {
        this.target = object;
    }

    private double calculateEuclideanDist(double x, double y) {
        if (this.target != null) {
            double distance = Math.sqrt(Math.pow((x - this.target.getTranslateX()), 2)
                    + Math.pow((y - this.target.getTranslateY()), 2));
            return distance;
        }
        return -1;
    }

    public void hunt() {
        double minDistance = Integer.MAX_VALUE;
        double finalX = 0;
        double finalY = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                double distance = calculateEuclideanDist(this.getGameObject().getTranslateX() + i,
                        this.getGameObject().getTranslateY() + j);
                if(distance < minDistance) {
                    minDistance = distance;
                    finalX = i;
                    finalY = j;
                }

            }
        }
        finalX *= 0.35;
        finalY *= 0.35;
        this.move(finalX, finalY);
    }

}

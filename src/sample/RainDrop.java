package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class RainDrop {
    private static final Random random = new Random();

    private final int height;
    private final int width;
    private final double speed;
    private final int zIndex;
    private final Rectangle rectangle;

    public RainDrop(){
        this.height = random.nextInt(11) + 5;
        this.zIndex = random.nextInt(4);

        if (zIndex == 0 || zIndex == 1)
        {
            this.width = random.nextInt(2) + 2;
            this.speed = random.nextDouble() + 0.4;
        }
        else
        {
            this.width = random.nextInt(1) + 1;
            this.speed = random.nextDouble() + 0.05;
        }


        this.rectangle = new Rectangle();
        this.rectangle.setFill(Color.DIMGRAY);
        this.rectangle.setHeight(height);
        this.rectangle.setWidth(width);
    }

    public double getSpeed() {
        return speed;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

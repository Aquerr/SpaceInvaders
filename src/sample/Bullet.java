package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a bullet fired by a player or an enemy.
 */
public class Bullet {
    private Rectangle rectangle;
    private Vector2d position;
    private float velocityY;
    private float velocityX;

    public Bullet(double x, double y, double width, double height, Color color, float velocityY, float velocityX){
        this.position = new Vector2d(x, y);
        this.rectangle = new Rectangle(x, y, width, height);
        this.rectangle.setFill(color);
        this.velocityY = velocityY;
        this.velocityX = velocityX;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
        this.rectangle.setX(this.position.getX());
        this.rectangle.setY(this.position.getY());
    }
}

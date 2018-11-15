package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The @<code>Player</code> class represents player's ship and
 * all functionality like shooting and moving.
 */
public class Player {
    private Rectangle rectangle;
    private Vector2d position;
    private boolean moveLeft;
    private boolean moveRight;
    private float velocityX;
    private int health = 20;

    public Player(double x, double y, double width, double height){
        this.position = new Vector2d(x, y);
        this.rectangle = new Rectangle(this.position.getX(), this.position.getY(), width, height);
        this.rectangle.setFill(Color.WHITE);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
        this.rectangle.setX(this.position.getX());
        this.rectangle.setY(this.position.getY());
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMovingLeft(boolean isMoving){
        this.moveLeft = isMoving;
        if (isMoving)
            this.velocityX = -4.0f;
        else
            this.velocityX = 0;
//        this.position.setX(this.position.getX() - 4);
//        this.rectangle.setX(this.position.getX() - 4);
    }

    public void setMovingRight(boolean isMoving){
        this.moveRight = isMoving;
        if (isMoving)
            this.velocityX = 4.0f;
        else
            this.velocityX = 0;
//        this.position.setX(this.position.getX() + 4);
//        this.rectangle.setX(this.position.getX() + 4);
    }

    public Bullet shoot(){
        return new Bullet(this.position.getX() + this.rectangle.getWidth() / 2, this.position.getY(), 2, 4, Color.YELLOW, -4.0f, 0);
    }

    public float getVelocityX() {
        return velocityX;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public Vector2d getCenter(){
        double x = this.position.getX() + this.rectangle.getWidth() / 2;
        double y = this.position.getY() + this.rectangle.getHeight() / 2;
        return new Vector2d(x, y);
    }
}

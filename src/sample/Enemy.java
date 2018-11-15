package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy {
    private Rectangle rectangle;
    private Vector2d position;
    private boolean moveLeft;
    private boolean moveRight;
    private float velocityX = 0.0f;
    private int health = 20;
    private float shootCooldown = 20.0f;

    public Enemy(double x, double y, double width, double height){
        this.position = new Vector2d(x, y);
        this.rectangle = new Rectangle(this.position.getX(), this.position.getY(), width, height);
        this.rectangle.setFill(Color.GREEN);
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

    public Bullet shoot(float velocityX, float velocityY){
        return new Bullet(this.position.getX() + this.rectangle.getWidth() / 2, this.position.getY() + this.rectangle.getHeight(), 2, 4, Color.LIME, velocityY, velocityX);
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getShootCooldown() {
        return shootCooldown;
    }

    public void setShootCooldown(float shootCooldown) {
        this.shootCooldown = shootCooldown;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Vector2d getCenter(){
        double x = this.position.getX() + this.rectangle.getWidth() / 2;
        double y = this.position.getY() + this.rectangle.getHeight() / 2;
        return new Vector2d(x, y);
    }
}

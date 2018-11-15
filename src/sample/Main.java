package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

//    private Thread gameThread = new Thread(this::update);
    private Pane pane;
    private Player player;
    private Label playerHealthLabel;

    private List<Enemy> enemyList = new ArrayList<>();
    private Pane enemyGroup;
    private List<Bullet> bulletList = new ArrayList<>();

    private Random random = new Random();
    private float enemiesVelocityX = 1.0f;

    private final List<RainDrop> rainDropList = new ArrayList<>();
    //private final Thread rainThread = new Thread(this::runRainThread);


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.pane = new Pane();
        this.pane.setMinWidth(800);
        this.pane.setMinHeight(600);
        this.pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        primaryStage.setTitle("Space Invaders");

        Scene scene = new Scene(this.pane);
        setupKeyEvents(scene);
        primaryStage.setScene(scene);
        primaryStage.show();

        //Start game "update" thread
        setupPlayer();
        setupEnemies();
        setupAmbience();
        update();
//        gameThread.start();
    }

    public void setupAmbience(){
        for (int i = 0; i < 150; i++) {
            spawnRainDrop();
        }

     //   rainThread.start();
    }

//    public void runRainThread(){
//        while (true)
//        {
//            try {
//                Thread.sleep(2);
//                updateAmbience();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private void spawnRainDrop(){
        RainDrop rainDrop = new RainDrop();
        Rectangle rectangle = rainDrop.getRectangle();
        double height = pane.getHeight();
        double width = pane.getWidth();

        rectangle.setX(random.nextDouble() * pane.getWidth());
        rectangle.setY(random.nextDouble() * pane.getHeight());

        this.rainDropList.add(rainDrop);
        this.pane.getChildren().add(rectangle);
    }

    private void setupKeyEvents(Scene scene){
        scene.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    if (event.getCode() == KeyCode.LEFT){
                        player.setMovingLeft(true);
                    }
                    if (event.getCode() == KeyCode.RIGHT){
                        player.setMovingRight(true);
                    }
                    if (event.getCode() == KeyCode.SPACE){
                        Bullet bullet = player.shoot();
                        synchronized (bulletList)
                        {
                            bulletList.add(bullet);
                        }
                        pane.getChildren().add(bullet.getRectangle());
                    }
                }
                else if (event.getEventType() == KeyEvent.KEY_RELEASED){
                    if (event.getCode() == KeyCode.LEFT){
                        player.setMovingLeft(false);
                    }
                    if (event.getCode() == KeyCode.RIGHT){
                        player.setMovingRight(false);
                    }
                }
            }
        });
    }

    public void setupPlayer(){
        this.player = new Player(350, 550, 60, 30);
        this.pane.getChildren().add(player.getRectangle());

        //Setup player health label
        playerHealthLabel = new Label();
        pane.getChildren().add(playerHealthLabel);
        playerHealthLabel.setTranslateX(10);
        playerHealthLabel.setTranslateY(550);
        playerHealthLabel.setText("Health: " + String.valueOf(player.getHealth()));
        playerHealthLabel.setTextFill(Color.WHITE);
    }

    private void setupEnemies() {
        this.enemyGroup = new Pane();
        spawnEnemies(6);
        this.pane.getChildren().add(enemyGroup);
    }

    private void spawnEnemies(int count){
        float nextPosition = 0;
        for (int i = 0; i < count; i++) {
            Enemy enemy = new Enemy(nextPosition, 50, 60, 30);
            this.enemyList.add(enemy);
            this.enemyGroup.getChildren().add(enemy.getRectangle());
//            this.pane.getChildren().add(enemy.getRectangle());
            nextPosition += 75;
        }
    }

    public void update(){
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                updateAmbience();

                //Update bullets
                for (int i = 0; i < bulletList.size(); i++) {
                    Bullet bullet = bulletList.get(i);
                    Vector2d oldPosition = bullet.getPosition();
                    oldPosition.setY(oldPosition.getY() + bullet.getVelocityY());
                    oldPosition.setX(oldPosition.getX() + bullet.getVelocityX());
                    bullet.setPosition(oldPosition);
//                    bullet.getRectangle().setY(oldPosition.getY());
//                    bullet.getRectangle().setX(oldPosition.getX());

                    //Check if outside the window
                    if (bullet.getPosition().getY() > pane.getHeight() || bullet.getPosition().getY() < 0){
                        pane.getChildren().remove(bullet.getRectangle());
                        bulletList.remove(i);
                        i--;
                    }

                    //Check if intersects with an enemy
                    for (int j = 0; j < enemyList.size(); j++) {
                        Enemy enemy = enemyList.get(j);
                        if (bullet.getPosition().getY() > enemy.getPosition().getY()
                                && bullet.getPosition().getY() < enemy.getPosition().getY() + enemy.getRectangle().getHeight()
                                && bullet.getPosition().getX() > enemy.getPosition().getX()
                                && bullet.getPosition().getX() < enemy.getPosition().getX() + enemy.getRectangle().getWidth()){
                            //Enemy hit
                            bulletList.remove(i);
                            pane.getChildren().remove(bullet.getRectangle());
                            enemyList.remove(enemy);
                            enemyGroup.getChildren().remove(enemy.getRectangle());
                            i--;
                        }
                    }

                    if (player.isAlive() && bullet.getPosition().getY() > player.getPosition().getY()
                            && bullet.getPosition().getY() < player.getPosition().getY() + player.getRectangle().getHeight()
                            && bullet.getPosition().getX() > player.getPosition().getX()
                            && bullet.getPosition().getX() < player.getPosition().getX() + player.getRectangle().getWidth()){
                            bulletList.remove(i);
                            pane.getChildren().remove(bullet.getRectangle());
                            player.setHealth(player.getHealth() - 1);
                            playerHealthLabel.setText("Health: " + String.valueOf(player.getHealth()));
                            if (player.getHealth() == 0)
                                pane.getChildren().remove(player.getRectangle());
                            i--;
                    }
                }

                //Update player
                if (player.isAlive())
                {
                    Vector2d vector2d = player.getPosition();
                    vector2d.setX(vector2d.getX() + player.getVelocityX());
                    player.setPosition(vector2d);
                }

                //Update enemies
                for (Enemy enemy : enemyList) {
                    Vector2d position = enemy.getPosition();
                    position.setX(position.getX() + enemiesVelocityX);

                    //Shoot
                    if (enemy.getShootCooldown() == 0)
                    {
                        if (random.nextBoolean()){
                            float velocityX = (float) (player.getCenter().getX() - enemy.getPosition().getX()) / 100;
//                            float velocityY = (float) (player.getCenter().getY() - enemy.getCenter().getY()) / 100;
                            float velocityY = 4.0f;
//                            float velocityX = player.getPosition().getX() < enemy.getPosition().getX() ? -1.0f : 1.0f;
                            Bullet bullet = enemy.shoot(velocityX, velocityY);
                            enemy.setShootCooldown(random.nextFloat() * 30);
                            bulletList.add(bullet);
                            pane.getChildren().add(bullet.getRectangle());
                        }
                        else
                        {
                            enemy.setShootCooldown(random.nextFloat() * 30);
                        }
                    }
                    else
                    {
                        enemy.setShootCooldown(enemy.getShootCooldown() - 0.2f * 1.2f);
                        if (enemy.getShootCooldown() < 0)
                            enemy.setShootCooldown(0);
                    }
                }
                enemyGroup.setLayoutX(enemyGroup.getLayoutX() + enemiesVelocityX);
                if (enemyGroup.getLayoutX() + enemyGroup.getWidth() >= pane.getWidth())
                    enemiesVelocityX = -1.0f;
                else if (enemyGroup.getLayoutX() <= 0)
                    enemiesVelocityX = 1.0f;

                if (enemyList.size() == 0){
                    spawnEnemies(6);
                }
            }
        };

        animationTimer.start();

//        while (true) {
//            try {
//                Thread.sleep(10);
//
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        //Update bullets
//                        for (int i = 0; i < bulletList.size(); i++) {
//                            Bullet bullet = bulletList.get(i);
//                            Vector2d oldPosition = bullet.getPosition();
//                            oldPosition.setY(oldPosition.getY() + bullet.getVelocityY());
//                            bullet.setPosition(oldPosition);
//                            bullet.getRectangle().setY(oldPosition.getY());
//
//                            if (bullet.getPosition().getY() > pane.getHeight() || bullet.getPosition().getY() < 0){
//                                pane.getChildren().remove(bullet.getRectangle());
//                                bulletList.remove(i);
//                                i--;
//                            }
//                        }
//
//                        //Update player movement
//
//                        //Update enemies
//                    }
//                });
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
    }

    private void updateAmbience() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                for (RainDrop rainDrop : rainDropList)
                {
                    Rectangle rectangle = rainDrop.getRectangle();
                    if (rectangle.getY() > pane.getHeight())
                    {
                        rectangle.setX(random.nextDouble() * pane.getWidth());
                        rectangle.setY(0);
                        continue;
                    }
                    rectangle.setY(rectangle.getY() + rainDrop.getSpeed());
                    rectangle.setY(rectangle.getY() + rainDrop.getSpeed());
                }
            }
        });
    }
}

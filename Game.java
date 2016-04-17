package game;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game {
  public static ArrayList<Block> platforms = new ArrayList<>();
  private HashMap<KeyCode, Boolean> keys = new HashMap<>();

  public static final int BLOCK_SIZE = 30;
  public static final int TANK_SIZE = 25;

  public static Pane appRoot = new Pane();
  public static Pane gameRoot = new Pane();

  public Character player;
  public Enemy autoPlayer;
  public Spawner spawn = new Spawner();

  private int newBlock;
  public int spawnSpeed;

  private Label ScoreLabel;
  private Label HealthLabel;

  public int gameScore = 0;

  public ArrayList<Enemy> enemies;
  public ArrayList<Shell> shells;
  private AnimationTimer timer;

  Image tankImg = new Image(getClass().getResourceAsStream("GAME OVER.png"));
  ImageView imageView = new ImageView(tankImg);

  /**
   * random map and GUI
   */
  private void initContent() {
    Rectangle bg = new Rectangle(1020, 750, Color.BLACK);
    HealthLabel = new Label("Health: 100");
    ScoreLabel = new Label("Score: 0");
    Font font = new Font(25);
    ScoreLabel.setFont(font);
    HealthLabel.setFont(font);
    ScoreLabel.setTextFill(Color.RED);
    HealthLabel.setTextFill(Color.RED);
    ScoreLabel.setTranslateY(25);
    gameRoot.getChildren().addAll(bg, HealthLabel, ScoreLabel);
    for (int i = 0; i < Map.level.length; i++) {
      String line = Map.level[i];
      for (int j = 0; j < line.length(); j++) {
        if (line.charAt(j) == 'B') {
          new Block(Block.BlockType.WALL, j * BLOCK_SIZE, i * BLOCK_SIZE);
        } else if (line.charAt(j) == 'R') {
          randBlock();
          switch (newBlock) {
            case 1:
              new Block(Block.BlockType.WATER, j * BLOCK_SIZE, i * BLOCK_SIZE);
              break;
            case 2:
              new Block(Block.BlockType.WALL, j * BLOCK_SIZE, i * BLOCK_SIZE);
              break;
            case 3:
              new Block(Block.BlockType.BRICK, j * BLOCK_SIZE, i * BLOCK_SIZE);
              break;
            case 4:
              new Block(Block.BlockType.GRASS, j * BLOCK_SIZE, i * BLOCK_SIZE);
              break;
            case 5:
              new Block(Block.BlockType.BRICK, j * BLOCK_SIZE, i * BLOCK_SIZE);
              break;
            case 6:
              new Block(Block.BlockType.GRASS, j * BLOCK_SIZE, i * BLOCK_SIZE);
              break;
            case 7:
              new Block(Block.BlockType.WATER, j * BLOCK_SIZE, i * BLOCK_SIZE);
              break;
          }
        }
      }

    }
    Music.chooseMusic(1);
    appRoot.getChildren().addAll(gameRoot);
  }

  private void randBlock() {
    newBlock = (int) ((Math.random() * 1000) % 15);
  }

  /*
   * 1 - normal mode 
   * else - autoplay
   */
  private void update(int mode) {
    if (mode == 1) {
      Character.setCooldown();
      if (isPressed(KeyCode.UP)) {
        player.setScaleX(1);
        player.animation.play();
        player.moveY(-3);
        player.setRotate(270);
        Character.setCurrentAxis(1);
        Music.activateEngineSound(true);
      }
      else if (isPressed(KeyCode.DOWN)) {
        player.setScaleX(1);
        player.animation.play();
        player.moveY(3);
        player.setRotate(90);
        Character.setCurrentAxis(2);
        Music.activateEngineSound(true);
      }
      else if (isPressed(KeyCode.LEFT)) {
        player.setScaleX(-1);
        player.animation.play();
        player.moveX(-3);
        player.setRotate(0);
        Character.setCurrentAxis(3);
        Music.activateEngineSound(true);
      }
      else if (isPressed(KeyCode.RIGHT)) {
        player.setScaleX(1);
        player.animation.play();
        player.moveX(3);
        player.setRotate(0);
        Character.setCurrentAxis(4);
        Music.activateEngineSound(true);
      }
      if (isPressed(KeyCode.SPACE) && Character.getCooldown() <= 0) {
        Character.cooldown = 100;
        shells.add(new Shell(Character.shotAxis, player.getTranslateX(),
            player.getTranslateY(), "Player"));
        gameRoot.getChildren().add(shells.get(shells.size() - 1));
        Music.shot();
      }
      else if (isPressed(KeyCode.ESCAPE)) {
        // pause();
      }
    } else {
      autoPlayer.randomMove();
      if (autoPlayer.getDy() == -1) {
        autoPlayer.setScaleX(1);
        autoPlayer.animation.play();
        autoPlayer.moveY(-3);
        autoPlayer.setRotate(270);
      }
      if (autoPlayer.getDy() == 1) {
        autoPlayer.setScaleX(1);
        autoPlayer.animation.play();
        autoPlayer.moveY(3);
        autoPlayer.setRotate(90);
      }
      if (autoPlayer.getDx() == -1) {
        autoPlayer.setScaleX(-1);
        autoPlayer.animation.play();
        autoPlayer.moveX(-3);
        autoPlayer.setRotate(0);
      }
      if (autoPlayer.getDx() == 1) {
        autoPlayer.setScaleX(1);
        autoPlayer.animation.play();
        autoPlayer.moveX(3);
        autoPlayer.setRotate(0);
      }
    }
  }

  private void updateBot() {
    for (int i = 0; i < enemies.size(); i++) {
      enemies.get(i).randomMove();
      if (enemies.get(i).setShootTimer() == 1) {
        shells.add(new Shell(enemies.get(i).getShotAxis(),
            enemies.get(i).getTranslateX(), enemies.get(i).getTranslateY(),
            "Enemy"));
        gameRoot.getChildren().add(shells.get(shells.size() - 1));
      }
      if (enemies.get(i).getDy() == -1) {
        enemies.get(i).setScaleX(1);
        enemies.get(i).animation.play();
        enemies.get(i).moveY(-3);
        enemies.get(i).setRotate(270);
      }
      if (enemies.get(i).getDy() == 1) {
        enemies.get(i).setScaleX(1);
        enemies.get(i).animation.play();
        enemies.get(i).moveY(3);
        enemies.get(i).setRotate(90);
      }
      if (enemies.get(i).getDx() == -1) {
        enemies.get(i).setScaleX(-1);
        enemies.get(i).animation.play();
        enemies.get(i).moveX(-3);
        enemies.get(i).setRotate(0);
      }
      if (enemies.get(i).getDx() == 1) {
        enemies.get(i).setScaleX(1);
        enemies.get(i).animation.play();
        enemies.get(i).moveX(3);
        enemies.get(i).setRotate(0);
      }
    }
  }

  /*
   * move shell and check collision with map
   */
  private void updateShell() {
    for (int i = 0; i < shells.size(); i++) {
      shells.get(i).move();
      for (Node platform : Game.platforms) {
        if (shells.get(i).getBoundsInParent()
            .intersects(platform.getBoundsInParent())) {
          if (shells.get(i).getShotOwner() == "Player") {
            Music.wallHit();
          }
          gameRoot.getChildren().remove(shells.get(i));
          shells.remove(shells.get(i));
          break;
        }
      }
    }
  }

  /*
   * check collision with player/enemies
   */
  private void checkHit() {
    for (int i = 0; i < shells.size(); i++) {
      if (shells.get(i).getShotOwner() == "Player") {
        for (int j = 0; j < enemies.size(); j++) {
          if (shells.get(i).getBoundsInParent()
              .intersects(enemies.get(j).getBoundsInParent())) {
            gameRoot.getChildren().remove(shells.get(i));
            shells.remove(shells.get(i));
            // "1" that means, that health <= 0
            if (enemies.get(j).updateHealth(50) == 1) {
              gameRoot.getChildren().remove(enemies.get(j));
              enemies.remove(enemies.get(j));
              Music.tankExplosion();
              gameScore++;
              ScoreLabel.setText("Score: " + gameScore);
              break;
            }
            Music.tankHit();
            break;
          }
        }
      } else {
        if (shells.get(i).getBoundsInParent()
            .intersects(player.getBoundsInParent())) {
          gameRoot.getChildren().remove(shells.get(i));
          shells.remove(shells.get(i));
          // if true - gameover
          if (player.updateHealth(50) == 1) {
            gameRoot.getChildren().remove(player);
            Music.tankExplosion();
            gameRoot.getChildren().add(imageView);
            timer.stop();
          }
          HealthLabel.setText("Health: " + player.health);
          Music.tankHit();
        }
      }
    }
  }

  private boolean isPressed(KeyCode key) {
    return keys.getOrDefault(key, false);
  }


  public void startGame(Stage primaryStage, int mode) {
    Scene scene = new Scene(appRoot, 1020, 750);
    scene.setFill(Color.BLACK);
    primaryStage.setScene(scene);
    primaryStage.show();
    enemies = new ArrayList<Enemy>();
    shells = new ArrayList<Shell>();
    initContent();
    if (mode == 1) {
      player = new Character();
      enemies.add(new Enemy());
      gameRoot.getChildren().add(player);
      gameRoot.getChildren().add(enemies.get(0));
    } else {
      autoPlayer = new Enemy();
      enemies.add(new Enemy());
      gameRoot.getChildren().add(autoPlayer);
      gameRoot.getChildren().add(enemies.get(0));
    }
    scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
    scene.setOnKeyReleased(event -> {
      keys.put(event.getCode(), false);
      player.animation.stop();
    });
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        update(mode);
        updateBot();
        updateShell();
        checkHit();
        if (enemies.size() <= 3) {
          // random bot spawn. depends on spawnSpeed
          if ((spawn.addNewEnemy()) == true) {
            enemies.add(new Enemy());
            gameRoot.getChildren().add(enemies.get(enemies.size() - 1));
          }
        }
        //if player movement == 0
        if (Character.getCurrentAxis() == 0)
          Music.activateEngineSound(false);
        Character.setCurrentAxis(0);
      }
    };
    timer.start();

  }

  /*
   * public void pause() { timer.stop(); // menu MenuItem cont = new MenuItem("опнднкфхрэ");
   * MenuItem exitGame = new MenuItem("бшунд"); SubMenu pauseMenu = new SubMenu(cont, exitGame);
   * cont.setOnMouseClicked(event -> { getChildren().remove(pauseMenu); timer.start(); });
   * exitGame.setOnMouseClicked(event -> { System.exit(0); }); getChildren().add(pauseMenu); }
   */
}


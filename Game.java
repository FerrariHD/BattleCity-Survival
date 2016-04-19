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

  Main main = new Main();
  
  public static final int BLOCK_SIZE = 30;
  public static final int TANK_SIZE = 25;

  public static Pane appRoot = new Pane();
  public static Pane gameRoot = new Pane();

  public Character player;
  public Enemy autoPlayer;
  public Spawner spawn = new Spawner();

  public int maxAmountOfBots = 3;

  public int damage = 50;

  public int movementSpeed = 3;

  private int newBlock;
  public static int spawnSpeed = 200;

  private Label ScoreLabel;
  private Label HealthLabel;

  public static String gameMode;

  public int gameScore = 0;

  public ArrayList<Enemy> enemies;
  public ArrayList<Shell> shells;
  private AnimationTimer timer;

  Image gameOverImg =
      new Image(getClass().getResourceAsStream("GAME OVER.png"));
  ImageView gameOverView = new ImageView(gameOverImg);

  Rectangle pauseBG = new Rectangle(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, Color.BLACK);

  /**
   * random map and GUI
   */
  private void initContent() {
    Rectangle bg = new Rectangle(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, Color.BLACK);
    pauseBG.setOpacity(0.5);
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
   * 
   */
  private void update() {
    if (gameMode == "Normal") {
      Character.setCooldown();
      if (isPressed(KeyCode.UP)) {
        player.setScaleX(1);
        player.animation.play();
        player.moveY(-movementSpeed);
        player.setRotate(270);
        Character.setCurrentAxis(1);
        Music.activateEngineSound(true);
      } else if (isPressed(KeyCode.DOWN)) {
        player.setScaleX(1);
        player.animation.play();
        player.moveY(movementSpeed);
        player.setRotate(90);
        Character.setCurrentAxis(2);
        Music.activateEngineSound(true);
      } else if (isPressed(KeyCode.LEFT)) {
        player.setScaleX(-1);
        player.animation.play();
        player.moveX(-movementSpeed);
        player.setRotate(0);
        Character.setCurrentAxis(3);
        Music.activateEngineSound(true);
      } else if (isPressed(KeyCode.RIGHT)) {
        player.setScaleX(1);
        player.animation.play();
        player.moveX(movementSpeed);
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
      } else if (isPressed(KeyCode.ESCAPE)) {
        pause();
      }
    } else {
      autoPlayer.randomMove();
      if (autoPlayer.getDy() == -1) {
        autoPlayer.setScaleX(1);
        autoPlayer.animation.play();
        autoPlayer.moveY(-movementSpeed);
        autoPlayer.setRotate(270);
        Music.activateEngineSound(true);
      }
      if (autoPlayer.getDy() == 1) {
        autoPlayer.setScaleX(1);
        autoPlayer.animation.play();
        autoPlayer.moveY(movementSpeed);
        autoPlayer.setRotate(90);
      }
      if (autoPlayer.getDx() == -1) {
        autoPlayer.setScaleX(-1);
        autoPlayer.animation.play();
        autoPlayer.moveX(-movementSpeed);
        autoPlayer.setRotate(0);
      }
      if (autoPlayer.getDx() == 1) {
        autoPlayer.setScaleX(1);
        autoPlayer.animation.play();
        autoPlayer.moveX(movementSpeed);
        autoPlayer.setRotate(0);
      }
      if (autoPlayer.setShootTimer() == 1) {
        shells.add(new Shell(autoPlayer.getShotAxis(),
            autoPlayer.getTranslateX(), autoPlayer.getTranslateY(), "Player"));
        gameRoot.getChildren().add(shells.get(shells.size() - 1));
        Music.shot();
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
        enemies.get(i).moveY(-movementSpeed);
        enemies.get(i).setRotate(270);
      }
      if (enemies.get(i).getDy() == 1) {
        enemies.get(i).setScaleX(1);
        enemies.get(i).animation.play();
        enemies.get(i).moveY(movementSpeed);
        enemies.get(i).setRotate(90);
      }
      if (enemies.get(i).getDx() == -1) {
        enemies.get(i).setScaleX(-1);
        enemies.get(i).animation.play();
        enemies.get(i).moveX(-movementSpeed);
        enemies.get(i).setRotate(0);
      }
      if (enemies.get(i).getDx() == 1) {
        enemies.get(i).setScaleX(1);
        enemies.get(i).animation.play();
        enemies.get(i).moveX(movementSpeed);
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
            // "1" - that means, that health <= 0
            if (enemies.get(j).updateHealth(damage) == 1) {
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
        if (gameMode == "Normal") {
          if (shells.get(i).getBoundsInParent()
              .intersects(player.getBoundsInParent())) {
            gameRoot.getChildren().remove(shells.get(i));
            shells.remove(shells.get(i));
            // if true - gameover
            if (player.updateHealth(damage) == 1) {
              gameRoot.getChildren().remove(player);
              Music.tankExplosion();
              gameOver();
            }
            HealthLabel.setText("Health: " + player.health);
            Music.tankHit();
          }
        } else if (gameMode == "Auto") {
          if (shells.get(i).getBoundsInParent()
              .intersects(autoPlayer.getBoundsInParent())) {
            gameRoot.getChildren().remove(shells.get(i));
            shells.remove(shells.get(i));
            // if true - gameover
            if (autoPlayer.updateHealth(damage) == 1) {
              gameRoot.getChildren().remove(autoPlayer);
              Music.tankExplosion();
              gameOver();
            }
            HealthLabel.setText("Health: " + autoPlayer.health);
            Music.tankHit();
          }
        }
      }
    }
  }

  private boolean isPressed(KeyCode key) {
    return keys.getOrDefault(key, false);
  }


  public void startGame(Stage primaryStage) {
    Scene scene = new Scene(appRoot, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
    scene.setFill(Color.BLACK);
    primaryStage.setScene(scene);
    enemies = new ArrayList<Enemy>();
    shells = new ArrayList<Shell>();
    initContent();
    if (gameMode == "Normal") {
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
        update();
        updateBot();
        updateShell();
        checkHit();
        if (enemies.size() < maxAmountOfBots) {
          // random bot spawn. depends on spawnSpeed
          if ((spawn.addNewEnemy()) == true) {
            enemies.add(new Enemy());
            gameRoot.getChildren().add(enemies.get(enemies.size() - 1));
          }
        }
        // if player movement == 0
        if (Character.getCurrentAxis() == 0)
          Music.activateEngineSound(false);
        Character.setCurrentAxis(0);
      }
    };
    timer.start();

  }

  public void pause() {
    timer.stop();
    Music.activateEngineSound(false);
    MenuItem cont = new MenuItem("����������");
    MenuItem mainMenu = new MenuItem("������� ����");
    MenuItem exitGame = new MenuItem("�����");
    SubMenu pauseMenu = new SubMenu(cont, mainMenu, exitGame);
    cont.setOnMouseClicked(event -> {
      gameRoot.getChildren().removeAll(pauseBG, pauseMenu);
      timer.start();
    });
    mainMenu.setOnMouseClicked(event -> {
      //
    });
    exitGame.setOnMouseClicked(event -> {
      System.exit(0);
    });
    gameRoot.getChildren().addAll(pauseBG, pauseMenu);
  }

  public void gameOver() {
    timer.stop();
    Music.activateEngineSound(false);
    MenuItem mainMenu = new MenuItem("������� ����");
    MenuItem exitGame = new MenuItem("�����");
    SubMenu gameOverMenu = new SubMenu(mainMenu, exitGame);
    mainMenu.setOnMouseClicked(event -> {
      //
    });
    exitGame.setOnMouseClicked(event -> {
      System.exit(0);
    });
    gameRoot.getChildren().addAll(gameOverView, gameOverMenu);
  }
  
}


package game;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import notate.Notation;

/**
 * Main class
 * 
 * @author FerrariHD
 *
 */
public class Main extends Application {

  public static int SCREEN_WIDTH = 1020;
  public static int SCREEN_HEIGHT = 750;

  Music music = new Music();

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Main menu
   */
  @Override
  public void start(Stage primaryStage) {
    /**
     * load all sounds
     */
    music.initSound();
    /**
     * choose mainMenuMusic
     */
    Music.chooseMusic(0);
    Pane root = new Pane();
    Game game = new Game();

    /**
     * MainMenuBackground (video)
     */
    Media media =
        new Media(getClass().getResource("MainMenuBackground.mp4").toString());
    MediaPlayer particles = new MediaPlayer(media);
    particles.setCycleCount(MediaPlayer.INDEFINITE);
    MediaView view = new MediaView(particles);
    root.getChildren().add(view);
    particles.play();

    MenuItem newGame = new MenuItem("NEW GAME");
    MenuItem replay = new MenuItem("REPLAY");
    MenuItem statistics = new MenuItem("STATISTICS");
    MenuItem options = new MenuItem("SETTINGS");
    MenuItem exitGame = new MenuItem("EXIT");
    SubMenu mainMenu =
        new SubMenu(newGame, replay, statistics, options, exitGame);
    MenuItem replayStart = new MenuItem("PLAY");
    MenuItem replayJavaSort = new MenuItem("JAVA SORT");
    MenuItem replayScalaSort = new MenuItem("SCALA SORT");
    MenuItem replayTest = new MenuItem("SORT TEST");
    MenuItem replayBack = new MenuItem("BACK");
    MenuItem showNotation = new MenuItem("SHOW NOTATION");
    SubMenu replayMenu = new SubMenu(replayStart, replayJavaSort,
        replayScalaSort, replayTest, showNotation, replayBack);
    MenuItem difficult = new MenuItem("DIFFICULTY");
    MenuItem optionsBack = new MenuItem("BACK");
    SubMenu optionsMenu = new SubMenu(difficult, optionsBack);
    MenuItem NG1 = new MenuItem("ONE PLAYER");
    MenuItem NG2 = new MenuItem("BOT");
    MenuItem NGBack = new MenuItem("BACK");
    SubMenu newGameMenu = new SubMenu(NG1, NG2, NGBack);
    MenuItem dif1 = new MenuItem("EASY");
    MenuItem dif2 = new MenuItem("HARD");
    MenuItem difBack = new MenuItem("BACK");
    SubMenu dif = new SubMenu(dif1, dif2, difBack);

    Notation notate = new Notation();
    ReplaysController results = new ReplaysController();
    FileList fileList = new FileList(ReplaysController.folderContent());

    MenuBox menuBox = new MenuBox(mainMenu);

    newGame.setOnMouseClicked(event -> {
      menuBox.setSubMenu(newGameMenu);
      Music.enterSound.play();
    });
    NG1.setOnMouseClicked(event -> {
      root.getChildren().clear();
      Game.gameMode = "Normal";
      game.startGame(primaryStage, results);
    });
    NG2.setOnMouseClicked(event -> {
      root.getChildren().clear();
      Game.gameMode = "Auto";
      game.startGame(primaryStage, results);
    });
    options.setOnMouseClicked(event -> {
      menuBox.setSubMenu(optionsMenu);
      Music.enterSound.play();
    });
    exitGame.setOnMouseClicked(event -> System.exit(0));
    optionsBack.setOnMouseClicked(event -> {
      menuBox.setSubMenu(mainMenu);
      Music.returnSound.play();
    });
    NGBack.setOnMouseClicked(event -> {
      menuBox.setSubMenu(mainMenu);
      Music.returnSound.play();
    });
    replay.setOnMouseClicked(event -> {
      fileList.setFiles(ReplaysController.folderContent());
      menuBox.setSubMenu(replayMenu);
      menuBox.setItem(fileList);
      Music.enterSound.play();
    });
    replayStart.setOnMouseClicked(event -> {
      if (fileList.getSelectionModel().isEmpty()) {
        return;
      } else {
        String fileName = fileList.getSelectionModel().getSelectedItem();
        results.openResultFile(fileName);
        root.getChildren().clear();
        Game.gameMode = "Replay";
        game.startGame(primaryStage, results);
      }
    });
    replayJavaSort.setOnMouseClicked(event -> {
      fileList.javaSort();
    });
    replayScalaSort.setOnMouseClicked(event -> {
      fileList.scalaSort();
    });
    replayTest.setOnMouseClicked(event -> {
      fileList.sortTests();
    });
    showNotation.setOnMouseClicked(event ->{
      if (fileList.getSelectionModel().isEmpty()) {
        return;
      } else {
        String fileName = fileList.getSelectionModel().getSelectedItem();
        notate.notationShow(fileName);
      }
    });
    replayBack.setOnMouseClicked(event -> {
      menuBox.getChildren().remove(fileList);
      menuBox.setSubMenu(mainMenu);
      Music.returnSound.play();
    });
    statistics.setOnMouseClicked(event -> {
      StatisticsArea statisticsArea = new StatisticsArea();
      statisticsArea.show();
    });
    difficult.setOnMouseClicked(event -> {
      menuBox.setSubMenu(dif);
      Music.enterSound.play();
    });
    dif1.setOnMouseClicked(event -> {
      game.spawnSpeed = 200;
      Music.enterSound.play();
    });
    dif2.setOnMouseClicked(event -> {
      game.spawnSpeed = 100;
      Music.enterSound.play();
    });
    difBack.setOnMouseClicked(event -> {
      menuBox.setSubMenu(optionsMenu);
      Music.returnSound.play();
    });
    root.getChildren().addAll(menuBox);

    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

    primaryStage.setTitle("BattleCity Survival");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}


package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }
/**
 * Main menu
 */
  @Override
  public void start(Stage primaryStage) {
    Music.initSound();
    Music.chooseMusic(0);
    Pane root = new Pane();
    Game game = new Game();

    Image imageBackGround = new Image(
        getClass().getResourceAsStream("wot5years_1024x768_noc_ru.jpg"));
    ImageView imgBackGround = new ImageView(imageBackGround);
    imgBackGround.setFitHeight(768);
    imgBackGround.setFitWidth(1024);
    Image imageRectangle = new Image(getClass().getResourceAsStream("box.png"));
    ImageView imgRectangle = new ImageView(imageRectangle);
    imgRectangle.setFitHeight(1020);
    imgRectangle.setFitWidth(320);
    imgRectangle.setLayoutX(90);
    Image imageLogo =
        new Image(getClass().getResourceAsStream("MainMenuLogo.png"));
    ImageView imgLogo = new ImageView(imageLogo);
    imgLogo.setFitHeight(150);
    imgLogo.setFitWidth(300);
    imgLogo.setLayoutX(100.5);
    imgLogo.setLayoutY(50);
    root.getChildren().add(imgBackGround);
    root.getChildren().add(imgRectangle);
    root.getChildren().add(imgLogo);

    MenuItem newGame = new MenuItem("НОВАЯ ИГРА");
    MenuItem keys = new MenuItem("УПРАВЛЕНИЕ");
    MenuItem about = new MenuItem("О ПРОГРАММЕ");
    MenuItem options = new MenuItem("НАСТРОЙКИ");
    MenuItem exitGame = new MenuItem("ВЫХОД");
    SubMenu mainMenu = new SubMenu(newGame, keys, about, options, exitGame);
    MenuItem sound = new MenuItem("ЗВУК");
    MenuItem difficult = new MenuItem("СЛОЖНОСТЬ");
    MenuItem optionsBack = new MenuItem("НАЗАД");
    SubMenu optionsMenu = new SubMenu(sound, difficult, optionsBack);
    MenuItem NG1 = new MenuItem("ОДИН ИГРОК");
    MenuItem NG2 = new MenuItem("БОТ");
    MenuItem NGBack = new MenuItem("НАЗАД");
    SubMenu newGameMenu = new SubMenu(NG1, NG2, NGBack);
    MenuItem volume1 = new MenuItem("100%");
    MenuItem volume2 = new MenuItem("75%");
    MenuItem volume3 = new MenuItem("50%");
    MenuItem VBack = new MenuItem("НАЗАД");
    SubMenu volume = new SubMenu(volume1, volume2, volume3, VBack);
    MenuItem dif1 = new MenuItem("ЛЕГКО");
    MenuItem dif2 = new MenuItem("СЛОЖНО");
    MenuItem difBack = new MenuItem("НАЗАД");
    SubMenu dif = new SubMenu(dif1, dif2, difBack);

    MenuBox menuBox = new MenuBox(mainMenu);

    newGame.setOnMouseClicked(event -> menuBox.setSubMenu(newGameMenu));
    newGame.setOnMousePressed(event -> Music.chooseMusic(2));
    NG1.setOnMouseClicked(event -> {
      root.getChildren().clear();
      game.startGame(primaryStage, 1);
    });
    NG2.setOnMouseClicked(event -> {
      root.getChildren().clear();
      game.startGame(primaryStage, 2);
    });
    options.setOnMouseClicked(event -> menuBox.setSubMenu(optionsMenu));
    options.setOnMousePressed(event -> Music.chooseMusic(2));
    exitGame.setOnMouseClicked(event -> System.exit(0));
    optionsBack.setOnMouseClicked(event -> menuBox.setSubMenu(mainMenu));
    optionsBack.setOnMousePressed(event -> Music.chooseMusic(2));
    NGBack.setOnMouseClicked(event -> menuBox.setSubMenu(mainMenu));
    NGBack.setOnMousePressed(event -> Music.chooseMusic(2));
    sound.setOnMouseClicked(event -> menuBox.setSubMenu(volume));
    sound.setOnMousePressed(event -> Music.chooseMusic(2));
    VBack.setOnMouseClicked(event -> menuBox.setSubMenu(optionsMenu));
    VBack.setOnMousePressed(event -> Music.chooseMusic(2));
    difficult.setOnMouseClicked(event -> menuBox.setSubMenu(dif));
    difficult.setOnMousePressed(event -> Music.chooseMusic(2));
    dif1.setOnMouseClicked(event -> game.spawnSpeed = 200);
    dif1.setOnMousePressed(event -> Music.chooseMusic(2));
    dif2.setOnMouseClicked(event -> game.spawnSpeed = 100);
    dif2.setOnMousePressed(event -> Music.chooseMusic(2));
    difBack.setOnMouseClicked(event -> menuBox.setSubMenu(optionsMenu));
    difBack.setOnMousePressed(event -> Music.chooseMusic(2));
    root.getChildren().addAll(menuBox);

    Scene scene = new Scene(root, 1020, 750);

    primaryStage.setTitle("BattleCity Survival");
    primaryStage.setScene(scene);
    primaryStage.show();

  }
}

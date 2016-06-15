package game;

import game.ReplaysController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import game.GameInfo;
import stats.*;

/**
 * Implement statistics area Represents as Pane that set on main Stage
 */
public class StatisticsArea extends Pane {

  public Statistics scalaStatistics = new Statistics();

  /**
   * Calculate statistics about games, used Scala class Statistics. And translate elements to
   * calculated coordinates
   */
  public void show() {
    String[] files = ReplaysController.folderContent();
    GameInfo[] gameInfo = new GameInfo[files.length];
    for (int i = 0; i < files.length; i++) {
      gameInfo[i] = new GameInfo(files[i]);
    }
    
    scalaStatistics.collectInformations(gameInfo);

    infoWindow();
  }

  private final int INFO_WINDOW_WIDTH = 650;
  private final int INFO_WINDOW_HEIGHT = 200;

  /**
   * Drawing information window
   */
  private void infoWindow() {
    Stage window = new Stage();
    Pane pane = new Pane();
    Rectangle background = new Rectangle(INFO_WINDOW_WIDTH, INFO_WINDOW_HEIGHT, Color.OLDLACE);
    Label text = new Label("Количество движений налево: " + scalaStatistics.left()
    + "\nКоличество движений направо: " + scalaStatistics.right()
    + "\nКоличество движений вверх: " + scalaStatistics.up()
    + "\nКоличество движений вниз: " + scalaStatistics.down()
    + "\nКоличество добавленных ботов: " + scalaStatistics.bot()
    + "\nКоличество выстрелов главного игрока: " + scalaStatistics.shot());
    Font font = new Font(16);
    text.setFont(font);
    pane.getChildren().addAll(background, text);
    Scene scene = new Scene(pane, INFO_WINDOW_WIDTH, INFO_WINDOW_HEIGHT);
    window.setScene(scene);
    window.setTitle("GameStats");
    window.showAndWait();
  }
}
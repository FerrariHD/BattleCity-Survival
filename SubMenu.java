package game;

import javafx.scene.layout.VBox;

public class SubMenu extends VBox {
  public SubMenu(MenuItem... items) {
    setSpacing(24);
    setTranslateY(236);
    setTranslateX(110);
    for (MenuItem item : items) {
      getChildren().addAll(item);
    }
  }
}

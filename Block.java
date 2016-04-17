package game;


import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Block extends Pane {
  Image blocksImg = new Image(getClass()
      .getResourceAsStream("NES_-_Battle_City_-_General_Sprites.png"));
  ImageView block;

  public enum BlockType {
    BRICK, WALL, GRASS, WATER
  }

  public Block(BlockType blockType, int x, int y) {
    block = new ImageView(blocksImg);
    block.setFitWidth(30);
    block.setFitHeight(30);
    setTranslateX(x);
    setTranslateY(y);
    switch (blockType) {
      case WALL:
        block.setViewport(new Rectangle2D(512, 32, 30, 30));
        Game.platforms.add(this);
        break;
      case BRICK:
        block.setViewport(new Rectangle2D(512, 0, 30, 30));
        Game.platforms.add(this);
        break;
      case WATER:
        block.setViewport(new Rectangle2D(512, 64, 30, 30));
        break;
      case GRASS:
        block.setViewport(new Rectangle2D(545, 64, 30, 30));
        break;
    }
    getChildren().add(block);
    Game.gameRoot.getChildren().add(this);
  }
}

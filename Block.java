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
    block.setFitWidth(Game.BLOCK_SIZE);
    block.setFitHeight(Game.BLOCK_SIZE);
    setTranslateX(x);
    setTranslateY(y);
    switch (blockType) {
      case WALL:
        block.setViewport(
            new Rectangle2D(512, 32, Game.BLOCK_SIZE, Game.BLOCK_SIZE));
        Game.platforms.add(this);
        break;
      case BRICK:
        block.setViewport(
            new Rectangle2D(512, 0, Game.BLOCK_SIZE, Game.BLOCK_SIZE));
        Game.platforms.add(this);
        break;
      case WATER:
        block.setViewport(
            new Rectangle2D(512, 64, Game.BLOCK_SIZE, Game.BLOCK_SIZE));
        break;
      case GRASS:
        block.setViewport(
            new Rectangle2D(545, 64, Game.BLOCK_SIZE, Game.BLOCK_SIZE));
        break;
    }
    getChildren().add(block);
    Game.gameRoot.getChildren().add(this);
  }
}

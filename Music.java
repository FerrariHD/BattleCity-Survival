package game;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {

  static MediaPlayer mediaplayer;
  static MediaPlayer mediaplayer2;
  static MediaPlayer engineSound;
  static AudioClip test;
  static AudioClip backSound;
  static AudioClip shot;
  static AudioClip wallhit;
  static AudioClip tankhit;
  static AudioClip tankexplosion;

  public static void initSound() {
    Media gameMusicFile = new Media(
        "file:///C:/Users/FerrariHD/workspace/Test/bin/AnOrangePlanet(loop).wav");
    Media musicMainMenuFile = new Media(
        "file:///C:/Users/FerrariHD/workspace/Test/bin/AnOrangePlanet-Percussion.wav");
    Media engineOneFile =
        new Media("file:///C:/Users/FerrariHD/workspace/Test/bin/engine.wav");

    mediaplayer = new MediaPlayer(musicMainMenuFile);
    mediaplayer.setVolume(0.7);
    mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);

    mediaplayer2 = new MediaPlayer(gameMusicFile);
    mediaplayer2.setVolume(0.7);
    mediaplayer2.setCycleCount(MediaPlayer.INDEFINITE);

    engineSound = new MediaPlayer(engineOneFile);
    engineSound.setVolume(1);
    engineSound.setCycleCount(MediaPlayer.INDEFINITE);

    test = new AudioClip(
        "file:///C:/Users/FerrariHD/workspace/Test/bin/enter.wav");
    test.setVolume(0.3);

    backSound =
        new AudioClip("file:///C:/Users/FerrariHD/workspace/Test/bin/esc.wav");
    backSound.setVolume(0.3);

    shot =
        new AudioClip("file:///C:/Users/FerrariHD/workspace/Test/bin/shot.wav");
    shot.setVolume(1);

    wallhit = new AudioClip(
        "file:///C:/Users/FerrariHD/workspace/Test/bin/wallHit2.wav");
    wallhit.setVolume(0.5);

    tankhit = new AudioClip(
        "file:///C:/Users/FerrariHD/workspace/Test/bin/tankHit.wav");
    tankhit.setVolume(0.8);

    tankexplosion = new AudioClip(
        "file:///C:/Users/FerrariHD/workspace/Test/bin/tankExplosion.wav");
    tankexplosion.setVolume(0.5);
  }

  public static void chooseMusic(int number) {
    switch (number) {
      case 0:
        mediaplayer.play();
        break;
      case 1:
        mediaplayer.stop();
        mediaplayer2.play();
        break;
      case 2:
        backSound.play();
        break;
    }
  }

  public static void shot() {
    shot.play();
  }

  public static void wallHit() {
    wallhit.play();
  }

  public static void tankHit() {
    tankhit.play();
  }

  public static void tankExplosion() {
    tankexplosion.play();
  }

  public static void activateEngineSound(boolean status) {
    if (status == true)
      engineSound.play();
    else
      engineSound.stop();
  }
}

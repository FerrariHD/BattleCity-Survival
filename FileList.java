package game;

import sort.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class FileList extends ListView<String> {
  private GameInfo[] items;
  public static final int WIDTH = 500;
  public static final int HEIGHT = 350;
  private final int spacingY = 40;

  public FileList(String[] addenItems) {
    setPrefSize(WIDTH, HEIGHT);
    setTranslateX(405);
    setTranslateY(spacingY);
    setOpacity(0.95);
    items = new GameInfo[addenItems.length];
    for (int i = 0; i < addenItems.length; i++) {
      items[i] = new GameInfo(addenItems[i]);
    }
    ObservableList<String> ol = FXCollections.observableArrayList();
    ol.addAll(addenItems);
    setItems(ol);
  }

  public void setFiles(String[] addenItems) {
    ObservableList<String> ol = FXCollections.observableArrayList();
    ol.addAll(addenItems);
    items = new GameInfo[addenItems.length];
    for (int i = 0; i < addenItems.length; i++) {
      items[i] = new GameInfo(addenItems[i]);
    }
    setItems(ol);
  }
  
  public void javaSort() {
    QuickSort quickSort = new QuickSort();
    quickSort.sort(items);
    ObservableList<String> ol = FXCollections.observableArrayList();
    for (int i = 0; i < items.length; i++) {
      ol.add(items[i].getFileName());
    }
    setItems(ol);
  }

  public void scalaSort() {
    QuickSortScala quickSort = new QuickSortScala();
    quickSort.sort(items);
    ObservableList<String> ol = FXCollections.observableArrayList();
    for (int i = 0; i < items.length; i++) {
      ol.add(items[i].getFileName());
    }
    setItems(ol);
  }

  private final int COUNT_SORT = 10000;

  public void sortTests() {
    int testsCount = 1;
    long[] scalaResults = new long[testsCount];
    long[] javaResults = new long[testsCount];

    for (int currentTest = 0; currentTest < testsCount; currentTest++) {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < COUNT_SORT; i++) {
        scalaSort();
      }
      long endTime = System.currentTimeMillis();
      scalaResults[currentTest] = endTime - startTime;

      startTime = System.currentTimeMillis();
      for (int i = 0; i < COUNT_SORT; i++) {
        javaSort();
      }
      endTime = System.currentTimeMillis();
      javaResults[currentTest] = endTime - startTime;
    }
    System.out.printf("\nРезультаты тестов\n");
    System.out.printf("Количество файлов: %d\n", items.length);
    System.out.printf("Количество повторений: %d\n", COUNT_SORT);
    System.out.printf("Количество тестов: %d\n", testsCount);
    System.out.printf("Среднее время для Scala(ms): \n");
    for (int i = 0; i < testsCount; i++) {
      System.out.printf("%d\t", scalaResults[i]);
    }
    System.out.printf("\nСреднее время для Java(ms): \n");
    for (int i = 0; i < testsCount; i++) {
      System.out.printf("%d\t", javaResults[i]);
    }
  }
}

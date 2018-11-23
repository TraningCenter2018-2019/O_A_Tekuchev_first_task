package crossword.logic.internals;

import java.util.Collection;

public class Crossword {
  private int rows;
  private int cols;
  private Word[] words;
  private String name;

  public Crossword(int rows, int cols, Collection<Word> words) {
    this.rows = rows;
    this.cols = cols;
    this.words = words.toArray(new Word[]{});
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return cols;
  }

  public Word getWord(int ind) {
    return words[ind];
  }

  public String getName() {
    return name;
  }

  public int getCountWords() {
    return words.length;
  }

  @Override
  public String toString() {
    return getName();
  }
}

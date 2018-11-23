package crossword.logic.internals;

/**
 * The word representation in a crossword
 */
public class Word {
  // the start row position
  private int startRow;

  // the start column position
  private int startCol;

  // the word itself
  private String value;

  // whether the word is places horizontal
  private boolean isHorizontal;

  public Word(String value, int sX, int sY, boolean isHor) {
    this.value = value;
    startRow = sX;
    startCol = sY;
    isHorizontal = isHor;
  }

  /**
   * Gets the word itself
   * @return
   */
  public String getValue() {
    return value;
  }

  /**
   * Gets the length of the word
   * @return
   */
  public int length() {
    return value.length();
  }

  /**
   * Gets the start row position
   * @return
   */
  public int getStartRow() {
    return startRow;
  }
  /**
   * Gets the start column position
   * @return
   */
  public int getStartCol() {
    return startCol;
  }

  /**
   * Get the word char
   * @param ind the char position
   * @return
   */
  public char getChar(int ind) {
    return value.charAt(ind);
  }

  /**
   * Gets whether the word is horizontal
   * @return
   */
  public boolean isHorizontal() {
    return isHorizontal;
  }
}

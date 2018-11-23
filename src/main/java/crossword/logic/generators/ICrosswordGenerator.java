package crossword.logic.generators;

import crossword.logic.internals.Crossword;

/**
 * A crossword creator interface
 */
public interface ICrosswordGenerator {
  /**
   * Creates a crossword
   *
   * @param keyWords the word from which it needs to get a crossword
   * @param countRows the row grid size
   * @param countCols the column grid size
   * @return
   */
  Crossword generate(String[] keyWords, int countRows, int countCols);
}

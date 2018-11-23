package crossword.logic.translators;

import crossword.logic.internals.Crossword;

/**
 * Converts the crossword to special data format
 */
public interface ITranslator {
  int[][] translate(Crossword cross);
}

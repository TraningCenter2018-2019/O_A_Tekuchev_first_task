package crossword.logic.translators;

/**
 * The translator for hidden words
 */
public class TranslatorHiddenWords extends AbstractTranslator {
  @Override
  protected char getChar(char ch) {
    return '_';
  }
}

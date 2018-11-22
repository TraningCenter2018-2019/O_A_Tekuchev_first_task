package crossword.logic.translators;

import crossword.logic.internals.Crossword;

/**
 * Переводит кроссворд в особый формат данных
 */
public interface ITranslator {
    int[][] translate(Crossword cross);
}

package crossword.logic.translators;

import crossword.logic.internals.Crossword;

public interface ITranslator {
    int[][] translate(Crossword cross);
}

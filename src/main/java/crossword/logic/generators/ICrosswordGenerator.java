package crossword.logic.generators;

import crossword.logic.internals.Crossword;

public interface ICrosswordGenerator {
    Crossword generate(String[] keyWords, int countRows, int countCols);
}

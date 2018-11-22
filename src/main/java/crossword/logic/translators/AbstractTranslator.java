package crossword.logic.translators;

import crossword.logic.internals.Crossword;
import crossword.logic.internals.Word;

import java.util.Arrays;

/**
 * Формат дынных представляет собой матрицу из целых значение, где
 * каждый элемент матрицы представляет собой букву в сетке кроссворда
 * младшие 16 бит - код буквы
 * старшие 16 бит - номер слова у его первой буквы
 */
public abstract class AbstractTranslator implements ITranslator {

    static private int BIT_SHIFT = 16;

    protected abstract char getChar(char ch);

    /**
     * Закодировать слово
     *
     * @param grid матрица данных
     * @param word кодируемое слово
     * @param order его прдеполагаемый номер в на сетке
     * @return реальный номер на сетке (если два слова начинаются на одну букву => у них один номер)
     */
    private int codeWord(int grid[][], Word word, int order) {
        int len = word.length();
        int startNumber = order << BIT_SHIFT;
        int wordNumber;
        if (grid[word.getStartRow()][word.getStartCol()] >> BIT_SHIFT != 0) {
            wordNumber = grid[word.getStartRow()][word.getStartCol()] >> BIT_SHIFT;
        }
        else {
            wordNumber = order;
            grid[word.getStartRow()][word.getStartCol()] = startNumber + getChar(word.getChar(0));
        }

        if (word.isHorizontal()) {
            int row = word.getStartRow();
            for (int i = word.getStartCol() + 1; i < word.getStartCol() + len; ++i) {
                if (grid[row][i] == 0) {
                    grid[row][i] = getChar(word.getChar(i - word.getStartCol()));
                }
            }
        }
        else {
            int col = word.getStartCol();
            for (int i = word.getStartRow() + 1; i < word.getStartRow() + len; ++i) {
                if (grid[i][col] == 0) {
                    grid[i][col] = getChar(word.getChar(i - word.getStartRow()));
                }
            }
        }
        return wordNumber;
    }

    @Override
    public final int[][] translate(Crossword cross) {
        int[][] grid = new int[cross.getRows()][cross.getColumns()];
        for (int i = 0; i < grid.length; ++i) {
            Arrays.fill(grid[i],0);
        }
        int order = 1;
        for (int i = 0; i < cross.getCountWords(); ++i) {
            if (codeWord(grid, cross.getWord(i), order) == order) {
                ++order;
            }
        }
        return grid;
    }
}

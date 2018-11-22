package crossword.logic.generators;

import crossword.logic.internals.Crossword;
import crossword.logic.internals.Word;
import crossword.logic.dictionaries.WordDictionary;

import java.util.*;

public class CrosswordGenerator implements ICrosswordGenerator {

    static private final char EMPTY_CHAR = '\0';
    static private final char LOCK_CHAR = '#';

    private char[][] _matrix;
    WordDictionary _dic;


    static private void setWord(char[][] matr, Word wrd) {
        int len = wrd.length();
        if (wrd.isHorizontal()) {
            int row = wrd.getStartRow();
            for (int i = wrd.getStartCol(); i < wrd.getStartCol() + len; ++i) {
                matr[row][i] = wrd.getChar(i - wrd.getStartCol());
            }
            if (wrd.getStartCol() + len < matr[0].length) {
                matr[row][wrd.getStartCol() + len] = LOCK_CHAR;
            }
        }
        else {
            int col = wrd.getStartCol();
            for (int i = wrd.getStartRow(); i < wrd.getStartRow() + len; ++i) {
                matr[i][col] = wrd.getChar(i - wrd.getStartRow());
            }
            if (wrd.getStartRow() + len < matr.length) {
                matr[wrd.getStartRow() + len][col] = LOCK_CHAR;
            }
        }
    }

    static private char[][] createEmptyMatrix(int rows, int cols) {
        var matr = new char[rows][];
        for (int i = 0; i < rows; ++i) {
            matr[i] = new char[cols];
            Arrays.fill(matr[i], EMPTY_CHAR);
        }
        return matr;
    }

    static private void printMatrix(char[][] matr) {
        for (char[] str : matr) {
            for (char lett : str) {
                System.out.print(lett + " ");
            }
            System.out.println();
        }
    }

    static private char[][] copy(char[][] matr) {
        char[][] copied = new char[matr.length][];
        for (int i = 0; i < matr.length; ++i) {

            copied[i] = matr[i].clone();
        }
        return copied;
    }

    /**
     * Возвращет слова заданной длины по переданной маске (буква - позиция)
     *
     * @param masks маска
     * @param len длина слова
     * @param used список использованных
     * @return список подходящих слов
     */
    private Collection<String> getWordsByMask(Map<Character, List<Integer>> masks, int len, Set<String> used) {
        var set = _dic.getWordsByLetters(masks);
        var filteredSet = new HashSet<String>();
        for (String word : set) {
            if (word.length() == len && !used.contains(word)) {
                filteredSet.add(word);
            }
        }
        return filteredSet;
    }


    /**
     * Выполняет рекурсивный поиск подходящих слов относительно текущего
     *
     * @param matrix поле кроссворда
     * @param currWord текущее слово, по которому идет поиск
     * @param usedWordsValues задействованные слова
     * @param maxCount макс. кол-во возможных слов в кроссворде
     * @param currChainWord текущие слова в кроссворде
     * @param biggestChainWordList список с наибольшим кол-вом установленных когда-либо слов в кроссворде
     * @return true если удалось установить все слова, иначе false
     */
    private boolean findWords(char[][] matrix, Word currWord, Set<String> usedWordsValues, int maxCount,
                              List<Word> currChainWord, LinkedList<Word> biggestChainWordList) {

        // является ли текущее слово горизонтальным
        boolean isHor = currWord.isHorizontal();

        // размер сетки
        int size = isHor ? matrix.length : matrix[0].length;

        // если слово не влезает в сетку
        if (currWord.length() > size) {
            return false;
        }

        // добавляем слово в список использованных
        usedWordsValues.add(currWord.getValue());

        // устанавливаем слово на сетку
        setWord(matrix, currWord);

        // добавляем слово в текущую цепочку
        currChainWord.add(currWord);

        // если текущая цепочка больше предыдущей максимальной
        // поставить текущую как максимальную
        if (currChainWord.size() > biggestChainWordList.size()) {
            biggestChainWordList.clear();
            biggestChainWordList.addAll(currChainWord);
        }

        /*printMatrix(matrix);
        System.out.println("----");*/

        // если были использованы все слова, то завершить алгоритм
        if (usedWordsValues.size() == maxCount) {
            return true;
        }

        // установить начальный индекс, по которому будет итерироваться слово
        // либо по-вертикали, либо по-горизонтали
        int startIndex = isHor ? currWord.getStartCol() : currWord.getStartRow();

        // установить предельный индекс, с которого может начинаться приставленное слово
        int startMaskLimit = isHor ? currWord.getStartRow() : currWord.getStartCol();

        // перебрать все буквы слова
        for (int i = startIndex; i < currWord.length(); ++i) {

            // первый указатель - индекс начала слова
            for (int j = 0; j <= startMaskLimit; ++j) {

                // маски для поиска слов для текущего пересечения
                var masks = new HashMap<Character, List<Integer>>();

                // второй указатель - индекс конца слова
                for (int k = j; k < size; ++k) {
                    // текущая клетка сетки
                    char currCell = isHor ? matrix[k][i] : matrix[i][k];

                    // если текущая клетка это "черная клетка", то прекратить перебор
                    if (currCell == LOCK_CHAR) {
                        break;
                    }

                    // если в текущей клетке стоит буква, то положить ее в маску
                    if (currCell != EMPTY_CHAR) {
                        if (!masks.containsKey(currCell)) {
                            masks.put(currCell, new ArrayList<>());
                        }
                        masks.get(currCell).add(k - j);
                    }

                    char nextCell = EMPTY_CHAR;
                    if (k < size - 1) {
                        nextCell = isHor ? matrix[k + 1][i] : matrix[i][k + 1];
                    }

                    // если второй указатель "перешагнул" через слово
                    // и при этом следующий за ним символ - не буква
                    if (k >= startMaskLimit &&
                            (k == size - 1 || nextCell == EMPTY_CHAR || nextCell == LOCK_CHAR)) {

                        // получить всевозможные слова по составленным масками
                        var words = getWordsByMask(masks, k - j + 1, usedWordsValues);
                        // скопировать текущую сетку
                        char[][] cop = copy(matrix);
                        // вызвать алгоритм рекурсивно для каждого из найденных слов
                        for (String wrd : words) {
                            var newWrd = isHor ? new Word(wrd, j, i, !isHor) : new Word(wrd, i, j, !isHor);
                            setWord(cop, newWrd);
                            if (findWords(cop, newWrd, usedWordsValues, maxCount, currChainWord, biggestChainWordList)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // удалить слово из текущей цепи
        currChainWord.remove(currWord);

        // удалить из использованных
        usedWordsValues.remove(currWord.getValue());
        return false;
    }

    @Override
    public Crossword generate(String[] keyWords, int countRows, int countCols) {
//        _matrix = createEmptyMatrix(countRows, countCols);
//
//        char[][] matrCopy = copy(_matrix);
        _dic = new WordDictionary(keyWords);
        var usedWordsValues = new HashSet<String>();
        var currChainWord = new LinkedList<Word>();
        var biggestChainWordList = new LinkedList<Word>();

        Arrays.sort(keyWords, (String o1, String o2) -> o2.length() - o1.length());

        for (String wrd : keyWords) {
            var matrix = createEmptyMatrix(countRows, countCols);
            if (findWords(
                    matrix,
                    new Word(wrd, countRows / 2, 0, true),
                    usedWordsValues,
                    keyWords.length,
                    currChainWord,
                    biggestChainWordList)) {

                break;
            }
        }
        return new Crossword(countRows, countCols, biggestChainWordList);
    }
}

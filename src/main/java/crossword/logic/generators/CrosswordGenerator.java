package crossword.logic.generators;

import crossword.logic.internals.Crossword;
import crossword.logic.internals.Word;
import crossword.logic.dictionaries.WordDictionary;

import java.util.*;

/**
 * The crossword generator
 */
public class CrosswordGenerator implements ICrosswordGenerator {
  static private final char EMPTY_CHAR = '\0';
  static private final char LOCK_CHAR = '#';

  WordDictionary dic;

  /**
   * Sets the word to the grid
   * @param grid
   * @param wrd
   */
  static private void setWord(char[][] grid, Word wrd) {
    int len = wrd.length();
    if (wrd.isHorizontal()) {
      int row = wrd.getStartRow();
      for (int i = wrd.getStartCol(); i < wrd.getStartCol() + len; ++i) {
        grid[row][i] = wrd.getChar(i - wrd.getStartCol());
      }
      if (wrd.getStartCol() + len < grid[0].length) {
        grid[row][wrd.getStartCol() + len] = LOCK_CHAR;
      }
    } else {
      int col = wrd.getStartCol();
      for (int i = wrd.getStartRow(); i < wrd.getStartRow() + len; ++i) {
        grid[i][col] = wrd.getChar(i - wrd.getStartRow());
      }
      if (wrd.getStartRow() + len < grid.length) {
        grid[wrd.getStartRow() + len][col] = LOCK_CHAR;
      }
    }
  }

  /**
   * Creates an empty crossword grid
   * @param rows
   * @param cols
   * @return
   */
  static private char[][] createEmptyGrid(int rows, int cols) {
    char[][] matr = new char[rows][];
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

  /**
   * creates the copy of the grid
   * @param matr
   * @return
   */
  static private char[][] copy(char[][] matr) {
    char[][] copied = new char[matr.length][];
    for (int i = 0; i < matr.length; ++i) {

      copied[i] = matr[i].clone();
    }
    return copied;
  }

  /**
   * Gets word with a given length by a given mask
   *
   * @param masks the mask
   * @param len   the word length
   * @param used  the list of used words
   * @return the list of compatible words
   */
  private Collection<String> getWordsByMask(Map<Character, List<Integer>> masks, int len, Set<String> used) {
    Set<String> set = dic.getWordsByLetters(masks);
    Set<String> filteredSet = new HashSet<String>();
    for (String word : set) {
      if (word.length() == len && !used.contains(word)) {
        filteredSet.add(word);
      }
    }
    return filteredSet;
  }


  /**
   * Tries to intersect words from
   *
   * @param grid               the crossword grid
   * @param currWord             the current word
   * @param usedWordsValues      the used words
   * @param maxCount             the max count of words in the crossword
   * @param currChainWord        the set word in the crossword
   * @param biggestChainWordList the list with the maximum chain of word that have been placed on the grid
   * @return true if all words were placed
   */
  private boolean findWords(char[][] grid, Word currWord, Set<String> usedWordsValues, int maxCount,
                            List<Word> currChainWord, LinkedList<Word> biggestChainWordList) {

    // whether the current word is horizontal
    boolean isHor = currWord.isHorizontal();

    // the grid size
    int size = isHor ? grid.length : grid[0].length;

    // if the cord is bigger than the grid
    if (currWord.length() > size) {
      return false;
    }

    // add the word in used list
    usedWordsValues.add(currWord.getValue());

    // set the word on the grid
    setWord(grid, currWord);

    // add the word in the current chain
    currChainWord.add(currWord);

    // if the current chain if bigger than max one
    // set the current chain as maximum
    if (currChainWord.size() > biggestChainWordList.size()) {
      biggestChainWordList.clear();
      biggestChainWordList.addAll(currChainWord);
    }

        /*printMatrix(matrix);
        System.out.println("----");*/

    // if all word have been used finish the algorithm
    if (usedWordsValues.size() == maxCount) {
      return true;
    }

    // set the initial index to iterate by the word
    // either horizontal or vertical
    int startIndex = isHor ? currWord.getStartCol() : currWord.getStartRow();

    // set the limit index from which the new word can start
    int startMaskLimit = isHor ? currWord.getStartRow() : currWord.getStartCol();

    // for every letter in the current word
    for (int i = startIndex; i < currWord.length(); ++i) {

      // the first pointer is the start index of the new word
      for (int j = 0; j <= startMaskLimit; ++j) {

        // the masks for the new word
        Map<Character, List<Integer>> masks = new HashMap<>();

        // the second pointer is the end index of the new word
        for (int k = j; k < size; ++k) {
          // the current cell
          char currCell = isHor ? grid[k][i] : grid[i][k];

          // if the current cell is "black"
          if (currCell == LOCK_CHAR) {
            break;
          }

          // if the current cell has a letter add it to the mask
          if (currCell != EMPTY_CHAR) {
            if (!masks.containsKey(currCell)) {
              masks.put(currCell, new ArrayList<>());
            }
            masks.get(currCell).add(k - j);
          }

          char nextCell = EMPTY_CHAR;
          if (k < size - 1) {
            nextCell = isHor ? grid[k + 1][i] : grid[i][k + 1];
          }

          // if the second pointer has gone over the word
          // and the next cell is not a letter
          if (k >= startMaskLimit &&
              (k == size - 1 || nextCell == EMPTY_CHAR || nextCell == LOCK_CHAR)) {

            // get all word by mask
            Collection<String> words = getWordsByMask(masks, k - j + 1, usedWordsValues);
            // cope the current grid
            char[][] cop = copy(grid);
            // call the algorithm recursivly
            for (String wrd : words) {
              Word newWrd = isHor ? new Word(wrd, j, i, !isHor) : new Word(wrd, i, j, !isHor);
              setWord(cop, newWrd);
              if (findWords(cop, newWrd, usedWordsValues, maxCount, currChainWord, biggestChainWordList)) {
                return true;
              }
            }
          }
        }
      }
    }

    // remove the word from the current chain
    currChainWord.remove(currWord);

    // remove the one from the list of used
    usedWordsValues.remove(currWord.getValue());
    return false;
  }

  @Override
  public Crossword generate(String[] keyWords, int countRows, int countCols) {
    dic = new WordDictionary(keyWords);
    Set<String> usedWordsValues = new HashSet<>();
    LinkedList<Word> currChainWord = new LinkedList<>();
    LinkedList<Word> biggestChainWordList = new LinkedList<>();

    Arrays.sort(keyWords, (String o1, String o2) -> o2.length() - o1.length());

    for (String wrd : keyWords) {
      char[][] matrix = createEmptyGrid(countRows, countCols);
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

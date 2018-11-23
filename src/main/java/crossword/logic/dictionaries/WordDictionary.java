package crossword.logic.dictionaries;

import crossword.logic.internals.Word;

import java.util.*;

/**
 * The word dictionary
 */
public class WordDictionary {
  private HashMap<Character, HashMap<Integer, ArrayList<String>>> set;

  public WordDictionary(String[] words) {
    set = fillSet(words);
  }

  /**
   * Заполнение структуры данными
   *
   * @param words
   * @return
   */
  private HashMap<Character, HashMap<Integer, ArrayList<String>>> fillSet(String[] words) {
    HashMap<Character, HashMap<Integer, ArrayList<String>>> set = new HashMap<>();
    for (String wrd : words) {
      for (int i = 0; i < wrd.length(); ++i) {
        if (!set.containsKey(wrd.charAt(i))) {
          set.put(wrd.charAt(i), new HashMap<>());
        }
        HashMap<Integer, ArrayList<String>> positions = set.get(wrd.charAt(i));
        ArrayList<String> w = positions.get(i);
        if (w == null) {
          w = new ArrayList<>();
          positions.put(i, w);
        }
        w.add(wrd.toLowerCase());
      }
    }
    return set;
  }

  /**
   * Пересечение множеств
   *
   * @param set1
   * @param set2
   * @return новое множество, включающее элементы, общие для двух множеств
   */
  private Set<String> intersect(Set<String> set1, Set<String> set2) {
    Set<String> intersectedSet = new HashSet<>();
    Set<String> minSet;
    Set<String> bigSet;
    if (set1.size() < set2.size()) {
      minSet = set1;
      bigSet = set2;
    } else {
      minSet = set2;
      bigSet = set1;
    }
    for (String item : minSet) {
      if (bigSet.contains(item)) {
        intersectedSet.add(item);
      }
    }
    return intersectedSet;
  }

  /**
   * Возвращает слова, содержащие букву на заданной позиции
   *
   * @param letter   буква слова
   * @param position позиция буквы в слове
   * @return
   */
  public Set<String> getWordsByLetter(Character letter, Integer position) {
    Collection<String> pos = set.get(letter).get(position);
    return pos == null ? new HashSet<>() : new HashSet<>(pos);
  }

  /**
   * Возвращает слова, содержащие букву на заданных позициях
   *
   * @param letter    буква слова
   * @param positions позиции буквы в слове
   * @return
   */
  public Set<String> getWordsByLetter(Character letter, List<Integer> positions) {
    Set<String> set = getWordsByLetter(letter, positions.get(0));
    for (int i = 1; i < positions.size(); ++i) {
      Set<String> newSet = getWordsByLetter(letter, positions.get(i));
      if (newSet.size() == 0) {
        return newSet;
      }
      set = intersect(set, newSet);
    }
    return set;
  }

  /**
   * Возвращает слова, содержащие заданные буквы на заданных позициях
   *
   * @param masks
   * @return
   */
  public Set<String> getWordsByLetters(Map<Character, List<Integer>> masks) {
    Set<String> set = new HashSet<>();
    boolean first = true;
    for (Character letter : masks.keySet()) {
      Set<String> words = getWordsByLetter(letter, masks.get(letter));
      if (first) {
        set.addAll(words);
        first = false;
        continue;
      }
      if (words.size() == 0) {
        set.clear();
        return set;
      }
      set = intersect(set, words);
    }
    return set;
  }
}

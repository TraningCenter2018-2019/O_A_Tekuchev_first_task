package crossword.logic.storages;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * The storage thar hold values in RAM
 * @param <T>
 */
public class RamStorage<T> implements IStorage<T> {
  private LinkedList<T> list = new LinkedList<>();

  @Override
  public void add(T item) {
    list.add(item);
  }

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public T find(IPredicate<T> predicate) {
    for (T item : list) {
      if (predicate.find(item)) {
        return item;
      }
    }
    return null;
  }

  @Override
  public Iterator<T> iterator() {
    return list.iterator();
  }
}

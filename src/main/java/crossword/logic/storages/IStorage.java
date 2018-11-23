package crossword.logic.storages;

/**
 * A storage interface
 * @param <T> data to store
 */
public interface IStorage<T> extends Iterable<T> {
  /**
   * Adds object to storage
   * @param item
   */
  void add(T item);

  /**
   * Gets the count of the elements
   * @return
   */
  int getCount();

  T find(IPredicate<T> predicate);
}

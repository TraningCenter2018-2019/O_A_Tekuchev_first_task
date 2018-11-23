package crossword.logic.storages;

@FunctionalInterface
public interface IPredicate<T> {
  boolean find(T item);
}

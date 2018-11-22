package crossword.logic.storages;

public interface IStorage<T> extends Iterable<T> {
    void add(T item);

    int getCount();

    T find(IPredicate<T> predicate);
}

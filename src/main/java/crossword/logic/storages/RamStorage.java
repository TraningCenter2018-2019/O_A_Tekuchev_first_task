package crossword.logic.storages;

import java.util.Iterator;
import java.util.LinkedList;

public class RamStorage<T> implements IStorage<T> {

    private LinkedList<T> _list = new LinkedList<>();

    @Override
    public void add(T item) {
        _list.add(item);
    }

    @Override
    public int getCount() {
        return _list.size();
    }

    @Override
    public T find(IPredicate<T> predicate) {
        for (var item : _list) {
            if (predicate.find(item)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return _list.iterator();
    }
}

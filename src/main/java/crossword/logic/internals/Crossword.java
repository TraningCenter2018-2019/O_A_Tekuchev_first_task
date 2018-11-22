package crossword.logic.internals;

import java.util.Collection;

public class Crossword {
    private int _rows;
    private int _cols;
    private Word[] _words;
    private String _name;

    public Crossword(int rows, int cols, Collection<Word> words) {
        _rows = rows;
        _cols = cols;
        _words = words.toArray(new Word[]{});
    }

    public void setName(String name) {
        _name = name;
    }

    public int getRows() {
        return _rows;
    }

    public int getColumns() {
        return _cols;
    }

    public Word getWord(int ind) {
        return _words[ind];
    }

    public String getName() {
        return _name;
    }

    public int getCountWords() {
        return _words.length;
    }
}

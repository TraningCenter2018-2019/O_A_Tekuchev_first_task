package crossword.logic.internals;

public class Word {
    private int _startRow;

    private int _startCol;

    private String _value;

    private boolean _isHorizontal;

    public Word(String value, int sX, int sY, boolean isHor) {
        _value = value;
        _startRow = sX;
        _startCol = sY;
        _isHorizontal = isHor;
    }

    public String getValue() {
        return _value;
    }

    public int length() {
        return _value.length();
    }

    public int getStartRow() {
        return _startRow;
    }

    public int getStartCol() {
        return _startCol;
    }

    public char getChar(int ind) {
        return _value.charAt(ind);
    }

    public boolean isHorizontal() {
        return _isHorizontal;
    }
}

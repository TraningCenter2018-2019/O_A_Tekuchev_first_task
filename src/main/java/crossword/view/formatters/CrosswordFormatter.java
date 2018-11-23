package crossword.view.formatters;

public class CrosswordFormatter implements IFormatter {
  static private int BIT_SHIFT = 16;
  static private int CHAR_MASK = 65535;

  @Override
  public String format(int data) {
    if (data == 0) {
      return " #";
    }
    int number = data >> BIT_SHIFT;
    int charCode = data & CHAR_MASK;
    char ch = (char) charCode;
    if (number != 0) {
      return Integer.toString(number) + ch;
    }
    return " " + ch;
  }
}

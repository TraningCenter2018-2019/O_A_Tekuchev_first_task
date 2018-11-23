package crossword.view.formatters;

/**
 *
 */
public interface IFormatter {

  /**
   * Convert the given data to show readable format
   *
   * @param data the data
   * @return the string representation
   */
  String format(int data);
}

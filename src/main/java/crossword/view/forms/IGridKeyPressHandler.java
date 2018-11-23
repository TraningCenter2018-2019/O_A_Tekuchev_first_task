package crossword.view.forms;

/**
 * An event handler on key press on a grid
 */
@FunctionalInterface
public interface IGridKeyPressHandler {
  /**
   *
   * @param key the pressed key
   * @param row the row index
   * @param col the column index
   */
  void keyPressed(char key, int row, int col);
}

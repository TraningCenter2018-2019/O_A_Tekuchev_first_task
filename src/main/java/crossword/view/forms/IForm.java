package crossword.view.forms;

import crossword.cli.ICommand;
import crossword.view.formatters.IFormatter;

/**
 * A form interface
 */
public interface IForm {

  /**
   * Creates an empty grid with the given size
   *
   * @param rows    the count of the rows
   * @param columns the count of the columns
   */
  void createGrid(int rows, int columns);

  /**
   * Gets the count of the rows
   *
   * @return
   */
  int getGridRows();

  /**
   * Gets the count of the columns
   *
   * @return
   */
  int getGridColumns();

  /**
   * Submits data to the form to show them on the grid
   *
   * @param grid the data
   */
  void setData(int[][] grid);

  /**
   * Устанавливает форматтер, который преобразует полученные данные в отображение на сетке
   * Sets a formatter that converts the given data to the images on the grid
   *
   * @param formatter new formatter
   */
  void setFormatter(IFormatter formatter);

  /**
   * Sets the given text to the description section
   *
   * @param desc text
   */
  void setDescription(String... desc);

  /**
   * Sets commands to the command section
   *
   * @param commands commands
   */
  void setCommands(ICommand... commands);

  /**
   * Sets an event handler on key press of the grid
   *
   * @param handler the event handler
   */
  void setGridKeyPressHandler(IGridKeyPressHandler handler);

  /**
   * Inputs a string from a dialog window
   *
   * @param title       the title of the windows
   * @param description the description
   * @param pattern     the pattern for user'r input (regexp)
   * @param errMsg      the message fot a user if his/her input text does not match to the pattern
   * @param isSmall     if true then the window will be one-line else multi-line
   * @return input string or null if a user pressed cancel
   */
  String inputFromDialogWindow(String title, String description, String pattern, String errMsg, boolean isSmall);

  /**
   *
   */
  String inputFromDialogWindow(String title, String description, boolean isSmall);

  /**
   * Represent the list of the object in the separate window
   *
   * @param title       the title
   * @param description the description
   * @param items       the object to show
   * @return selected object or null if a user pressed cancel
   */
  Object selectFromActionDialog(String title, String description, Object[] items);

  /**
   * Shows message in a window
   *
   * @param title the title
   * @param text  the message
   */
  void showMessage(String title, String text);

  /**
   * Shows the form
   */
  void show();

  /**
   * Closes the form
   */
  void close();
}

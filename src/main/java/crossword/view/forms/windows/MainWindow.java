package crossword.view.forms.windows;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import crossword.cli.ICommand;
import crossword.view.formatters.IFormatter;
import crossword.view.forms.IGridKeyPressHandler;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The main window of a form
 */
public class MainWindow extends BasicWindow {
  static private final int DESCRIPTION_TXTBX_WIDTH = 20;

  // the panel with a grid
  private Panel _gridPanel;

  // the panel with commands
  private Panel _commandPanel;

  // the description field
  private TextBox _descriptionTxtBx;

  // the grid
  private Table<String> _grid;

  // the row count
  private int _gridRows;

  // the column count
  private int _gridColumns;

  // the event handler on key press on the grid
  private IGridKeyPressHandler _keyPressHandler;

  // the formatter
  private IFormatter _formatter;

  public MainWindow() {
    setHints(Arrays.asList(Hint.FULL_SCREEN));

    Panel mainPanel = new Panel();
    mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

    _gridPanel = getGridPanel();
    _commandPanel = getCommandPanel();
    _descriptionTxtBx = getDescriptionTextBox();

    Panel left = new Panel();
    left.setLayoutManager(new LinearLayout(Direction.VERTICAL));
    left.addComponent(_gridPanel.withBorder(Borders.singleLine("Сетка")));
    left.addComponent(_commandPanel.withBorder(Borders.singleLine("Действия")));

    Panel right = new Panel();
    right.addComponent(_descriptionTxtBx.withBorder(Borders.singleLine("Описание")));

    mainPanel.addComponent(left);
    mainPanel.addComponent(right);

    setComponent(mainPanel);

    handleInput(new KeyStroke(KeyType.Unknown));
    addWindowListener(new KeyWindowsListener());
  }

  /**
   * Creates the empty headers for a table (grid)
   *
   * @param count the columns count
   * @return the empty headers
   */
  private String[] getEmptyHeaders(int count) {
    String[] headers = new String[count];
    Arrays.fill(headers, "");
    return headers;
  }

  /**
   * Creates grid panel
   *
   * @return
   */
  private Panel getGridPanel() {
    Panel gridPanel = new Panel();

    _grid = new Table<String>(getEmptyHeaders(9));
    _grid.getTableModel().addRow(
        "*", "w", "e", "l", "c", "o", "m", "e", "*"
    );

    gridPanel.addComponent(_grid);
    return gridPanel;
  }

  /**
   * Creates text box for description
   *
   * @return
   */
  private TextBox getDescriptionTextBox() {
    TextBox txtBx = new TextBox(new TerminalSize(DESCRIPTION_TXTBX_WIDTH, 15));
    txtBx.setReadOnly(true);
    return txtBx;
  }

  /**
   * Create command panel
   *
   * @return
   */
  private Panel getCommandPanel() {
    Panel commandPanel = new Panel();

    return commandPanel;
  }

  /**
   * Removes rows from the grid
   */
  private void clearGrid() {
    for (int i = _grid.getTableModel().getRowCount() - 1; i >= 0; --i) {
      _grid.getTableModel().removeRow(i);
    }
  }

  /**
   * Creates a grid with a given size
   *
   * @param rows    the row count
   * @param columns the column count
   */
  public void createGrid(int rows, int columns) {
    _gridPanel.removeAllComponents();
    _grid = new Table<>(getEmptyHeaders(columns));
    _gridRows = rows;
    _gridColumns = columns;
    _grid.setCellSelection(true);
    _gridPanel.addComponent(_grid);
  }

  /**
   * Gets the row count
   *
   * @return
   */
  public int getGridRows() {
    return _gridRows;
  }

  /**
   * Get the column count
   *
   * @return
   */
  public int getGridColumns() {
    return _gridColumns;
  }

  /**
   * Sets data to the grid
   *
   * @param data
   */
  public void setDataToGrid(int[][] data) {
    clearGrid();
    for (int i = 0; i < _gridRows; ++i) {
      String[] row = new String[_gridColumns];
      for (int j = 0; j < _gridColumns; ++j) {
        row[j] = _formatter.format(data[i][j]);
      }
      _grid.getTableModel().addRow(row);
    }
  }

  /**
   * Sets a formatter
   *
   * @param formatter
   */
  public void setFormatter(IFormatter formatter) {
    _formatter = formatter;
  }

  /**
   * Sets command to the command panel
   *
   * @param commands new commands
   */
  public void setCommands(ICommand[] commands) {
    _commandPanel.removeAllComponents();
    for (final ICommand cmd : commands) {
      Button button = new Button(cmd.getDescription());
      button.addListener(btn -> cmd.execute());
      _commandPanel.addComponent(button);
    }
  }

  /**
   * Sets description to the description text box
   *
   * @param desc every item goes with a new line
   */
  public void setDescription(String[] desc) {
    _descriptionTxtBx.setText("");
    for (String str : desc) {
      _descriptionTxtBx.addLine(str);
    }
  }

  /**
   * Sets event handler onk key press
   * @param handler
   */
  public void setGridHandler(IGridKeyPressHandler handler) {
    _keyPressHandler = handler;
  }

  /**
   * The window event handler
   */
  class KeyWindowsListener implements WindowListener {

    @Override
    public void onResized(Window window, TerminalSize terminalSize, TerminalSize terminalSize1) {
    }

    @Override
    public void onMoved(Window window, TerminalPosition terminalPosition, TerminalPosition terminalPosition1) {
    }

    @Override
    public void onInput(Window window, KeyStroke keyStroke, AtomicBoolean atomicBoolean) {
      if (_keyPressHandler == null || _grid == null || !_grid.isFocused() || keyStroke.getCharacter() == null) {
        return;
      }
      int row = _grid.getSelectedRow();
      int col = _grid.getSelectedColumn();
      _keyPressHandler.keyPressed(keyStroke.getCharacter(), row, col);
    }

    @Override
    public void onUnhandledInput(Window window, KeyStroke keyStroke, AtomicBoolean atomicBoolean) {
    }
  }

}

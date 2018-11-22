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
 * Главное окно формы
 */
public class MainWindow extends BasicWindow {

    static private final int DESCRIPTION_TXTBX_WIDTH = 20;

    // панель с сеткой
    private Panel _gridPanel;

    // панель с командами
    private Panel _commandPanel;

    // поле описания
    private TextBox _descriptionTxtBx;

    // сетка
    private Table<String> _grid;

    // кол-во строк в сетке
    private int _gridRows;

    // кол-во столбцов в сетке
    private int _gridColumns;

    // обоаботчик события на нажатие клавиши на сетке
    private IGridKeyPressHandler _keyPressHandler;

    // преобразователь данных
    private IFormatter _formatter;

    public MainWindow() {
        setHints(Arrays.asList(Hint.FULL_SCREEN));

        var mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        _gridPanel = getGridPanel();
        _commandPanel = getCommandPanel();
        _descriptionTxtBx = getDescriptionTextBox();

        var left = new Panel();
        left.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        left.addComponent(_gridPanel.withBorder(Borders.singleLine("Сетка")));
        left.addComponent(_commandPanel.withBorder(Borders.singleLine("Действия")));

        var right = new Panel();
        right.addComponent(_descriptionTxtBx.withBorder(Borders.singleLine("Описание")));

        mainPanel.addComponent(left);
        mainPanel.addComponent(right);

        setComponent(mainPanel);

        handleInput(new KeyStroke(KeyType.Unknown));
        addWindowListener(new KeyWindowsListener());
    }

    /**
     * Создает пустые хедеры для таблицы, которая играет роль сетки
     *
     * @param count кол-во столбцов
     * @return массив пустых строк
     */
    private String[] getEmptyHeaders(int count) {
        var headers = new String[count];
        Arrays.fill(headers, "");
        return headers;
    }

    /**
     * Создает панел для расположения сетки
     * @return
     */
    private Panel getGridPanel() {
        var gridPanel = new Panel();

        _grid = new Table<String>(getEmptyHeaders(9));
        _grid.getTableModel().addRow(
                "*", "w", "e", "l", "c", "o", "m", "e", "*"
        );

        gridPanel.addComponent(_grid);
        return gridPanel;
    }

    /**
     * Создает текстбокс для описания
     *
     * @return
     */
    private TextBox getDescriptionTextBox() {
        var txtBx = new TextBox(new TerminalSize(DESCRIPTION_TXTBX_WIDTH,15));
        txtBx.setReadOnly(true);
        return txtBx;
    }

    /**
     * Создает панель для размещения команд
     * @return
     */
    private Panel getCommandPanel() {
        var commandPanel = new Panel();

        return commandPanel;
    }

    /**
     * Удаляет строки из сетки
     */
    private void clearGrid() {
        for (int i = _grid.getTableModel().getRowCount() - 1; i >= 0; --i) {
            _grid.getTableModel().removeRow(i);
        }
    }

    /**
     * Создает сетку с заданным размером
     *
     * @param rows кол-во строк
     * @param columns кол-во столбцов
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
     * Возвращает кол-во строк в сетке
     *
     * @return
     */
    public int getGridRows() {
        return _gridRows;
    }

    /**
     * Возвращает кол-во столбцов в сетке
     *
     * @return
     */
    public int getGridColumns() {
        return _gridColumns;
    }

    // !!!!
    /**
     * Устанавливает данные в сетку, пропуская из через форматтер
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
     * Устанавливает форматтер
     * @param formatter
     */
    public void setFormatter(IFormatter formatter) {
        _formatter = formatter;
    }

    /**
     * Устанавливает команды в виде кнопок в панель команд
     *
     * @param commands команды для установки
     */
    public void setCommands(ICommand[] commands) {
        _commandPanel.removeAllComponents();
        for (final var cmd : commands) {
            var button = new Button(cmd.getDescription());
            button.addListener(btn -> cmd.execute());
            _commandPanel.addComponent(button);
        }
    }

    /**
     * Устанавливает текст в текстбокс с описанием
     *
     * @param desc массив строк, каждая строка устанавливатся с начала
     */
    public void setDescription(String[] desc) {
        _descriptionTxtBx.setText("");
        for (String str : desc) {
            _descriptionTxtBx.addLine(str);
        }
    }

    public void setGridHandler(IGridKeyPressHandler handler) {
        _keyPressHandler = handler;
    }

    /**
     * Обработчик событий формы
     */
    class KeyWindowsListener implements WindowListener {

        @Override
        public void onResized(Window window, TerminalSize terminalSize, TerminalSize terminalSize1) { }

        @Override
        public void onMoved(Window window, TerminalPosition terminalPosition, TerminalPosition terminalPosition1) { }

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
        public void onUnhandledInput(Window window, KeyStroke keyStroke, AtomicBoolean atomicBoolean) { }
    }

}

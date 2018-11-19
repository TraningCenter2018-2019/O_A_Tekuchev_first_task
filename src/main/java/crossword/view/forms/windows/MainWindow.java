package crossword.view.forms.windows;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import crossword.cli.ICommand;
import crossword.view.formatters.IFormatter;

import java.util.Arrays;

public class MainWindow extends BasicWindow {

    static private final int DESCRIPTION_TXTBX_WIDTH = 20;

    private Panel _gridPanel;
    private Panel _commandPanel;
    private TextBox _descriptionTxtBx;

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

        var welcom = new Table<Character>(getEmptyHeaders(9));
        welcom.getTableModel().addRow(
                '*', 'w', 'e', 'l', 'c', 'o', 'm', 'e', '*'
        );

        gridPanel.addComponent(welcom);
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

}

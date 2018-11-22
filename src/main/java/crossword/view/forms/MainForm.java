package crossword.view.forms;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import crossword.cli.ICommand;
import crossword.view.formatters.IFormatter;
import crossword.view.forms.windows.MainWindow;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Главная форма приложения
 */
public class MainForm implements IForm {

    DefaultTerminalFactory _terminalFactory;
    Screen _screen;
    MainWindow _mainWindow;
    MultiWindowTextGUI _textGui;

    public MainForm() {
        try {
            _terminalFactory = new DefaultTerminalFactory();
            _screen = _terminalFactory.createScreen();
            _mainWindow = new MainWindow();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createGrid(int rows, int columns) {
        _mainWindow.createGrid(rows, columns);
    }

    @Override
    public int getGridRows() {
        return _mainWindow.getGridRows();
    }

    @Override
    public int getGridColumns() {
        return _mainWindow.getGridColumns();
    }

    @Override
    public void setData(int[][] grid) {
        _mainWindow.setDataToGrid(grid);
    }

    @Override
    public void setFormatter(IFormatter formatter) {
        _mainWindow.setFormatter(formatter);
    }

    @Override
    public void setDescription(String... desc) {
        _mainWindow.setDescription(desc);
    }

    @Override
    public void setCommands(ICommand... commands) {
        _mainWindow.setCommands(commands);
    }

    @Override
    public void setGridKeyPressHandler(IGridKeyPressHandler handler) {
        _mainWindow.setGridHandler(handler);
    }

    @Override
    public String inputFromDialogWindow(String title, String description, String pattern, String errMsg, boolean isSmall) {
        var termSize = new TerminalSize(isSmall ? 10 : 50, isSmall ? 1 : 10);
        return new TextInputDialogBuilder()
                .setTitle(title)
                .setDescription(description)
                .setValidationPattern(Pattern.compile(pattern), errMsg)
                .setTextBoxSize(termSize)
                .build()
                .showDialog(_textGui);
    }

    @Override
    public String inputFromDialogWindow(String title, String description, boolean isSmall) {
        return inputFromDialogWindow(title, description, ".*", "", isSmall);
    }

    @Override
    public Object selectFromActionDialog(String title, String description, Object[] items) {
        var  builder = new ActionListDialogBuilder()
                .setTitle(title)
                .setDescription(description);
        Object[] selected = new Object[1];
        for (var item : items) {
            builder.addAction(item.toString(), () -> selected[0] = item);
        }
        var t = builder.build().showDialog(_textGui);
        return selected[0];
    }

    @Override
    public void showMessage(String title, String text) {
        new MessageDialogBuilder()
                .setTitle(title)
                .setText(text)
                .build()
                .showDialog(_textGui);
    }

    @Override
    public void show() {
        try {
            _screen.startScreen();
            _textGui = new MultiWindowTextGUI(_screen);
            _textGui.addWindowAndWait(_mainWindow);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            _screen.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

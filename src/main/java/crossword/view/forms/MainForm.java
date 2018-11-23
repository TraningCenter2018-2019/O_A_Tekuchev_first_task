package crossword.view.forms;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialog;
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
 * The pseudo graphic form
 */
public class MainForm implements IForm {
  // the terminal factory
  private DefaultTerminalFactory terminalFactory;

  // the terminal screen
  private Screen screen;

  // the main window
  private MainWindow mainWindow;

  // the text gui object
  private MultiWindowTextGUI textGui;

  public MainForm() {
    try {
      terminalFactory = new DefaultTerminalFactory();
      screen = terminalFactory.createScreen();
      mainWindow = new MainWindow();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void createGrid(int rows, int columns) {
    mainWindow.createGrid(rows, columns);
  }

  @Override
  public int getGridRows() {
    return mainWindow.getGridRows();
  }

  @Override
  public int getGridColumns() {
    return mainWindow.getGridColumns();
  }

  @Override
  public void setData(int[][] grid) {
    mainWindow.setDataToGrid(grid);
  }

  @Override
  public void setFormatter(IFormatter formatter) {
    mainWindow.setFormatter(formatter);
  }

  @Override
  public void setDescription(String... desc) {
    mainWindow.setDescription(desc);
  }

  @Override
  public void setCommands(ICommand... commands) {
    mainWindow.setCommands(commands);
  }

  @Override
  public void setGridKeyPressHandler(IGridKeyPressHandler handler) {
    mainWindow.setGridHandler(handler);
  }

  @Override
  public String inputFromDialogWindow(String title, String description, String pattern, String errMsg, boolean isSmall) {
    TerminalSize termSize = new TerminalSize(isSmall ? 10 : 50, isSmall ? 1 : 10);
    return new TextInputDialogBuilder()
        .setTitle(title)
        .setDescription(description)
        .setValidationPattern(Pattern.compile(pattern), errMsg)
        .setTextBoxSize(termSize)
        .build()
        .showDialog(textGui);
  }

  @Override
  public String inputFromDialogWindow(String title, String description, boolean isSmall) {
    return inputFromDialogWindow(title, description, ".*", "", isSmall);
  }

  @Override
  public Object selectFromActionDialog(String title, String description, Object[] items) {
    ActionListDialogBuilder builder = new ActionListDialogBuilder()
        .setTitle(title)
        .setDescription(description);
    Object[] selected = new Object[1];
    for (Object item : items) {
      builder.addAction(item.toString(), () -> selected[0] = item);
    }
    Object t = builder.build().showDialog(textGui);
    return selected[0];
  }

  @Override
  public void showMessage(String title, String text) {
    new MessageDialogBuilder()
        .setTitle(title)
        .setText(text)
        .build()
        .showDialog(textGui);
  }

  @Override
  public void show() {
    try {
      screen.startScreen();
      textGui = new MultiWindowTextGUI(screen);
      textGui.addWindowAndWait(mainWindow);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void close() {
    try {
      screen.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

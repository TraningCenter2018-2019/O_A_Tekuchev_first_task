package crossword.view.forms;

@FunctionalInterface
public interface IGridKeyPressHandler {
    void keyPressed(char key, int row, int col);
}

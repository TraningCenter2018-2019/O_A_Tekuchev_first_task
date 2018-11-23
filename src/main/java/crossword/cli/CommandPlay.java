package crossword.cli;

import crossword.logic.internals.Crossword;
import crossword.logic.storages.IStorage;
import crossword.logic.translators.ITranslator;
import crossword.view.forms.IForm;

/**
 * The resolve crossword command
 */
public class CommandPlay extends AbstractFormCommand {

  // the current grid
  private int[][] currGrid;

  // the answer grid
  private int[][] answerGrid;

  // the crossword storage
  private IStorage<Crossword> storage;

  // the translator that hides letters
  private ITranslator translatorHidden;

  // the translator that shows letters
  private ITranslator translatorShowed;

  public CommandPlay(IForm form, IStorage<Crossword> stor, ITranslator transHid, ITranslator transShow) {
    super(form);
    storage = stor;
    translatorHidden = transHid;
    translatorShowed = transShow;
  }

  /**
   * Checks whether the crossword is solved correctly
   *
   * @return true if it is else - false
   */
  private boolean check() {
    for (int i = 0; i < currGrid.length; ++i) {
      for (int j = 0; j < currGrid[i].length; ++j) {
        if (currGrid[i][j] != answerGrid[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Event handler on key press on the grid
   *
   * @param key the pressed key
   * @param row the row index
   * @param col the column index
   */
  void keyPress(char key, int row, int col) {
    if (key == '\n') {
      if (check()) {
        getForm().showMessage("Сообщение", "Кроссворд решен верно");
      } else {
        getForm().showMessage("Сообщение", "Кроссворд решен не верно");
      }
      return;
    }
    if (currGrid[row][col] == 0) {
      return;
    }
    currGrid[row][col] &= 2147418112;
    currGrid[row][col] += key;
    getForm().setData(currGrid);

  }

  @Override
  public String getDescription() {
    return "играть";
  }

  @Override
  public void execute() {
    if (storage.getCount() == 0) {
      getForm().showMessage("Сообщение", "Список кроссвордов пуст");
      return;
    }
    Crossword[] crossWords = new Crossword[storage.getCount()];
    int count = 0;
    for (Crossword cross : storage) {
      crossWords[count++] = cross;
    }
    Crossword playingCross = (Crossword) getForm().selectFromActionDialog(
        "Доступные кроссворды",
        "Выберите кроссворд",
        crossWords
    );
    if (playingCross == null) {
      return;
    }
    getForm().createGrid(playingCross.getRows(), playingCross.getColumns());
    currGrid = translatorHidden.translate(playingCross);
    answerGrid = translatorShowed.translate(playingCross);
    getForm().setData(currGrid);
    getForm().setGridKeyPressHandler(this::keyPress);
  }
}

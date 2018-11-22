package crossword.cli;

import crossword.logic.internals.Crossword;
import crossword.logic.storages.IStorage;
import crossword.logic.translators.ITranslator;
import crossword.view.forms.IForm;

public class CommandPlay extends AbstractFormCommand {

    private int[][] _currGrid;
    private int[][] _answerGrid;
    private ITranslator _translatorShowed;

    public CommandPlay(IForm form, IStorage<Crossword> stor, ITranslator transHid, ITranslator transShow) {
        super(form, stor, transHid);
        _translatorShowed = transShow;
    }

    static private int[][] copy(int[][] matr) {
        int[][] copied = new int[matr.length][];
        for (int i = 0; i < matr.length; ++i) {

            copied[i] = matr[i].clone();
        }
        return copied;
    }

    private boolean check() {
        for (int i = 0; i < _currGrid.length; ++i) {
            for (int j = 0; j < _currGrid[i].length; ++j) {
                if (_currGrid[i][j] != _answerGrid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    void keyPress(char key, int row, int col) {
        if (key == '\n') {
            if (check()) {
                getForm().setDescription("Кроссворд решен верно");
            }
            else {
                getForm().setDescription("Кроссворд решен не верно");
            }
            return;
        }
        if (_currGrid[row][col] == 0) {
            return;
        }
        _currGrid[row][col] &= 2147418112;
        _currGrid[row][col] += key;
        getForm().setData(_currGrid);

    }

    @Override
    public String getDescription() {
        return "играть";
    }

    @Override
    public void execute() {
        var crossNames = new String[getStorage().getCount()];
        int count = 0;
        for (var cross : getStorage()) {
            crossNames[count++] = cross.getName();
        }
        getForm().setDescription(crossNames);
        var selectedName = getForm().inputFromDialogWindow("Выберите кроссворд", "Введите имя",true);
        if (selectedName == null) {
            return;
        }
        var playingCross = getStorage().find(cross -> cross.getName().equals(selectedName));
        if (playingCross == null) {
            return;
        }
        getForm().createGrid(playingCross.getRows(),playingCross.getColumns());
        _currGrid = getTranslator().translate(playingCross);
        _answerGrid = _translatorShowed.translate(playingCross);
        getForm().setData(_currGrid);
        getForm().setGridKeyPressHandler(this::keyPress);
    }
}

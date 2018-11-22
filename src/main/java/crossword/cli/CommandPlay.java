package crossword.cli;

import crossword.logic.internals.Crossword;
import crossword.logic.storages.IStorage;
import crossword.logic.translators.ITranslator;
import crossword.view.forms.IForm;

/**
 * Команда разгадывания кроссворда
 */
public class CommandPlay extends AbstractFormCommand {

    // текущая сетка
    private int[][] _currGrid;

    // сетка с ответами
    private int[][] _answerGrid;

    // хранилище кроссвордов
    private IStorage<Crossword> _storage;

    // транслятор скрывающий буквы
    private ITranslator _translatorHidden;

    // транслятор показывающий буквы
    private ITranslator _translatorShowed;

    public CommandPlay(IForm form, IStorage<Crossword> stor, ITranslator transHid, ITranslator transShow) {
        super(form);
        _storage = stor;
        _translatorHidden = transHid;
        _translatorShowed = transShow;
    }

    /**
     * Проверяет правильность решения кроссворда
     *
     * @return true если кроссворд решен верно
     */
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

    /**
     * Обработчик события на нажатие клавиши на сетке
     *
     * @param key нажатая клавиша
     * @param row индекс строки
     * @param col индекс столбца
     */
    void keyPress(char key, int row, int col) {
        if (key == '\n') {
            if (check()) {
                getForm().showMessage("Сообщение","Кроссворд решен верно");
            }
            else {
                getForm().showMessage("Сообщение","Кроссворд решен не верно");
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
        if (_storage.getCount() == 0) {
            getForm().showMessage("Сообщение", "Список кроссвордов пуст");
            return;
        }
        var crossWords = new Crossword[_storage.getCount()];
        int count = 0;
        for (var cross : _storage) {
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
        getForm().createGrid(playingCross.getRows(),playingCross.getColumns());
        _currGrid = _translatorHidden.translate(playingCross);
        _answerGrid = _translatorShowed.translate(playingCross);
        getForm().setData(_currGrid);
        getForm().setGridKeyPressHandler(this::keyPress);
    }
}

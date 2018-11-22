package crossword.view.forms;

import crossword.cli.ICommand;
import crossword.view.formatters.IFormatter;

/**
 * Интерфейс формы
 */
public interface IForm {

    /**
     * Создает пустую сетку с заданным размером
     *
     * @param rows кол-во строк
     * @param columns кол-во столбцов
     */
    void createGrid(int rows, int columns);

    /**
     * Возвращает кол-во строк в сетке
     *
     * @return
     */
    int getGridRows();

    /**
     * Возвращает кол-во столбцов в сетке
     *
     * @return
     */
    int getGridColumns();

    /**
     * Передает данные в форму для отображения на сетке
     *
     * @param grid данные сетки
     */
    void setData(int[][] grid);

    /**
     * Устанавливает форматтер, который преобразует полученные данные в отображение на сетке
     *
     * @param formatter
     */
    void setFormatter(IFormatter formatter);

    /**
     * Устанавливает переданный текст в секцию описания формы
     *
     * @param desc текст описания
     */
    void setDescription(String ...desc);

    /**
     * Устанавливает команды в секцию команд формы
     *
     * @param commands команды
     */
    void setCommands(ICommand...commands);

    /**
     * Устанавливает обработчик события на нажатие клавиши на сетке
     *
     * @param handler обработчки
     * @see IGridKeyPressHandler
     */
    void setGridKeyPressHandler(IGridKeyPressHandler handler);

    /**
     * Ввод строки из диалогового окна формы
     *
     * @param title заголовок окна
     * @param description описание
     * @param pattern шаблон ввода (регулярное выражение)
     * @param errMsg сообщение пользователю, если его ввод не соответсвует шаблону
     * @param isSmall если true, то окно - однострочное, иначе многострочное
     * @return введенную строку или null, если пользователь отменил ввод
     */
    String inputFromDialogWindow(String title, String description, String  pattern, String errMsg, boolean isSmall);

    /**
     * Ввод строки из диалогового окна формы
     *
     * @param title заголовок окна
     * @param description описание
     * @param isSmall если true, то окно - однострочное, иначе многострочное
     * @return введенную строку или null, если пользователь отменил ввод
     */
    String inputFromDialogWindow(String title, String description, boolean isSmall);

    /**
     * Предоставляет список объектов в отдельном окне
     *
     * @param title заголовок
     * @param description описание
     * @param items объекты списка
     * @return выбранный объект или null при отмене
     */
    Object selectFromActionDialog(String title, String description, Object[] items);

    /**
     * Показывает сообщение в окошке
     *
     * @param title заголовок
     * @param text тест сообщения
     */
    void showMessage(String title, String text);

    /**
     * Отобразить форму
     */
    void show();

    /**
     * Зыкрыть форму
     */
    void close();
}

package crossword.view.forms;

import crossword.cli.ICommand;
import crossword.view.formatters.IFormatter;

public interface IForm {

    /**
     * Передает данные в форму для отображения в виде сетки
     * Размер сетки будет равен размеру массива переданых данных
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
     * Ввод строки из диалогового окна формы
     *
     * @param title заголовок окна
     * @param description описание
     * @param pattern шаблон ввода (регулярное выражение)
     * @return введенную строку или null, если пользователь отменил ввод
     */
    String inputFromDialogWindow(String title, String description, String  pattern, String errMsg, boolean isSmall);

    /**
     * Отображение формы
     */
    void show();
}

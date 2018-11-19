package crossword.view.formatters;

/**
 * Переводит полученные данные
 */
public interface IFormatter {

    /**
     * Переводит полученные данные в удобочитаемый формат
     *
     * @param data данные
     * @return строковое представление данных
     */
    String format(int data);
}

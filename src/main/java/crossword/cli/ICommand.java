package crossword.cli;

/**
 *
 */
public interface ICommand {

    /**
     * Текстовое описание команды
     *
     * @return
     */
    String getDescription();

    /**
     * Выполнить команду
     */
    void execute();
}

package crossword.cli;

public interface ICommand {

    String getDescription();

    void execute();
}

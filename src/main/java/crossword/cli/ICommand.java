package crossword.cli;

/**
 * A console command interface
 */
public interface ICommand {

  /**
   * A text description of the command
   *
   * @return
   */
  String getDescription();

  /**
   * Executes the command
   */
  void execute();
}

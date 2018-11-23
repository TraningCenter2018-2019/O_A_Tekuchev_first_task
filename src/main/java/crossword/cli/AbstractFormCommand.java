package crossword.cli;

import crossword.view.forms.IForm;

/**
 * A command that works with a form
 */
public abstract class AbstractFormCommand implements ICommand {
  // the form
  private IForm _form;

  public AbstractFormCommand(IForm form) {
    _form = form;
  }

  /**
   * Gets the associated form
   *
   * @return
   */
  protected final IForm getForm() {
    return _form;
  }

}

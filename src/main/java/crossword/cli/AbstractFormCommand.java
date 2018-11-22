package crossword.cli;

import crossword.view.forms.IForm;

/**
 *
 */
public abstract class AbstractFormCommand implements ICommand {

    private IForm _form;

    public AbstractFormCommand(IForm form) {
        _form = form;
    }

    protected final IForm getForm() {
        return _form;
    }

    protected final void setForm(IForm form) {
        _form = form;
    }

}

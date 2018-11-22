package crossword.cli;

import crossword.view.forms.IForm;

/**
 * Команда выхода из приложения
 */
public class CommandExit extends AbstractFormCommand {

    public CommandExit(IForm form) {
        super(form);
    }

    @Override
    public String getDescription() {
        return "выход";
    }

    @Override
    public void execute() {
        getForm().close();
    }
}

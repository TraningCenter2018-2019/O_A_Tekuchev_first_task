package crossword.cli;

import crossword.view.forms.IForm;

/**
 * Команда вызова помощи
 */
public class CommandHelp extends AbstractFormCommand {
    public CommandHelp(IForm form) {
        super(form);
    }

    @Override
    public String getDescription() {
        return "помощь";
    }

    @Override
    public void execute() {
        getForm().showMessage("Помощь","Чтобы создать кроссворд нажмите 'создать' \nи следуйте дальнейшим инструкциям\n"
        + "Чтобы разгадать кроссворд нажмите 'играть' \nи, перемещаясь по сетке стрелками, установите буквы на позиции\n"
        + "Для проверки результата нажмите enter в любом месте сетки");
    }
}

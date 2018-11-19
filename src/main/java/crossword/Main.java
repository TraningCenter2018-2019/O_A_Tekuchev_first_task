package crossword;

import crossword.cli.ICommand;
import crossword.view.forms.MainForm;

public class Main {
    static public void main(String[] args) {
        var f = new MainForm();
        f.setCommands(new ICommand() {
            @Override
            public String getDescription() {
                return "test command";
            }

            @Override
            public void execute() {
                var str = f.inputFromDialogWindow("title", "desc", ".*", "err", true);
                if (str != null) {
                    f.setDescription("Вы ввели: ", str);
                }
            }
        });
        f.show();
    }
}

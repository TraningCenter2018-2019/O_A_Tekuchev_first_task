package crossword.cli;

import crossword.logic.internals.Crossword;
import crossword.logic.generators.ICrosswordGenerator;
import crossword.logic.storages.IStorage;
import crossword.logic.translators.ITranslator;
import crossword.view.forms.IForm;

import java.util.regex.Pattern;

public class CommandCreate extends AbstractFormCommand {

    private ICrosswordGenerator _generator;

    public CommandCreate(IForm form, IStorage<Crossword> stor, ITranslator trans, ICrosswordGenerator gener) {
        super(form, stor, trans);
        _generator = gener;
    }

    @Override
    public String getDescription() {
        return "создать";
    }

    @Override
    public void execute() {
        String demension = getForm().inputFromDialogWindow(
                "Размер поля",
                "Введите размеры поля через пробел",
                "^\\s*\\d+\\s+\\d+\\s*$",
                "Некорректный ввод",
                true);
        var pattern = Pattern.compile("^(\\d+)\\s+(\\d+)$");
        var matcher = pattern.matcher(demension);
        if (!matcher.matches()) {
            return;
        }
        int row = Integer.parseInt(matcher.group(1));
        int cols = Integer.parseInt(matcher.group(2));

        String strNumber = getForm().inputFromDialogWindow(
                "Кол-во слов",
                "Введите кол-во слов в кроссворде",
                "^\\s*\\d+\\s*$",
                "Некорректный ввод",
                true);
        if (strNumber == null) {
            return;
        }
        int count = Integer.parseInt(strNumber);
        String[] keyWords = new String[count];
        String[] desc = new String[count];
        for (int i = 0; i < count; ++i) {
            String str = getForm().inputFromDialogWindow(
                    "Слово " + (i + 1),
                    "Введите слово и описание на новой строке",
                    false);
            if (str == null) {
                return;
            }
            var splited =  str.split("\\n");
            keyWords[i] = splited[0];
            desc[i] = splited.length > 1 ? splited[1] : "";

        }

        var crossword = _generator.generate( keyWords,row,cols);
        var name = getForm().inputFromDialogWindow("Введите назавание кроссворда", "", true);
        crossword.setName(name);
        getStorage().add(crossword);
        getForm().createGrid(row,cols);
        getForm().setData(getTranslator().translate(crossword));
        getForm().setDescription(desc);
    }
}

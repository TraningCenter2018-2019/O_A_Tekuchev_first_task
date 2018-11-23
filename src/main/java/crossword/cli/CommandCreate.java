package crossword.cli;

import crossword.logic.internals.Crossword;
import crossword.logic.generators.ICrosswordGenerator;
import crossword.logic.storages.IStorage;
import crossword.logic.translators.ITranslator;
import crossword.view.forms.IForm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A create crossword command
 */
public class CommandCreate extends AbstractFormCommand {
  // The crossword storage
  private IStorage<Crossword> storage;

  // The data translator
  private ITranslator translator;

  // The crossword generator
  private ICrosswordGenerator generator;

  public CommandCreate(IForm form, IStorage<Crossword> stor, ITranslator trans, ICrosswordGenerator gener) {
    super(form);
    storage = stor;
    translator = trans;
    generator = gener;
  }

  @Override
  public String getDescription() {
    return "создать";
  }

  @Override
  public void execute() {
    // input field size
    String demension = getForm().inputFromDialogWindow(
        "Размер поля",
        "Введите размеры поля через пробел",
        "^\\s*\\d+\\s+\\d+\\s*$",
        "Некорректный ввод",
        true);
    Pattern pattern = Pattern.compile("^(\\d+)\\s+(\\d+)$");
    Matcher matcher = pattern.matcher(demension);
    if (!matcher.matches()) {
      return;
    }
    int row = Integer.parseInt(matcher.group(1));
    int cols = Integer.parseInt(matcher.group(2));

    // input count words
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
      String[] splited = str.split("\\n");
      keyWords[i] = splited[0];
      desc[i] = splited.length > 1 ? splited[1] : "";

    }

    // create crossword
    Crossword crossword = generator.generate(keyWords, row, cols);

    getForm().createGrid(row, cols);
    getForm().setData(translator.translate(crossword));
    getForm().setDescription(desc);

    String name = getForm().inputFromDialogWindow(
        "Сохранить",
        "Введите назавание",
        true);
    if (name != null) {
      crossword.setName(name);
      storage.add(crossword);
    }
  }
}

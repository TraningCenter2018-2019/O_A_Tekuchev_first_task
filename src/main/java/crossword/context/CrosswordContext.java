package crossword.context;

import crossword.cli.CommandCreate;
import crossword.cli.CommandExit;
import crossword.cli.CommandHelp;
import crossword.cli.CommandPlay;
import crossword.logic.generators.CrosswordGenerator;
import crossword.logic.internals.Crossword;
import crossword.logic.storages.IStorage;
import crossword.logic.storages.RamStorage;
import crossword.logic.translators.ITranslator;
import crossword.logic.translators.TranslatorHiddenWords;
import crossword.logic.translators.TranslatorShowedWords;
import crossword.view.formatters.CrosswordFormatter;
import crossword.view.forms.IForm;
import crossword.view.forms.MainForm;

public class CrosswordContext implements IContext {
  private IForm form;

  public CrosswordContext(String... args) {
    form = new MainForm();
    IStorage<Crossword> storage = new RamStorage<>();
    ITranslator ts = new TranslatorShowedWords();
    form.setFormatter(new CrosswordFormatter());
    form.setCommands(
        new CommandCreate(form, storage, ts, new CrosswordGenerator()),
        new CommandPlay(form, storage, new TranslatorHiddenWords(), ts),
        new CommandHelp(form),
        new CommandExit(form)
    );
  }

  @Override
  public void start() {
    form.show();
  }
}

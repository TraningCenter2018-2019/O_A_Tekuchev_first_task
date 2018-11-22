package crossword.context;

import crossword.cli.CommandCreate;
import crossword.cli.CommandExit;
import crossword.cli.CommandHelp;
import crossword.cli.CommandPlay;
import crossword.logic.generators.CrosswordGenerator;
import crossword.logic.internals.Crossword;
import crossword.logic.storages.RamStorage;
import crossword.logic.translators.TranslatorHiddenWords;
import crossword.logic.translators.TranslatorShowedWords;
import crossword.view.formatters.CrosswordFormatter;
import crossword.view.forms.IForm;
import crossword.view.forms.MainForm;

public class CrosswordContext implements IContext {

    private IForm _form;
    public CrosswordContext(String ...args) {
        _form = new MainForm();
        var storage = new RamStorage<Crossword>();
        var ts = new TranslatorShowedWords();
        _form.setFormatter(new CrosswordFormatter());
        _form.setCommands(
                new CommandCreate(_form, storage, ts, new CrosswordGenerator()),
                new CommandPlay(_form, storage, new TranslatorHiddenWords(), ts),
                new CommandHelp(_form),
                new CommandExit(_form)
        );
    }

    @Override
    public void start() {
        _form.show();
    }
}

package crossword;

import crossword.cli.CommandCreate;
import crossword.cli.CommandPlay;
import crossword.logic.internals.Crossword;
import crossword.logic.generators.CrosswordGenerator;
import crossword.logic.storages.RamStorage;
import crossword.logic.translators.TranslatorHiddenWords;
import crossword.logic.translators.TranslatorShowedWords;
import crossword.view.formatters.CrosswordFormatter;
import crossword.view.forms.MainForm;

public class Main {
    static public void main(String[] args) {
        var f = new MainForm();
        var s = new RamStorage<Crossword>();
        var ts = new TranslatorShowedWords();
        f.setFormatter(new CrosswordFormatter());
        f.setCommands(
                new CommandCreate(f, s, ts, new CrosswordGenerator()),
                new CommandPlay(f, s, new TranslatorHiddenWords(), ts)
        );
        f.show();

    }
}

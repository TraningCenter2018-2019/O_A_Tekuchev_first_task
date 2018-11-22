package crossword.cli;

import crossword.logic.internals.Crossword;
import crossword.logic.storages.IStorage;
import crossword.logic.translators.ITranslator;
import crossword.view.forms.IForm;

public abstract class AbstractFormCommand implements ICommand {

    private IForm _form;
    private IStorage<Crossword> _storage;
    private ITranslator _translator;

    public AbstractFormCommand(IForm form, IStorage<Crossword> stor, ITranslator trans) {
        _form = form;
        _storage = stor;
        _translator = trans;
    }

    protected final IForm getForm() {
        return _form;
    }

    protected final void setForm(IForm form) {
        _form = form;
    }

    protected final IStorage<Crossword> getStorage() {
        return _storage;
    }

    protected final void setStorage(IStorage<Crossword> stor) {
        _storage = stor;
    }

    protected final ITranslator getTranslator() {
        return _translator;
    }

    protected final void setTranslator(ITranslator trans) {
        _translator = trans;
    }
}

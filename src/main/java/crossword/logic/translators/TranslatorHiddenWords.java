package crossword.logic.translators;

public class TranslatorHiddenWords extends AbstractTranslator {
    @Override
    protected char getChar(char ch) {
        return ' ';
    }
}

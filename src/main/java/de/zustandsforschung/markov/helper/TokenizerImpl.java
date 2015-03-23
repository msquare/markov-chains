package de.zustandsforschung.markov.helper;

import de.zustandsforschung.markov.model.Tokens;

public class TokenizerImpl implements Tokenizer {

    @Override
    public Tokens<String> tokenize(final String input) {
        final Tokens<String> tokens = new Tokens<String>();
        if (input != null && !"".equals(input)) {
            final String preparedInput = input.replaceAll(PUNCTUATION_REGEX,
                    " $1 ");
            tokens.addAll(new Tokens<String>(preparedInput.split(" +")));
        }
        return tokens;
    }

}

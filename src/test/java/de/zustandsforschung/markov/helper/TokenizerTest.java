package de.zustandsforschung.markov.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.zustandsforschung.markov.model.Tokens;

public class TokenizerTest {

    @Test
    public void testTokenizeEmpty() {
        String input = "";
        Tokenizer markovChain = new TokenizerImpl();
        assertEquals(new Tokens<String>(), markovChain.tokenize(input));
    }

    @Test
    public void testTokenizeSingle() {
        String input = "Word";
        Tokenizer markovChain = new TokenizerImpl();
        assertEquals(new Tokens<String>("Word"), markovChain.tokenize(input));
    }

    @Test
    public void testTokenizeWithSpace() {
        String input = "Two Words";
        Tokenizer markovChain = new TokenizerImpl();
        assertEquals(new Tokens<String>("Two", "Words"),
                markovChain.tokenize(input));
    }

    @Test
    public void testTokenizeWithPunctuation() {
        String input = "Two. Words, only!\"more";
        Tokenizer markovChain = new TokenizerImpl();
        assertEquals(new Tokens<String>("Two", ".", "Words", ",", "only", "!",
                "\"", "more"), markovChain.tokenize(input));
    }

}

package de.zustandsforschung.markov.helper;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class TokenizerTest {

	@Test
	public void testTokenizeEmpty() {
		String input = "";
		Tokenizer markovChain = new TokenizerImpl();
		assertEquals(Arrays.asList(), markovChain.tokenize(input));
	}

	@Test
	public void testTokenizeSingle() {
		String input = "Word";
		Tokenizer markovChain = new TokenizerImpl();
		assertEquals(Arrays.asList("Word"), markovChain.tokenize(input));
	}

	@Test
	public void testTokenizeWithSpace() {
		String input = "Two Words";
		Tokenizer markovChain = new TokenizerImpl();
		assertEquals(Arrays.asList("Two", "Words"), markovChain.tokenize(input));
	}

	@Test
	public void testTokenizeWithPunctuation() {
		String input = "Two. Words, only!\"more";
		Tokenizer markovChain = new TokenizerImpl();
		assertEquals(Arrays.asList("Two", ".", "Words", ",", "only", "!", "\"",
				"more"), markovChain.tokenize(input));
	}

}

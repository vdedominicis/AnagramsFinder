package org.apache.maven.anagrams;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.junit.Before;
import org.junit.Test;

/**
 * @author valerio
 * @version 1.0
 */
public class AnagramsUtilsTest {
	
	private AnagramsUtils anagramUtils = null;
	
	@Before
	public void setUp() throws Exception {
		anagramUtils = new AnagramsUtils(LogManager.getLogger("testLogger"));
	}
	
	/**
	 * The word value is calculated properly for the empty word
	 */
	@Test
	public void getValue_emptyWord() {
		assertEquals((long)1, anagramUtils.getValue("").longValue());
	}
	
	/**
	 * The word value is calculated properly for the null word
	 */
	@Test
	public void getValue_nullWord() {
		assertEquals((long)1, anagramUtils.getValue(null).longValue());
	}
	
	/**
	 * The word value is calculated properly for an arbitrary word
	 */
	@Test
	public void getValue_arbitraryWord() {
		assertEquals((long)30, anagramUtils.getValue("abc").longValue());
	}

	/**
	 * In case there are no words, no exceptions are thrown
	 */
	@Test
	public void findAnagrams_noExceptionAreThrownIfNoWordsAreProvided() {
		assertEquals(0, anagramUtils.findAnagrams(null).size());
		assertEquals(0, anagramUtils.findAnagrams(new LinkedList<String>()).size());
	}
	
	/**
	 * The invalid words (empty or null) are skipped while searching for anagrams
	 */
	@Test
	public void findAnagrams_theInvalidWordAreSkipped() {
		List<String> words = new LinkedList<String>();
		words.add("");
		words.add(null);
		assertEquals(0, anagramUtils.findAnagrams(words).size());
	}
	
	/**
	 * The anagrams groups are build properly providing well known sets of anagrams.
	 * This test verify the number of anagrams groups that the program build and compare it to the expected value.
	 */
	@Test
	public void findAnagrams_anagramsAreSpotted() {
		List<String> words = new LinkedList<String>();

		//Only one word - 1 group
		words.add("word");
		assertEquals(1, anagramUtils.findAnagrams(words).size());
		//Anagram of "word" - Still 1 group
		words.add("drow");
		assertEquals(1, anagramUtils.findAnagrams(words).size());
		//New word, 2 groups
		words.add("sword");
		assertEquals(2, anagramUtils.findAnagrams(words).size());
		//New word, 3 groups
		words.add("dawn");
		assertEquals(3, anagramUtils.findAnagrams(words).size());
		//Anagram of "dawn" - Still 3 groups
		words.add("wand");
		assertEquals(3, anagramUtils.findAnagrams(words).size());
		//The only word with no anagrams is removed - 2 groups
		words.remove("sword");
		assertEquals(2, anagramUtils.findAnagrams(words).size());
		//2 words which are mutual anagrams are removed - 1 group
		words.remove("drow");
		words.remove("word");
		assertEquals(1, anagramUtils.findAnagrams(words).size());
	}
		
	/**
	 * All anagrams in a group are concatenated together properly in a string with the specified separator
	 */
	@Test
	public void anagramSholdBeConcatenated(){
		List<String> words = new ArrayList<String>();
		words.add("race");
		words.add("care");
		words.add("acre");
		assertEquals("race,care,acre", anagramUtils.toString(words, ","));
	}
	
}

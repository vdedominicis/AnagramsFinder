package org.apache.maven.anagrams;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.naming.directory.InvalidAttributesException;
import org.apache.log4j.Logger;

/**
 * This class is a toolbox for managing single words and groups of anagrams.
 *
 * @author Valerio De Dominicis
 * @version 1.0
 */
class AnagramsUtils extends UtilsWithLogger{
	
	//This map is initialized only once when an instance of AnagramUtils is created. It provides
	//the prime number associated to each letter in the alphabet
	private Map<Character, Long> lettersValues;
	
	/**
	 * @throws InvalidAttributesException If the logger is null
	 */
	public AnagramsUtils(Logger logger) throws InvalidAttributesException {
		
		super(logger);
		
		//A specific prime number is associated to each letter. The value 1 is used for representing the empty word ("") or the non-existing one (null)
		lettersValues = new HashMap<Character, Long>();
		lettersValues.put('a', (long)2);
		lettersValues.put('b', (long)3);
		lettersValues.put('c', (long)5);
		lettersValues.put('d', (long)7);
		lettersValues.put('e', (long)11);
		lettersValues.put('f', (long)13);
		lettersValues.put('g', (long)17);
		lettersValues.put('h', (long)19);
		lettersValues.put('i', (long)23);
		lettersValues.put('j', (long)29);
		lettersValues.put('k', (long)31);
		lettersValues.put('l', (long)37);
		lettersValues.put('m', (long)41);
		lettersValues.put('n', (long)43);
		lettersValues.put('o', (long)47);
		lettersValues.put('p', (long)53);
		lettersValues.put('q', (long)59);
		lettersValues.put('r', (long)61);
		lettersValues.put('s', (long)67);
		lettersValues.put('t', (long)71);
		lettersValues.put('u', (long)73);
		lettersValues.put('v', (long)79);
		lettersValues.put('x', (long)83);
		lettersValues.put('y', (long)89);
		lettersValues.put('w', (long)97);
		lettersValues.put('z', (long)101);
	}
	
	/**
	 * Return the numeric value associated to a word. The value associated to a word is obtained by multiplying the prime numbers associated to the word's letters. 
	 * For instance, if we have the word "ab" and the associations 
	 * a -> 2 
	 * b -> 3
	 * The word value will be 6 
	 *  
	 * @param word The word to process
	 * @return the numeric value associated to the word
	 */
	public Long getValue(String word) {

		Long wordValue = (long)1;
		
		//1 represent the empty (word = "") or non-existing (word == null) words. They are both invalid
		if(word == null)
			return wordValue;
		
		logger.debug("AnagramUtils - getValue - Calculating the value for the word : " + word);
		
		//The word values is calculated
		for(char singleChar : word.toLowerCase().trim().toCharArray()) {
			wordValue = wordValue * lettersValues.get(singleChar);
		}
		
		return wordValue;
	}
	
	/**
	 * This method process a list of words and split it into anagrams sublists.
	 * 
	 * @param words The list of words of interest
	 * @return A collection containing lists of anagrams
	 */
	public Collection<List<String>> findAnagrams(List<String> words) {
		
		Map<Long, List<String>> valueToAnagrams = new HashMap<Long, List<String>>();
		
		if(words == null) {
			return valueToAnagrams.values();
		}
		
		this.logger.debug("AnagramUtils - findAnagrams - Processing the words");
		
		//The anagrams are spotted comparing the different words values (check method "getValue"). 
		//If two words has the same word value then they are anagrams of each other.
		
		Long currentWordValue = null;
		for(String currentWord : words) {

			currentWordValue = this.getValue(currentWord);
			
			//In case the word value is 1, then the word is invalid (empty or not existing). Thus, is ignored.
			if(currentWordValue == 1)
				continue;
			
			//The words with the same value are grouped.
			if(valueToAnagrams.containsKey(currentWordValue)) {
				valueToAnagrams.get(currentWordValue).add(currentWord);
			}
			// If a new value is found a new group is created
			else {
				List<String> anagrams = new LinkedList<String>();
				anagrams.add(currentWord);
				valueToAnagrams.put(currentWordValue, anagrams);
			}
			
		}
		
		return valueToAnagrams.values();
		
	}
	
	/**
	 * Given a list of strings, this method join them in a single string using the specified separator
	 */
	public String toString(List<String> anagrams, String separator) {
		StringJoiner joiner = new StringJoiner(separator);
		
		//We create a single string which contains all the words through a joiner 
		anagrams.stream().forEach(anagram -> joiner.add(anagram));		
		
		return joiner.toString();
	}
	
}
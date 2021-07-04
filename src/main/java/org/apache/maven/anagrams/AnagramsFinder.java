package org.apache.maven.anagrams;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * The AnagramsFinder class is the core of the program. It reads and processes file containing the words to find the anagrams.
 * 
 * @author Valerio De Dominicis
 * @version 1.0
 */
public class AnagramsFinder {

	private Logger logger = null;
	private AnagramsUtils anagramsUtils = null;
	private FileUtils fileUtils = null;
	
	public static void main(String args[]) throws InvalidAttributesException {
		new AnagramsFinder().find();
	}
	
	/**
	 * @throws InvalidAttributesException if the logger cannot be initialized
	 */
	public AnagramsFinder() throws InvalidAttributesException {
		this.logger = LogManager.getLogger("debugLogger");
		this.anagramsUtils = new AnagramsUtils(logger);
		this.fileUtils = new FileUtils(logger);
	}
	
	private void find() {
		try {
			//The words are fetched from the file and then scanned for finding the potential anagrams
			List<String> words = this.fileUtils.readFile(this.fileUtils.getWordsFilePath());
			Collection<List<String>> anagramGroups = this.anagramsUtils.findAnagrams(words);
			this.printAnagrams(anagramGroups);
		}
		catch(NullPointerException | IOException ex) {
			this.logger.error("AnagramsFinder - start - File not found, execution will be stopped", ex);
		}
	}
	
	private void printAnagrams(Collection<List<String>> anagramsGroups) {
		this.logger.debug("AnagramsFinder - printAnagrams - Found anagrams: ");

		anagramsGroups	.stream()
						//We filter the groups with only 1 element (for whom no anagrams were found)
						.filter((List<String> currentAnagrams) -> currentAnagrams.size() > 1)
						//We iterate on the remaining groups to print the found anagrams
						.forEach(currentAnagrams -> logger.debug(this.anagramsUtils.toString(currentAnagrams, ",")));
		
	}
	
}
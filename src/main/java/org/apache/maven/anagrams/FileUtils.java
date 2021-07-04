package org.apache.maven.anagrams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import javax.naming.directory.InvalidAttributesException;
import org.apache.log4j.Logger;

/**
 * This class is a toolbox to interact with the configuration and the file contaiing the words.
 * 
 * @author Valerio De Dominicis
 * @version 1.0
 */
class FileUtils extends UtilsWithLogger{
	
	private static final String PROPERTIES_FILE_NAME = "anagram.properties";
	private static final String PROPERTIES_PATH = System.getProperty("user.dir");
	
	/**
	 * @throws InvalidAttributesException If the logger is null
	 */
	public FileUtils(Logger logger) throws InvalidAttributesException {
		super(logger);
	}
	
	/**
	 * Returns the name of the file containing the words. This name can be configured in the anagram.properties file
	 * @throws IOException If the .properties file is not found
	 */
	String getFileName() throws IOException {
		logger.debug("FileUtils - getFileName - Looking for the configuration file: " + PROPERTIES_PATH + "/" + PROPERTIES_FILE_NAME);
		Properties configFile = new Properties();
		configFile.load(new FileInputStream(PROPERTIES_PATH + "/" + PROPERTIES_FILE_NAME));
		logger.debug("FileUtils - getFileName - Getting the file name from the configuration file");
		return configFile.getProperty("WORDS_FILE_NAME");
	}
	
	/**
	 * Returns the path of the file containing the words.
	 * @throws IOException If the .properties file is not found
	 */
	public String getWordsFilePath() throws IOException {
		String fileName = this.getFileName();
		logger.debug("FileUtils - getWordFilePath - Getting the word file path " + PROPERTIES_PATH + "/" + fileName);
		return PROPERTIES_PATH + "/" + fileName;
	}
	
	/**
	 * Returns the words read from the file.
	 * 
	 * @param filePath The path of the file containing the words
	 * @return A list of string representing the words read from the file
	 * @throws FileNotFoundException If the words file is not found
	 * @throws NullPointerException If the filePath is null
	 */
	public List<String> readFile(String filePath) throws FileNotFoundException, NullPointerException {
        List<String> words = new LinkedList<String>();
        
        logger.debug("FileUtils - readFile - Reading the words in the file " + filePath);
        Scanner s = new Scanner(new File(filePath));
        while (s.hasNext()) {
            words.add(s.next());
        }
        s.close();
        
		return words;
	}

	//------------------- GETTERS AND SETTERS
	
	public String getPropertiesPath() {
		return PROPERTIES_PATH;
	}
	
}

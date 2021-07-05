package org.apache.maven.anagrams;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.junit.Before;
import org.junit.Test;

/**
 * @author valerio
 * @version 1.0
 */
public class FileUtilsTest {
	
	private FileUtils fileUtils = null;
	
	@Before
	public void setUp() throws Exception {
		fileUtils = new FileUtils(LogManager.getLogger("testLogger"));
	}
	
	/**
	 * Test if the words file name is not null
	 */
	@Test
	public void getFileName_fileNameIsNotNull() throws IOException {
		assertNotNull(fileUtils.getFileName());
	}
	
	/**
	 * Test if the words file path is not null
	 */
	@Test
	public void getWordsFilePath_wordsFilePathIsNotNull() throws IOException {
		assertNotNull(fileUtils.getWordsFilePath()); 
	}
	
	/**
	 * Test if the fileUtils instance throws an exception in case the words file does not exist
	 * @throws FileNotFoundException This exception is expected to be thrown because a wrong file name is used
	 */
	@Test (expected = FileNotFoundException.class)
	public void readFile_wordsFileDoesNotExist() throws FileNotFoundException  {
		String fakeFilePath = fileUtils.getPropertiesPath() + "/fakeFile.txt";
		fileUtils.readFile(fakeFilePath);
	}
	
	/**
	 * Test if the fileUtils instance throws an exception in case the word file is null
	 */
	@Test (expected = NullPointerException.class)
	public void readFile_wordsFileIsNull() throws FileNotFoundException {
			fileUtils.readFile(null); 
	}
	
	/**
	 * Test if the words are read properly from the file
	 */
	@Test
	public void readFile_wordsFileIsRead() throws FileNotFoundException, NullPointerException, IOException  {
		List<String> readWords = fileUtils.readFile(fileUtils.getWordsFilePath());
		assertNotNull(readWords);
		assertNotEquals(0, readWords.size());
	}
	
}

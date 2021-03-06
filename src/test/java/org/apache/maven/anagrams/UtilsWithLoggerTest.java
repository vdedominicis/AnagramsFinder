package org.apache.maven.anagrams;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.LogManager;
import org.junit.Test;

/**
 * @author valerio
 * @version 1.0
 */
public class UtilsWithLoggerTest {

	/**
	 * In case no logger is provided, an exception should be thrown
	 */
	@Test (expected = InvalidAttributesException.class)
	public void constructor_exceptionThrownIfNoLoggerProvided() throws InvalidAttributesException {
		new UtilsWithLogger(null);
	}
	
	/**
	 * If the logger is provided, then no exceptions should be thrown
	 */
	@Test
	public void constructor_noExceptionsThrownIfLoggerProvided() throws InvalidAttributesException {
		new UtilsWithLogger(LogManager.getLogger("testLogger"));
	}
	
}

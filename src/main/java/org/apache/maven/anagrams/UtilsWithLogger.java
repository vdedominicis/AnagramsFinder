package org.apache.maven.anagrams;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

class UtilsWithLogger {
	
	Logger logger = null;
	
	public UtilsWithLogger(Logger logger) throws InvalidAttributesException {
		if(logger == null) {
			throw new InvalidAttributesException("The logger cannot be null");
		}
		
		this.logger = logger;
	}
}

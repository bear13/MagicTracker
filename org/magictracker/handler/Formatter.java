package org.magictracker.handler;

import java.security.AccessController;

import org.magictracker.processor.TrackRecord;

import sun.security.action.GetPropertyAction;

public abstract class Formatter {
	
	protected String lineSeparator = (String)AccessController.doPrivileged(new GetPropertyAction("line.separator"));
	public abstract String format(TrackRecord record);
	
	public String getLineSeparator() {
		return lineSeparator;
	}
	
	protected String getClassNameWithoutPackage(String className){
		
		int index = className.lastIndexOf(".");
		int length = className.length();
		String name = className.substring(index+1,length);
		
		return name;
	}
	
}

package org.magictracker.processor;

import java.io.Serializable;

public class TrackRecord implements Serializable{
	
	private String clazzName;
	private String methodName;
	private String content;
	private String lineNumber;
	private String trackType;
	private int titleLevel;
	
	public TrackRecord(String type,String content,String methodName,String className,String lineNumber){
		this.trackType = type;
		this.content = content;
		this.clazzName = className;
		this.methodName = methodName;
		this.lineNumber = lineNumber;
	}
	
	public TrackRecord(String type,String content,String methodName,String className,String lineNumber,int titleLevel){
		this.trackType = type;
		this.content = content;
		this.clazzName = className;
		this.methodName = methodName;
		this.lineNumber = lineNumber;
		this.titleLevel = titleLevel;
	}

	public String getClazzName() {
		return clazzName;
	}

	public String getContent() {
		return content;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public String getMethodName() {
		return methodName;
	}

	public int getTitleLevel() {
		return titleLevel;
	}

	public String getTrackType() {
		return trackType;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setTitleLevel(int titleLevel) {
		this.titleLevel = titleLevel;
	}

	public void setTrackType(String trackType) {
		this.trackType = trackType;
	}
	
	

}

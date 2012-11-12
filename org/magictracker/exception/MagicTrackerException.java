package org.magictracker.exception;

public class MagicTrackerException extends RuntimeException{
	
	protected Throwable cause = null;
	
	public MagicTrackerException(){
		
	}
	
	public MagicTrackerException(String msg){
		super(msg);
	}
	
	public MagicTrackerException(Throwable cause){
		this((cause == null)?null:cause.toString(),cause);
	}
	
	public MagicTrackerException(String msg,Throwable cause){
		super(msg + " (Caused by "+cause+")");
		this.cause = cause;
	}
	
	public Throwable getCause(){
		return this.cause;
	}

}

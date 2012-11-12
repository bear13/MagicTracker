package org.magictracker;

public interface Tracker {
	
	public void r(String content);
	public void begin(String title);
	public void begin(String title,String content);
	public void stop();
	public void stop(String content);
	public void reportList();

}

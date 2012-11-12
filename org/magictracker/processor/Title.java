package org.magictracker.processor;

public class Title {
	
	private String title;
	private int level;
	
	public Title(String title){
		this.title = title;
	}
	
	public Title(String title,int level){
		this.title = title;
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public String getTitle() {
		return title;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	

}

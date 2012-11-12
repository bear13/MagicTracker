package org.magictracker.conf;

public class HandlerConfig {
	
	private String id; //Œ®“ª±Í ∂
	private String clazzName;
	private String formatterName;
	private String savePath;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClazzName() {
		return clazzName;
	}
	public String getFormatterName() {
		return formatterName;
	}
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}
	public void setFormatterName(String formatterName) {
		this.formatterName = formatterName;
	}
	
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	

}

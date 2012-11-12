package org.magictracker.handler;

import org.magictracker.processor.TrackRecord;

public abstract class Handler {
	
	protected String id;
	protected String clazzName;
	//protected String formatterName;
	
	protected Formatter formatter;
	
	public Handler(){}
	
	public abstract void publish(TrackRecord record);
	
	/**
	 * 
	 * 实例化后被调用(ProcessorManager loadHandler())
	 *
	 */
	/*
	public void configHandler(){
		
		if(formatterName.equals("") || formatterName == null){
			System.err.println("no formatter name");
			return;
		}else{
			try{
				Class clazz = Class.forName(formatterName);
				this.formatter = (Formatter)clazz.newInstance();
			}catch(ClassNotFoundException ce){
				System.err.println("can not find formatter "+formatterName);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}*/

	public Formatter getFormatter() {
		return formatter;
	}

	public void setFormatter(Formatter formatter) {
		this.formatter = formatter;
	}

	public String getClazzName() {
		return clazzName;
	}

	public String getId() {
		return id;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	

}

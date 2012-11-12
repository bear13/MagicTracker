package org.magictracker.conf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.magictracker.handler.Handler;

public class Configuration {
	
	//private String defaultConfig;
	//private String definedConfig;
	//private boolean hasConfig = false;
	private Map<String,Handler> handlers = new HashMap<String,Handler>();
	//private Map<String,HandlerConfig> handlerConfigMap = new HashMap<String,HandlerConfig>();
	private List<VisibleConfig> visiableList = new ArrayList<VisibleConfig>(); 
	
	public Configuration(){
		/*this.defaultConfig = defaultConfig;
		this.definedConfig = configFileName;
		readConfiguration();*/
	}
	/*
	private void readConfiguration(){
		
		if(!this.hasConfig){
			synchronized(this){
				File defaultFile = new File(defaultConfig);
				File definedFile = new File(definedConfig);
				
				if(!defaultFile.exists()){
					System.err.println(defaultConfig+" is missing");
					return;
				}else{
					readDefault(defaultFile);
				}
				
				if(definedFile.exists()){
					readDefined(definedFile);
				}
			}
		}
	}
	
	private void readDefault(File f){
		
		Digester d = new Digester();
		
	}
	
	private void readDefined(File f){
		
	}
	public void addHandlerConfig(HandlerConfig handlerConfig){
		addHandlerConfig(handlerConfig.getId(),handlerConfig);
	}
	
	public void addHandlerConfig(String key,HandlerConfig handlerConfig){
		if(this.handlerConfigMap.containsKey(key)){
			this.handlerConfigMap.remove(key);
		}
		this.handlerConfigMap.put(key,handlerConfig);
	}
	
	public void putAllHandlerConfig(Map<String,HandlerConfig> newHandlerConfigList){
		this.handlerConfigMap.putAll(newHandlerConfigList);
	}
	public Map<String,HandlerConfig> getHandlerConfigMap() {
		return handlerConfigMap;
	}
	*/
	
	public void addVisible(VisibleConfig name){
		this.visiableList.add(name);
	}
	
	public void addAllVisible(List<VisibleConfig> newVisibleList){
		this.visiableList.addAll(newVisibleList);
	}

	public List<VisibleConfig> getVisiableList() {
		return visiableList;
	}
	
	public Map<String,Handler> getHandlers(){
		return this.handlers;
	}
	
	public void putHandler(Handler handler){
		String key = handler.getId();
		if(this.handlers.containsKey(key)){
			this.handlers.remove(key);
		}
		this.handlers.put(key,handler);
	}
	
	public void putAllHandler(Map<String,Handler> newHandlers){
		this.handlers.putAll(newHandlers);
	}
	

}

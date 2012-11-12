package org.magictracker.impl;

import java.io.Serializable;
import java.util.List;

import org.magictracker.Tracker;
import org.magictracker.conf.VisibleConfig;
import org.magictracker.processor.Processor;
import org.magictracker.processor.ProcessorManager;
import org.magictracker.processor.Title;
import org.magictracker.processor.TrackType;

public class DefaultTracker implements Tracker,Serializable{
	
	private String target;
	private Processor processor;
	private String tempClazzName;
	private String tempMethodName;
	private String tempLineNumber;
	
	public DefaultTracker(String targetName){
		this.target = targetName;
		this.processor = getProcessor();
	}

	public void r(String content) {
		
		Processor p = getProcessor();
		prepare();
		p.trackp(TrackType.NORMAL,content,this.tempMethodName,this.tempClazzName,this.tempLineNumber);
		
	}
	
	public void begin(String title){
		
		Processor processor = getProcessor();
		addTitleToStack(title);
		
		prepare();
		processor.trackp(TrackType.BEGIN,"",this.tempMethodName,this.tempClazzName,this.tempLineNumber);
		
	}

	public void begin(String title, String content) {
		
		Processor processor = getProcessor();
		addTitleToStack(title);
		
		prepare();
		processor.trackp(TrackType.BEGIN,content,this.tempMethodName,this.tempClazzName,this.tempLineNumber);
		
	}

	public void stop() {
		
		Processor processor = getProcessor();
		prepare();
		processor.trackp(TrackType.STOP,"",this.tempMethodName,this.tempClazzName,this.tempLineNumber);
		
		removeTitleFromStack();
	}

	public void stop(String content) {
		
		Processor processor = getProcessor();
		prepare();
		processor.trackp(TrackType.STOP,content,this.tempMethodName,this.tempClazzName,this.tempLineNumber);
		
		removeTitleFromStack();
		
	}

	public void reportList() {
		
		Processor processor = getProcessor();
		List list = processor.getManager().getTitleNameList();
		
		prepare();
		processor.trackp(TrackType.TITLES,list.toString(),this.tempMethodName,this.tempClazzName,this.tempLineNumber);
		
		
	}
	
	private Processor getProcessor(){
		if(this.processor != null){
			return this.processor;
		}else{
			this.processor = ProcessorManager.getProcessorManager().createProcessor(this.target);
		}
		return this.processor;
	}
	
	private void prepare(){
		
		Throwable t = new Throwable();
		StackTraceElement[] ste = t.getStackTrace();
		
		if(ste != null && ste.length > 2){
			StackTraceElement caller = ste[2];
			this.tempClazzName = caller.getClassName();
			this.tempMethodName = caller.getMethodName();
			this.tempLineNumber = String.valueOf(caller.getLineNumber());
		}
		
	}
	
	private void addTitleToStack(String titleName){
		
		ProcessorManager manager = this.processor.getManager();
		manager.getTitleNameList().add(titleName);
		
		Title currentTitle = new Title(titleName);
		
		List<VisibleConfig> visibleTitle = ProcessorManager.getConfig().getVisiableList();
		for(VisibleConfig v : visibleTitle){
			String visibleName = v.getName();
			if(titleName.equals(visibleName)){
				int currentTitleLevel = manager.getTitleLevel() + 1;
				currentTitle.setLevel(currentTitleLevel);
				manager.setTitleLevel(currentTitleLevel);
				break;
			}else{
				currentTitle.setLevel(0);
			}
		}
		
		manager.addTitleToStack(currentTitle);
	
	}
	
	private void removeTitleFromStack(){
		
		ProcessorManager manager = this.processor.getManager();
		
		Title currentTitle = manager.getTitleFromStack();
		String titleName = currentTitle.getTitle();
		
		List<VisibleConfig> visibleTitle = ProcessorManager.getConfig().getVisiableList();
		for(VisibleConfig v : visibleTitle){
			if(titleName.equals(v.getName())){
				int currentTitleLevel = manager.getTitleLevel() - 1;
				//System.out.println("*** "+currentTitleLevel);
				manager.setTitleLevel(currentTitleLevel);
				break;
			}
		}
		
		manager.removeTitleFromStack();
		
	}

}

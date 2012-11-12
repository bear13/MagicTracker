package org.magictracker.processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.magictracker.conf.VisibleConfig;
import org.magictracker.handler.Handler;
import org.magictracker.processor.TrackRecord;
import org.magictracker.processor.ProcessorManager;
import org.magictracker.processor.Title;

public class Processor {
	
	private String target;
	private ProcessorManager manager;
	//private List<Handler> handlers = new ArrayList<Handler>();
	
	public Processor(String target){
		this.target = target;
	}
	
	public void trackp(String type,String content,String methodName,String className,String lineNumber){
		
		if(this.manager == null){
			this.manager = ProcessorManager.getProcessorManager();
		}
		int titleStackSize = this.manager.getTitleStack().size();
		
		if(titleStackSize > 0){
			Title title = this.manager.getTitleStack().peek(); //Õ»¶¥³öÕ»
			String titleName = title.getTitle();
			int titleLevel = title.getLevel();
			
			List<VisibleConfig> visibles = ProcessorManager.getConfig().getVisiableList();
			for(VisibleConfig visible : visibles){
				String visibleName = visible.getName();
				if(visibleName.equals(titleName)){
					TrackRecord tr = new TrackRecord(type,content,methodName,className,lineNumber,titleLevel);
					//mr.setMarkerTarget(this.target);
					track(tr);
					break;
				}
			}
		}else{
			TrackRecord tr = new TrackRecord(type,content,methodName,className,lineNumber);
			//mr.setMarkerTarget(this.target);
			track(tr);
		}
		
	}
	
	private void track(TrackRecord trackRecord){
		/*
		if(this.handlers.size() > 0){
			for(Handler handler : handlers){
				handler.publish(trackRecord);
			}
		}else{
			System.err.println("No handler defined");
		}*/
		if(this.manager == null){
			this.manager = ProcessorManager.getProcessorManager();
		}
		
		Map<String,Handler> handlers = this.manager.getConfig().getHandlers();
		if(handlers.size() > 0){
			Iterator it = handlers.values().iterator();
			while(it.hasNext()){
				Handler handler = (Handler)it.next();
				handler.publish(trackRecord);
			}
		}else{
			System.err.println("No handler defined");
		}
		
		
		
	}

	public ProcessorManager getManager() {
		return manager;
	}

	public String getTarget() {
		return target;
	}

	public void setManager(ProcessorManager manager) {
		this.manager = manager;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}

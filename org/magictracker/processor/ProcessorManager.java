package org.magictracker.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.digester.Digester;
import org.magictracker.conf.ConfigRuleSetOlder;
import org.magictracker.conf.ConfigRuleSet;
import org.magictracker.conf.Configuration;
import org.magictracker.conf.HandlerConfig;
import org.magictracker.conf.VisibleConfig;
import org.magictracker.handler.Formatter;
import org.magictracker.handler.Handler;

import org.xml.sax.SAXException;

public class ProcessorManager {
	
	private static ProcessorManager manager;
	private static Configuration config;
	private static String currentTime;
	private String DEFAULT_CONFIG_FILE = "org/magictracker/default-config.xml";
	private String DEFINED_CONFIG_FILE = "tracker-config.xml";
	private static boolean hasConfig = false;
	//key 和 value(Processor) 之间是虚引用
	private Hashtable<String,WeakReference<Processor>> processorRefs = new Hashtable<String,WeakReference<Processor>>();
	private Stack<Title> titleStack = new Stack<Title>();
	private List<String> titleNameList = new ArrayList<String>();
	private int titleLevel = 0;
	
	static{
		if(manager == null){
			manager = new ProcessorManager();
		}
		if(currentTime == null){
			Date d = new Date(System.currentTimeMillis());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
			currentTime = df.format(d);
		}
	}
	
	public static ProcessorManager getProcessorManager(){
		if(manager != null){
			manager.readConfiguration();
		}else{
			manager = new ProcessorManager();
			manager.readConfiguration();
		}
		return manager;
	}
	
	/**
	 * 
	 * 读取配置文件进行配置
	 * 
	 * @author BearBear
	 * 
	 * 
	 * catch FileNotFoundException
	 * 
	 */
	private void readConfiguration(){
		
		if(!hasConfig){
			synchronized(this){
				
				
				
				Digester d = new Digester();
				d.push(this);
				d.addRuleSet(new ConfigRuleSet());
				d.addSetNext("tracker-config","addConiguration");
				try {
					
					d.parse(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE));

				} catch (FileNotFoundException e){
					System.err.println("[Error]默认配置文件 "+DEFAULT_CONFIG_FILE+" 丢失");
					return;
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				}
				//System.out.println("*************");
				readDefinedConfiguration(DEFINED_CONFIG_FILE);
				hasConfig = true;
				
			}
		}
		
		
		
	}
	
	/**
	 * 
	 * 读取配置文件进行配置
	 * 
	 * catch MalformedURLException
	 * 
	 */
	private void readDefinedConfiguration(String configName){
		
		Configuration definedConfig = null;
		Digester d = new Digester();
		d.addRuleSet(new ConfigRuleSet());
		try {
			definedConfig = (Configuration)d.parse(this.getClass().getClassLoader().getResourceAsStream(configName));
			//show(definedConfig);
			
		} catch (MalformedURLException e){
			System.out.println("[Info]用户自定义配置文件 "+DEFINED_CONFIG_FILE+" 不存在,仅使用默认配置");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//合拼两个 Configuration 的两个List属性
		if(definedConfig != null){
			List vl = definedConfig.getVisiableList();
			Map hl = definedConfig.getHandlers();
			if(vl.size() > 0){
				this.config.addAllVisible(vl);
			}
			if(hl.size() > 0){
				this.config.putAllHandler(hl);
			}
		}
		
	}
	
	public void addConiguration(Configuration config){
		if(ProcessorManager.config == null){
			ProcessorManager.config = config;
		}
	}
	
	public Processor createProcessor(String target){
		
		Processor p = getProcessor(target);
		if(p == null){
			p = new Processor(target);
			addProcessor(p);
			p = getProcessor(target);
		}
		return p;
	}
	
	public synchronized Processor getProcessor(String target){
		
		WeakReference<Processor> ref = this.processorRefs.get(target);
		if(ref == null){
			return null;
		}
		
		Processor p = (Processor)ref.get();
		if(p == null){
			this.processorRefs.remove(target);
		}
			
		return p;
		
	}
	
	public synchronized boolean addProcessor(Processor p){
		
		String targetName = p.getTarget();
		if(targetName == null || targetName.equals("")){
			throw new NullPointerException("targetName is not exist");
		}
		WeakReference<Processor> ref = this.processorRefs.get(targetName);
		if(ref != null){
			if(ref.get() == null){
				this.processorRefs.remove(targetName);
			}else{
				return false;
			}
		}
		
		processorRefs.put(targetName,new WeakReference(p));
		
		return true;
	}
	
	private void show(Configuration conf){
		
		List<VisibleConfig> lv = conf.getVisiableList();
		Map handlers = conf.getHandlers();
		for(VisibleConfig v : lv){
			System.out.println(v.getName());
		}
		
		Set entrySet = handlers.entrySet();
		Iterator it = entrySet.iterator();
		
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry)it.next();
			Handler handler = (Handler)entry.getValue();
			String handlerName = handler.getClazzName();
			Formatter formatter = handler.getFormatter();
			System.out.println(entry.getKey()+","+handlerName+", **"+formatter.getClass().getName());
		}
	}
	
	public Stack<Title> getTitleStack(){
		return this.titleStack;
	}
	
	public Title getTitleFromStack() {
		return titleStack.peek(); //出栈(不移除)
	}
	
	public void addTitleToStack(Title title){
		this.titleStack.push(title); //入栈
	}
	
	public void removeTitleFromStack(){
		this.titleStack.pop(); //出栈并移除
	}

	public static void main(String[] args){
		ProcessorManager pm = new ProcessorManager();
		pm.readConfiguration();
		if(config != null){
			System.out.println("exist");
			pm.show(config);
		}
	}

	public static Configuration getConfig() {
		return config;
	}

	public List<String> getTitleNameList() {
		return titleNameList;
	}

	public void addTitleNameList(String titleName) {
		this.titleNameList.add(titleName);
	}

	public int getTitleLevel() {
		return titleLevel;
	}

	public void setTitleLevel(int titleLevel) {
		this.titleLevel = titleLevel;
	}

	public static String getCurrentTime() {
		return currentTime;
	}

	public static void setCurrentTime(String currentTime) {
		ProcessorManager.currentTime = currentTime;
	}
	
	
	
	

}

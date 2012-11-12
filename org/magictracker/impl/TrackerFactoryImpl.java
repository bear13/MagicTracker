package org.magictracker.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Hashtable;

import org.magictracker.Tracker;
import org.magictracker.TrackerFactory;
import org.magictracker.exception.MagicTrackerException;

public class TrackerFactoryImpl extends TrackerFactory{
	
	private Hashtable<String,Tracker> instances = new Hashtable<String,Tracker>();
	private String DEFAULT_TRACKER = "org.magictracker.impl.DefaultTracker";
	private String TRACKER_INTERFACE = "org.magictracker.Tracker";
	private Constructor trackerConstructor;
	private Class[] trackerConstructorSinature = {String.class};
	
	//private TrackerFactoryImpl(){}

	@Override
	public Tracker getInstance(Class targetClazz) throws MagicTrackerException {
		return getInstance(targetClazz.getName());
	}

	@Override
	public Tracker getInstance(String targetName) throws MagicTrackerException {
		
		Tracker tracker = (Tracker)this.instances.get(targetName);
		
		if(tracker == null){
			tracker = newInstance(targetName);
			this.instances.put(targetName,tracker);
		}
		return tracker;
	}
	
	private Tracker newInstance(String targetName) throws MagicTrackerException {
		
		Tracker instance = null;
		try{
			instance = (Tracker)getTrackerConstructor().newInstance(targetName);
			return instance;
			
		}catch(InvocationTargetException e){
			Throwable c = e.getTargetException();
			if(c != null){
				throw new MagicTrackerException(c);
			}
			throw new MagicTrackerException(e);
		}catch(Throwable t){
			throw new MagicTrackerException(t);
		}
	}
	
	private Constructor getTrackerConstructor() throws MagicTrackerException {
		
		if(this.trackerConstructor != null){
			return this.trackerConstructor;
		}else{
			
			Class interfaceClazz = null;
			Class trackerClazz = null;
			
			try{
				interfaceClazz = this.getClass().getClassLoader().loadClass(this.TRACKER_INTERFACE);
				trackerClazz = loadTrackerClazz(this.DEFAULT_TRACKER);
				
				if(trackerClazz == null){
					throw new MagicTrackerException("Tracker Class is null -- from : TrackerFactoryImpl.getTrackerConstructor()");
				}
				
				// 如果 trackerClazz 没有实现 interfaceClazz 接口
				if(!interfaceClazz.isAssignableFrom(trackerClazz)){
					Class[] interfaces = trackerClazz.getInterfaces();
					for(Class i : interfaces){
						if(i.equals(this.TRACKER_INTERFACE)){
							throw new MagicTrackerException("Invalid class loader hierarchy -- from : TrackerFactoryImpl.getTrackerConstructor()");
						}
					}
				}
			}catch(Throwable t){
				throw new MagicTrackerException(t);
			}
			
			try{
				this.trackerConstructor = trackerClazz.getConstructor(this.trackerConstructorSinature);
				return this.trackerConstructor;
			}catch(Throwable t){
				throw new MagicTrackerException("No suitable Log constructor "+this.trackerConstructorSinature+" for "+this.DEFAULT_TRACKER,t);
			}
			
		}
	}
	
	private Class loadTrackerClazz(final String clazzName) throws ClassNotFoundException {
		
		Object result = AccessController.doPrivileged(new PrivilegedAction<Object>(){

			public Object run() {
				ClassLoader loader = TrackerFactoryImpl.getThreadClazzLoader();
				if(loader != null){
					try{
						return loader.loadClass(clazzName);
					}catch(ClassNotFoundException cnfe){
						try{
							Class.forName(clazzName);
						}catch(ClassNotFoundException cnfee){
							System.err.println("ClassNotFoundException -- from : TrackerFactoryImpl.loadTrackerClazz()");
							return cnfee;
						}
					}
				}
				return null;
			}
		});
		
		if(result instanceof ClassNotFoundException){
			throw (ClassNotFoundException)result;
		}
		
		return (Class)result;
		
	}

}

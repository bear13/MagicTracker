package org.magictracker;

import java.util.Hashtable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.magictracker.exception.MagicTrackerException;

public abstract class TrackerFactory {
	
	private static String TRACKER_FACTORY_IMPL = "org.magictracker.impl.TrackerFactoryImpl"; 
	private static Hashtable factories = new Hashtable();
	
	public abstract Tracker getInstance(Class targetClazz) throws MagicTrackerException;
	public abstract Tracker getInstance(String targetName) throws MagicTrackerException;
	
	public static Tracker getTracker(Class targetClazz) throws MagicTrackerException {
		Tracker tracker = getFactory().getInstance(targetClazz);
		return tracker;
	}
	
	private static TrackerFactory getFactory() throws MagicTrackerException {
		
		ClassLoader loader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction<Object>(){

			public Object run() {
				// TODO Auto-generated method stub
				return TrackerFactory.getThreadClazzLoader();
			}	
		});
		
		TrackerFactory factory = getExistFactory(loader);
		if(factory != null){
			return factory;
		}else{
			factory = newFactory(TrackerFactory.TRACKER_FACTORY_IMPL,TrackerFactory.class.getClassLoader());
		}
		
		if(factory != null){
			saveFactory(loader,factory);
		}
		return factory;
	}
	
	protected static ClassLoader getThreadClazzLoader() throws MagicTrackerException {
		
		ClassLoader loader = null;
		
		try{
			Method method = Thread.class.getMethod("getClassLoader()",null);
			try{
				loader = (ClassLoader)method.invoke(Thread.currentThread(),null);
			}catch(IllegalAccessException iae){
				throw new MagicTrackerException(iae);
			}catch(InvocationTargetException ite){
				throw new MagicTrackerException(ite);
			}
			
		}catch(NoSuchMethodException e){
			loader = TrackerFactory.class.getClassLoader();
		}
		return loader;
		
	}
	
	private static TrackerFactory getExistFactory(ClassLoader loader){
		
		if(loader == null){
			System.err.println("ClassLoader is null -- from : TrackerFactory.getExistFactory()");
			return null;
		}
		return (TrackerFactory)factories.get(loader);
		
	}
	
	@SuppressWarnings("unchecked")
	private static void saveFactory(ClassLoader loader,TrackerFactory factory){
		
		if(loader != null && factory != null){
			factories.put(loader,factory);
		}else{
			System.err.println("ClassLoader and TrackerFactory are null -- from : TrackerFactory.saveFactory()");
		}
		
	}
	
	private static TrackerFactory newFactory(final String clazzName,final ClassLoader loader){
		
		Object result = AccessController.doPrivileged(new PrivilegedAction<Object>(){

			public Object run() {
				Class clazz = null;
				if(loader != null){
					try{
						clazz = loader.loadClass(clazzName);
						return (TrackerFactory)clazz.newInstance();
						
					}catch(ClassNotFoundException cnfe){
						System.err.println("ClassNotFoundException -- from : TrackerFactory.newFactory()");
					}catch(NoClassDefFoundError ncdfe){
						System.err.println("NoClassDefFoundError -- from : TrackerFactory.newFactory()");
					}catch(ClassCastException cce){
						System.err.println("ClassCastException -- from : TrackerFactory.newFactory()");
					}catch(InstantiationException e) {
						System.err.println("InstantiationException -- from : TrackerFactory.newFactory()");
						e.printStackTrace();
					}catch(IllegalAccessException e) {
						System.err.println("IllegalAccessException -- from : TrackerFactory.newFactory()");
						e.printStackTrace();
					}
				}
				return new MagicTrackerException();
			}	
		});
		
		if(result instanceof MagicTrackerException){
			throw (MagicTrackerException)result;
		}
		return (TrackerFactory)result;
	}

}

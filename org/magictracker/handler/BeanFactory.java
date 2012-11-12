package org.magictracker.handler;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.xml.sax.Attributes;

public class BeanFactory extends AbstractObjectCreationFactory{

	@Override
	public Object createObject(Attributes attr) throws Exception {
		
		Object bean = null;
		String className = attr.getValue("clazzName");
		if(className != null){
			bean = getInstance(className);
		}
		return bean;
	}
	
	private Object getInstance(String className){
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Object obj = null;
		try {
			
			obj = loader.loadClass(className).newInstance();
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}

}

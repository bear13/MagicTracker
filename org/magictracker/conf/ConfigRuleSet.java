package org.magictracker.conf;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;
import org.magictracker.handler.BeanFactory;

public class ConfigRuleSet extends RuleSetBase{

	@Override
	public void addRuleInstances(Digester d) {
		
		d.addObjectCreate("tracker-config",Configuration.class);
		
		d.addObjectCreate("tracker-config/visible-list/name",VisibleConfig.class);
		d.addBeanPropertySetter("tracker-config/visible-list/name");
		d.addSetNext("tracker-config/visible-list/name","addVisible");
		
		/*
		d.addObjectCreate("tracker-config/handlers/handler",HandlerConfig.class);
		d.addSetProperties("tracker-config/handlers/handler");	
		d.addBeanPropertySetter("tracker-config/handlers/handler/formatter","formatterName");
		d.addBeanPropertySetter("tracker-config/handlers/handler/path","savePath");
		d.addSetNext("tracker-config/handlers/handler","addHandlerConfig");*/
		
		d.addFactoryCreate("tracker-config/handlers/handler",new BeanFactory());
		d.addSetProperties("tracker-config/handlers/handler");
		//d.addBeanPropertySetter("tracker-config/handlers/handler/formatter","formatterName");
		
		d.addFactoryCreate("tracker-config/handlers/handler/formatter",new BeanFactory());
		d.addSetProperties("tracker-config/handlers/handler/formatter");
		d.addSetNext("tracker-config/handlers/handler/formatter","setFormatter","org.magictracker.handler.Formatter");
		
		d.addSetNext("tracker-config/handlers/handler","putHandler","org.magictracker.handler.Handler");
		
		
		
		
		
		
	}

}

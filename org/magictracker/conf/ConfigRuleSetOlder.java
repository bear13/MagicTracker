package org.magictracker.conf;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class ConfigRuleSetOlder extends RuleSetBase{

	@Override
	public void addRuleInstances(Digester d) {
		
		d.addObjectCreate("tracker-config",Configuration.class);
		
		//d.addCallMethod("tracker-config/visible-list","addVisible",1);
		//d.addCallParam("tracker-config/visible-list/name",0);
		d.addObjectCreate("tracker-config/visible-list/name",VisibleConfig.class);
		d.addBeanPropertySetter("tracker-config/visible-list/name");
		d.addSetNext("tracker-config/visible-list/name","addVisible");
		
		d.addObjectCreate("tracker-config/handlers/handler",HandlerConfig.class);
		d.addSetProperties("tracker-config/handlers/handler");	
		d.addBeanPropertySetter("tracker-config/handlers/handler/formatter","formatterName");
		d.addBeanPropertySetter("tracker-config/handlers/handler/path","savePath");
		d.addSetNext("tracker-config/handlers/handler","addHandlerConfig");
		
		
		
		
	}

}

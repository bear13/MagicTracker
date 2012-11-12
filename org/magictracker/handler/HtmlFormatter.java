package org.magictracker.handler;

import org.magictracker.processor.TrackRecord;
import org.magictracker.processor.TrackType;

public class HtmlFormatter extends Formatter{

	@Override
	public String format(TrackRecord record) {
		
		StringBuffer sb = new StringBuffer();
		String content = record.getContent();
		String className = getClassNameWithoutPackage(record.getClazzName());
		String methodName = record.getMethodName();
		String lineNumber = record.getLineNumber();
		int titleLevel = record.getTitleLevel();
		String trackType = record.getTrackType();
		
		if(!trackType.equals(TrackType.STOP)){
			if(trackType.equals(TrackType.BEGIN)){
				sb.append("<div class=\"begin_div\">");
			}else{
				sb.append("<div class=\"simple_div\">");
			}
			
		}
		/*
		if(titleLevel > 0){
			for(int i = 0;i<titleLevel;i++){
				sb.append("  ");
			}
		}*/
		
		if(trackType.equals(TrackType.BEGIN)){
			sb.append("<div class=\"title_div\">");
		}else if(trackType.equals(TrackType.STOP)){
			//sb.append("©¸ ");
			sb.append(content);
			sb.append("</div>");

			return sb.toString();
		}else if(trackType.equals(TrackType.NORMAL)){
			//sb.append("©¦ ");
			
		}else if(trackType.equals(TrackType.TITLES)){
			sb.append("*** *** All titles : "+content+" *** ***");
			sb.append(lineSeparator);
			return sb.toString();
		}
		
		sb.append(content);
		/*
		if(!trackType.equals(TrackType.BEGIN)){
			sb.append("</div>");
		}else{
			sb.append("</div>");
		}*/
		
		if(trackType.equals(TrackType.NORMAL) || trackType.equals(TrackType.BEGIN)){
			sb.append("<span class=\"info_span\">");
			sb.append("        --- ");
			sb.append(className+" ");
			sb.append(methodName+"() ");
			sb.append("line:"+lineNumber);
			sb.append("</span>");
		}
		sb.append("</div>");
		sb.append(lineSeparator);
		
		return sb.toString();
	}

}

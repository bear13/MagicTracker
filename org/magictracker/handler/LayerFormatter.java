package org.magictracker.handler;

import org.magictracker.processor.TrackRecord;
import org.magictracker.processor.TrackType;

public class LayerFormatter extends Formatter{

	@Override
	public String format(TrackRecord record) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		String content = record.getContent();
		String className = getClassNameWithoutPackage(record.getClazzName());
		String methodName = record.getMethodName();
		String lineNumber = record.getLineNumber();
		String trackType = record.getTrackType();
		int titleLevel = record.getTitleLevel();
		
		if(titleLevel > 0){
			for(int i = 0;i<titleLevel;i++){
				sb.append("  ");
			}
		}
		
		if(trackType.equals(TrackType.BEGIN)){
			sb.append("©° ");
		}else if(trackType.equals(TrackType.STOP)){
			sb.append("©¸ ");
			sb.append(content);
			sb.append(lineSeparator);
			return sb.toString();
		}else if(trackType.equals(TrackType.NORMAL)){
			sb.append("©¦ ");
		}else if(trackType.equals(TrackType.TITLES)){
			sb.append("*** *** All titles : "+content+" *** ***");
			sb.append(lineSeparator);
			return sb.toString();
		}
		
		sb.append(content);
		sb.append("        --- ");
		sb.append(className+".");
		sb.append(methodName+"().");
		sb.append(lineNumber);
		
		sb.append(lineSeparator); //»»ÐÐ
		
		return sb.toString();
	}

}

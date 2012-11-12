package org.magictracker.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.magictracker.processor.ProcessorManager;
import org.magictracker.processor.TrackRecord;

public class TxtHandler extends Handler{
	
	protected final String type = ".txt";
	protected String path;
	private File file;
	private Writer writer;
	
	public TxtHandler(){
	}

	@Override
	public void publish(TrackRecord record) {
		// TODO Auto-generated method stub
		if(this.file == null){
			
			initOutput();
			
		}
		
		String msg = null;
		try{
			msg = getFormatter().format(record);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		try{
			
			if(writer != null){
				writer.write(msg);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		flush();
		
	}
	
	private void initOutput(){
		
		String fileName = this.path + ProcessorManager.getCurrentTime() + this.type;
		this.file = new File(fileName);
		try {
			flushAndClose();
			this.writer = new FileWriter(file,true);
		} catch(FileNotFoundException fe){
			System.err.println("找不到输出路径");
			fe.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private synchronized void flushAndClose() throws SecurityException{
		
		if(this.writer != null){
			try {
				this.writer.flush();
				this.writer.close();
			} catch (IOException e) {
				System.err.println("IOException -- from : FileHandler.flushAndClose()");
				e.printStackTrace();
			}
			this.writer = null;
			//this.outputStream = null;
			
		}
	}
	
	public void flush() {
		// TODO Auto-generated method stub
		
		if(writer != null){
			try{
				writer.flush();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	

}

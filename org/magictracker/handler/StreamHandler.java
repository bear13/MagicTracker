package org.magictracker.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.magictracker.processor.TrackRecord;

public class StreamHandler extends Handler{
	
	private Writer writer;
	private OutputStream outputStream;
	
	public StreamHandler(){
		setOutputStream(System.out);
	}

	@Override
	public synchronized void publish(TrackRecord record) {
		// TODO Auto-generated method stub
		String content = null;
		try{
			content = getFormatter().format(record);
		}catch(Exception ex){
			ex.printStackTrace();
			return;
		}
		
		try{
			writer.write(content);
		}catch(Exception ex){
			System.err.println("writer.write() Exception -- from : StreamHandler.publish()");
		}
		
		flush();
		
	}
	
	public void setOutputStream(OutputStream outputStream) throws SecurityException{
		
		if(outputStream == null){
			throw new NullPointerException();
		}
		flushAndClose();
		this.outputStream = outputStream;
		this.writer = new OutputStreamWriter(this.outputStream);
	}
	
	public synchronized void flush() {
		if(writer != null){
			try{
				writer.flush();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
	}
	
	private synchronized void flushAndClose() throws SecurityException{
		
		if(this.writer != null){
			try {
				this.writer.flush();
				this.writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.writer = null;
			this.outputStream = null;
			
		}
	}
	
	

}

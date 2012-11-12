package org.magictracker.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;

import org.magictracker.processor.ProcessorManager;
import org.magictracker.processor.TrackRecord;

public class HtmlHandler extends Handler{
	
	protected final String type = ".html";
	protected String path;
	private String htmlModelFile = "HtmlModel.html";
	private StringBuffer htmlModelContent = new StringBuffer();
	private String flag = "${content}";
	private String flagRexp = "\\$\\{content\\}";
	private long lastContentLength;
	private String outputFile;
	private boolean outputFileExist = false;
	private File tempFile;
	
	public HtmlHandler(){
		
		prepareOutputFile();
	}

	@Override
	public void publish(TrackRecord record) {
		
		if(!this.outputFileExist){
			generateOutputFile();
		}
		
		String html = null;
		
		try{
			html = getFormatter().format(record);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		publish(html);
		
	}
	
	private void prepareOutputFile(){
		
		//File f = new File(this.htmlModelFile);
		Reader reader = null;
		
		try{
			reader = new InputStreamReader(this.getClass().getResourceAsStream(this.htmlModelFile));
			char[] buffer = new char[512];
			int hasRead = 0;
			while((hasRead = reader.read(buffer)) != -1){
				String s = new String(buffer,0,hasRead);
				this.htmlModelContent.append(s);
			}
			
		}catch(FileNotFoundException e){
			System.err.println(this.htmlModelFile+"不存在");
			//e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.lastContentLength = getLastContentLength();
		
		//generateOutputFile();
		
		try {
			this.tempFile = File.createTempFile("tmp",null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private long getLastContentLength(){
		
		String c = this.htmlModelContent.toString();
		int flagPosition = c.indexOf(this.flag);
		
		return c.length() - flagPosition + this.flag.length();
		
	}
	
	private void generateOutputFile(){
		
		RandomAccessFile raf = null;
		
		try{
			
			this.outputFile = this.path + ProcessorManager.getCurrentTime() + this.type;
			raf = new RandomAccessFile(this.outputFile,"rw");
			String newContent = this.htmlModelContent.toString().replaceAll(this.flagRexp,"");
			
			if(raf.length() == 0){
				raf.writeBytes(newContent);
			}
			this.outputFileExist = true;
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}finally{
			try{
				raf.close();	
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	private void publish(String content){
		
		RandomAccessFile raf = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		long pos = 0;
		
		try {
			raf = new RandomAccessFile(this.outputFile,"rw");
			pos = raf.length() - this.lastContentLength;
			
			raf.seek(pos);
			
			fos = new FileOutputStream(this.tempFile);
			byte[] buffer = new byte[1024];
			int hasRead = 0;
			while((hasRead = raf.read(buffer)) != -1){
				fos.write(buffer,0,hasRead);
			}
			
			raf.seek(pos);
			raf.writeBytes(content + System.getProperty("line.separator"));
			
			fis = new FileInputStream(this.tempFile);
			while((hasRead = fis.read(buffer)) != -1){
				raf.write(buffer,0,hasRead);
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("没有html文件"+this.outputFile);
			e.printStackTrace();
		} catch (IOException ioe){
			ioe.printStackTrace();
		} finally{
			try{
				raf.close();
				fis.close();
				fos.close();
			}catch(Exception e){
				e.printStackTrace();
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

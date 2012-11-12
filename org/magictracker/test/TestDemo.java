package org.magictracker.test;

import org.magictracker.Tracker;
import org.magictracker.TrackerFactory;

public class TestDemo {
	
	Tracker t = TrackerFactory.getTracker(TestDemo.class);
	
	public static void main(String[] args){
		TestDemo td = new TestDemo();
		td.start();
	}
	
	private void start(){
		t.r("this is r");
		t.r("this is r");
		
		t.begin("init","initial application");
		init();
		t.stop();
		
		t.begin("config","configurate application");
		config();
		t.stop();
		
		t.r("this is r");
		t.r("this is r");
		t.r("this is r");
		
		t.begin("prepare","prepare to process");
		prepare();
		t.stop();
		
		t.begin("execute","processing");
		execute();
		t.stop();
		
		t.r("this is done");
		
		
		System.out.println("done");
		
		t.reportList();
	}
	
	private void init(){
		
		t.r("this is I01");
		t.r("this is I02");
		
	}
	
	private void config(){
		
		t.r("this is C01");
		t.r("this is C02");
		t.r("this is C03");
		
		t.begin("readfile","read config file");
		readConfigfile();
		t.stop();
		
		t.r("this is C04");
		t.r("this is C05");
		
	}
	
	private void prepare(){
		
		t.r("this is P01");
		t.r("this is P02");
		t.r("this is P03");
		t.r("this is P04");
		
		
	}
	
	private void execute(){
		
		t.r("this is E01");
		t.r("this is E02");
		t.r("this is E13");
		t.r("this is E14");
		t.r("this is E15");
		
	}
	
	private void readConfigfile(){
		
		t.r("this is R01");
		t.r("this is R02");
		t.r("this is R03");
		
	}

}

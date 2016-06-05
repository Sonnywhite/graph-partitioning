package io;

import main.Main;

public class ConsoleLogger {

	private String className = "no classname specified";
	protected ConsoleLogger logger;
	
	public ConsoleLogger(String className) {
		this.className = className;
		this.logger = this;
	}
	
	public void logProgress(int currentCount, int maxCount) {
		if(Main.VERBOSE) {
			
			float percent = (((float) currentCount)/((float) maxCount)) * 100;
			
			String progressBar = "[";
			int scale = 1; // should be greater than 0
			int tenth = Math.round((float) percent/10)*scale;
			for(int j=1; j<tenth; j++) {
				progressBar+="=";
			}
			progressBar +=">";
			for(int j=tenth+1;j<=(scale*10); j++) {
				progressBar +=" ";
			}
			progressBar +="]";
			
			String percentString = (int)percent+"%";
			
			String end = "";
			if(percent==100) {
				end = "done!\n";
			} else
				end = "\r";
			
			System.out.print("INFO: ("+className+") "+currentCount+"/"+maxCount+" " +progressBar+ " "+percentString+" "+end);
		}
	}
	
	public void log(String str) {
		if(Main.VERBOSE)
			justWrite("INFO: ("+className+") "+str);
	}
	
	/**
	 * just writes a string down, but in the err channel
	 * 
	 * @param str
	 */
	public void logError(String str) {
		if(!Main.QUIET)
			System.err.println("ERROR: ("+className+") "+str);
	}
	
	/**
	 * just writes a string down, but in a format
	 * 
	 * @param format
	 * @param args
	 */
	public void logFormat(String format, Object... args) {
		if(!Main.QUIET)
			System.out.format(format, args);
	}
	
	private void justWrite(String str) {
		if(!Main.QUIET)
			System.out.println(str);
	}

}

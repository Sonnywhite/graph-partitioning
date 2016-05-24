package io;

public class ConsoleLogger {
	
	private boolean verbose = false;
	private boolean quiet = false;
	
	public ConsoleLogger(boolean verbose, boolean quiet) {
		this.verbose = verbose;
		this.quiet = quiet;
	}
	
	public void logOptional(String str) {
		if(verbose)
			log("INFO: "+str);
	}
	
	public void logTime(String str) {
		log("TIME: "+str);
	}
	
	public void logError(String str) {
		if(!quiet)
			System.err.println("ERROR: "+str);
	}
	
	public void logFormat(String format, Object... args) {
		if(!quiet)
			System.out.format(format, args);
	}
	
	private void log(String str) {
		if(!quiet)
			System.out.println(str);
	}

}

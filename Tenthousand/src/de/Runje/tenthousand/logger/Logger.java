package de.Runje.tenthousand.logger;

public class Logger {

	private static Logger logger = new Logger();
	private Logger() {
		// TODO Auto-generated constructor stub
	}

	public static void log(LogLevel level, String key, String message) {
		//TODO: use loglevel
		System.out.println(level + "\t" + key + "\t" + message);
	}
	
	
}

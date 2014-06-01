package de.Runje.tenthousand.logger;

public class Logger {

	private static Logger logger = new Logger();
	public static LogLevel logLevel = LogLevel.INFO;
	private Logger() {
	}

	public static void log(LogLevel level, String key, String message) {
		if (level.compareTo(logLevel) >= 0)  {
			System.out.println(level + "\t" + key + "\t" + message);
		}
	}
	
	
}

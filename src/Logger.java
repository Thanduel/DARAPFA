
public class Logger {
	public static final int DEBUG = 0, INFO = 1, WARN = 2, ERROR = 3;
	public static int LEVEL = INFO;
	
	private static void log(String message, int level)	{
		if(level >= LEVEL)
			System.out.println(message);
	}
	
	public static void debug(String message)	{	log(message, DEBUG);	}
	public static void info(String message)		{	log(message, INFO);		}
	public static void warn(String message)		{	log(message, WARN);		}
	public static void error(String message)	{	log(message, ERROR);	}
}

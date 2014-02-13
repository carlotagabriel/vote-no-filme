package carlota.components.utils;


public class Logger {

	private static Logger logger;
	
	private Logger(){
		init();
	};
	
	private void init(){
		logInfo("Logger class has been initialized", Logger.class);
	};
	
	public static Logger getInstance(){
		if(logger == null){
			logger = new Logger();
		}
		return logger;
	}
	
	public void logWarning(String message, Class<?> typeClass){
		System.out.println("[WARNING - " + typeClass.getCanonicalName() + "] " + message);
	}
	
	public void logInfo(String message, Class<?> typeClass){
		System.out.println("[INFO - " + typeClass.getCanonicalName() + "] " + message + ".");
	}

	public void logSevere(String message, Class<?> typeClass){
		System.out.println("[SEVERE - " + typeClass.getCanonicalName() + "] " + message);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return null;
	}
}

package io.github.openguava.jvtool.lang.log;

import java.util.HashMap;
import java.util.Map;

/**
 * 空指令日志工厂
 * @author openguava
 *
 */
public class NopLoggerFactory implements LoggerFactory {

	final Map<Class<?>, Logger> clsLoggers = new HashMap<>();
	
	final Map<String, Logger> strloggers = new HashMap<>();
	
	@Override
	public synchronized Logger getLogger(Class<?> clazz) {
		Logger logger = clsLoggers.get(clazz);
		if (logger == null) {
			logger = new NopLogger(clazz);
			clsLoggers.put(clazz, logger);
		}
		return logger;
	}

	@Override
	public synchronized Logger getLogger(String name) {
		Logger logger = strloggers.get(name);
		if (logger == null) {
			logger = new NopLogger(name);
			strloggers.put(name, logger);
		}
		return logger;
	}

	@Override
	public void reset() {
		this.clsLoggers.clear();
		this.strloggers.clear();
	}
}

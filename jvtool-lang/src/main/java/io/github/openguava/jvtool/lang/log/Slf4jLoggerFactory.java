package io.github.openguava.jvtool.lang.log;

import java.util.HashMap;
import java.util.Map;

/**
 * slf4j LoggerFactory
 * 
 * @author openguava
 *
 */
public class Slf4jLoggerFactory implements LoggerFactory {

	final Map<Class<?>, Logger> clsLoggers = new HashMap<>();
	
	final Map<String, Logger> strloggers = new HashMap<>();

	@Override
	public synchronized Logger getLogger(Class<?> clazz) {
		Logger logger = clsLoggers.get(clazz);
		if (logger == null) {
			logger = new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(clazz));
			clsLoggers.put(clazz, logger);
		}
		return logger;
	}

	@Override
	public synchronized Logger getLogger(String name) {
		Logger logger = strloggers.get(name);
		if (logger == null) {
			logger = new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(name));
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

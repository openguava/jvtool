package io.github.openguava.jvtool.lang.log;

/**
 * 空指令 Logger
 * @author openguava
 *
 */
public class NopLogger implements Logger {
	
	private String name;
	
	public NopLogger(String name) {
		this.name = name;
	}
	
	public NopLogger(Class<?> clazz) {
		this.name = clazz.getName();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isTraceEnabled() {
		return true;
	}

	@Override
	public void trace(String msg) {
		
	}

	@Override
	public void trace(String format, Object arg) {
		
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {

	}

	@Override
	public void trace(String format, Object... arguments) {

	}

	@Override
	public void trace(String msg, Throwable t) {

	}

	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	@Override
	public void debug(String msg) {

	}

	@Override
	public void debug(String format, Object arg) {

	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {

	}

	@Override
	public void debug(String format, Object... arguments) {

	}

	@Override
	public void debug(String msg, Throwable t) {

	}

	@Override
	public boolean isInfoEnabled() {
		return true;
	}

	@Override
	public void info(String msg) {

	}

	@Override
	public void info(String format, Object arg) {

	}

	@Override
	public void info(String format, Object arg1, Object arg2) {

	}

	@Override
	public void info(String format, Object... arguments) {

	}

	@Override
	public void info(String msg, Throwable t) {

	}

	@Override
	public boolean isWarnEnabled() {
		return true;
	}

	@Override
	public void warn(String msg) {

	}

	@Override
	public void warn(String format, Object arg) {

	}

	@Override
	public void warn(String format, Object... arguments) {

	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {

	}

	@Override
	public void warn(String msg, Throwable t) {

	}

	@Override
	public boolean isErrorEnabled() {
		return true;
	}

	@Override
	public void error(String msg) {

	}

	@Override
	public void error(String format, Object arg) {

	}

	@Override
	public void error(String format, Object arg1, Object arg2) {

	}

	@Override
	public void error(String format, Object... arguments) {

	}

	@Override
	public void error(String msg, Throwable t) {

	}
}

package io.github.openguava.jvtool.lang;

import java.io.Serializable;
import java.util.function.Function;

@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
	
}
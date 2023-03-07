package io.github.openguava.jvtool.lang.http.useragent;

/**
 * Interaface that gets string and returns extrancted version 
 * @author alexr
 */
interface VersionFetcher {
	Version version(String str);
}

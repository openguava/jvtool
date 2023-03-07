package io.github.openguava.jvtool.lang.http.useragent;

/**
 * Enum constants classifying the different types of applications which are common in referrer strings
 * @author harald
 *
 */
public enum ApplicationType {

	/**
	 * Webmail service like Windows Live Hotmail and Gmail.
	 */
	WEBMAIL("Webmail client"),
	UNKNOWN("unknown");
	
	private String name;
	
	private ApplicationType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}

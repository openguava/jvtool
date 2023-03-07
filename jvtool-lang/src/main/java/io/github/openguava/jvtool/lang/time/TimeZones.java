package io.github.openguava.jvtool.lang.time;

/**
 * Helps to deal with {@link java.util.TimeZone}s.
 *
 * @since 3.7
 */
public class TimeZones {

    // do not instantiate
    private TimeZones() {
    }

    /**
     * A public version of {@link java.util.TimeZone}'s package private {@code GMT_ID} field.
     */
    public static final String GMT_ID = "GMT";
}

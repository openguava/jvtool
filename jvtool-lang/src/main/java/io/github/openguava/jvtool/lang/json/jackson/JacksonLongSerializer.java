package io.github.openguava.jvtool.lang.json.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * jackson Long 自定义序列化(兼容javascript long 类型)
 * @author openguava
 *
 */
public class JacksonLongSerializer extends JsonSerializer<Long> {

	/** 实例 */
	public final static JacksonLongSerializer instance = new JacksonLongSerializer();
	
	public static JacksonLongSerializer getInstance() {
		return instance;
	}
	
	/** javascript long 最大值(2的53次方) = 9007199254740992 */
	public static final Long JAVASCRIPT_MAX_LONG = ((Double)Math.pow(2L, 53L)).longValue();
	
	/** long 最大值 */
	protected long maxLong = JAVASCRIPT_MAX_LONG;
	
	public long getMaxLong() {
		return maxLong;
	}
	
	public void setMaxLong(long maxLong) {
		this.maxLong = maxLong;
	}
	
	@Override
	public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(value != null) {
			if(value.longValue() >= this.getMaxLong()) {
				gen.writeString(String.valueOf(value));
			} else {
				gen.writeNumber(value.longValue());
			}
		} else {
			gen.writeNull();
		}
	}

	public static void main(String[] args) {
		
	}
}

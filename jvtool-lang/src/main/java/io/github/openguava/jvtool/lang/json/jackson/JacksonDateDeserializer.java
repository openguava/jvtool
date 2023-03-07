package io.github.openguava.jvtool.lang.json.jackson;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import io.github.openguava.jvtool.lang.util.DateUtils;
import io.github.openguava.jvtool.lang.util.NumberUtils;
import io.github.openguava.jvtool.lang.util.StringUtils;

/**
 * jackson Date 类型自定义反序列化
 * @author openguava
 *
 */
public class JacksonDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        String text = jsonParser.getText();
        // 去除首尾空格
        String dateStr = StringUtils.trim(text);
        // 空字符
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
    	// 数字转时间
    	if(NumberUtils.isNumber(dateStr)) {
    		return DateUtils.getDate(NumberUtils.parseLong(dateStr));
    	}
        Date parseDate = DateUtils.parse(dateStr);
        return parseDate;
	}
}

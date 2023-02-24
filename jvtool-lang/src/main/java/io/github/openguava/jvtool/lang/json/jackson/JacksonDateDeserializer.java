package io.github.openguava.jvtool.lang.json.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import io.github.openguava.jvtool.lang.util.ArrayUtils;
import io.github.openguava.jvtool.lang.util.DateUtils;
import io.github.openguava.jvtool.lang.util.NumberUtils;
import io.github.openguava.jvtool.lang.util.StringUtils;

/**
 * jackson Date 类型自定义反序列化
 * @author openguava
 *
 */
public class JacksonDateDeserializer extends JsonDeserializer<Date> {

	protected String[] patterns = { "yyyy-MM-ddTHH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy/MM/dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm", "yyyy-MM-dd HH", "yyyy/MM/dd HH", "yyyy-MM-dd", "yyyy/MM/dd", "yyyy"};
	
	public String[] getPatterns() {
		return this.patterns;
	}
	
	public void setPatterns(String[] patterns) {
		this.patterns = patterns;
	}
	
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
        Date parseDate = null;
        Throwable throwable = null;
        // 匹配表达式
        List<String> matchPatterns = new ArrayList<String>();
        // 不匹配表达式
        List<String> noMatchPatterns = new ArrayList<String>();
        // 查找匹配
        ArrayUtils.each(this.patterns, x -> {
        	if(dateStr.length() == x.length()) {
        		matchPatterns.add(x);
        	} else {
        		noMatchPatterns.add(x);
        	}
        });
        // 首次尝试匹配字符长度匹配
        for(String pattern : matchPatterns) {
        	try {
        		parseDate = DateUtils.parse(dateStr, pattern);
        		if(parseDate != null) {
        			return parseDate;
        		}
			} catch (Exception e) {
				throwable = e;
			}
        }
        // 长度不匹配表达式排序
        noMatchPatterns.sort((x,y) -> y.length() - x.length());
        // 长度不匹配表达式循环尝试
    	for(String pattern : noMatchPatterns) {
    		try {
        		parseDate = DateUtils.parse(dateStr, pattern);
        		if(parseDate != null) {
        			return parseDate;
        		}
			} catch (Exception e) {
				throwable = e;
			}

    	}
    	if(throwable != null) {
    		throw new IllegalArgumentException(throwable);
    	}
        return parseDate;
	}
}

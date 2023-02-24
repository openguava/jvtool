package io.github.openguava.jvtool.spring.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import io.github.openguava.jvtool.lang.util.StringUtils;

/**
 * spring 工具类
 * @author openguava
 *
 */
public class SpringUtils {
	
	protected SpringUtils() {
		
	}
	
	/**
	 * 获取请求MultiPart表单文件
	 * @param request
	 * @return
	 */
	public static List<MultipartFile> getRequestMultipartFiles(HttpServletRequest request){
		List<MultipartFile> multipartFiles = new ArrayList<>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(!multipartResolver.isMultipart(request)){
			return multipartFiles;
		}
		// 转换成多部分request
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multiRequest.getFileNames();
        while (iterator.hasNext()) {
        	String name = iterator.next();
        	List<MultipartFile> files = multiRequest.getFiles(name);
        	for(MultipartFile file : files){
        		String fileName = file.getOriginalFilename();
        		if (StringUtils.isEmpty(fileName)) {
        			continue;
        		}
        		multipartFiles.add(file);
        	}
        }
        return multipartFiles;
	}
}

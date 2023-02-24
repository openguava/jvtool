package io.github.openguava.jvtool.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import io.github.openguava.jvtool.lang.auth.annotation.RequiresApi;
import io.github.openguava.jvtool.lang.util.AuthUtils;

@Aspect
@Component
public class ApiAuthAspect implements Ordered {

	@Around("@annotation(requiresApi)")
	public Object requiresApiAround(ProceedingJoinPoint joinPoint, RequiresApi requiresApi) throws Throwable {
		if(requiresApi != null) {
			AuthUtils.checkApi(requiresApi);
		}
		return joinPoint.proceed();
	}
	
	/**
	 * 确保在权限认证aop执行前执行
	 */
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1;
	}
}

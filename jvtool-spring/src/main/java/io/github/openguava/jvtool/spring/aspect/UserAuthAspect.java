package io.github.openguava.jvtool.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import io.github.openguava.jvtool.lang.auth.annotation.RequiresAnonymous;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresPermissions;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresRoles;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresLogin;
import io.github.openguava.jvtool.lang.util.AuthUtils;

@Aspect
@Component
public class UserAuthAspect {

	@Around("@annotation(requiresLogin)")
	public Object requiresUserAround(ProceedingJoinPoint joinPoint, RequiresLogin requiresLogin) throws Throwable {
		if(requiresLogin != null) {
			AuthUtils.checkLogin();
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(requiresRoles)")
	public Object requiresRolesAround(ProceedingJoinPoint joinPoint, RequiresRoles requiresRoles) throws Throwable {
		if(requiresRoles != null) {
			AuthUtils.checkRole(requiresRoles);
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(requiresPermissions)")
	public Object requiresPermissionsAround(ProceedingJoinPoint joinPoint, RequiresPermissions requiresPermissions) throws Throwable {
		if(requiresPermissions != null) {
			AuthUtils.checkPermission(requiresPermissions);
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(requiresAnonymous)")
	public Object requiresAnonymousAround(ProceedingJoinPoint joinPoint, RequiresAnonymous requiresAnonymous) throws Throwable {
		if(requiresAnonymous != null) {
			AuthUtils.checkAnonymous(requiresAnonymous);
		}
		return joinPoint.proceed();
	}
}

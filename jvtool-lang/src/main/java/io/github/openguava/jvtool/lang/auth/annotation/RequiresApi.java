package io.github.openguava.jvtool.lang.auth.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * api认证：api请求鉴权之后才能进入该方法
 * @author openguava
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresApi {
	
	/**
	 * 是否校验用户信息
	 * @return
	 */
	boolean checkUser() default false;
}

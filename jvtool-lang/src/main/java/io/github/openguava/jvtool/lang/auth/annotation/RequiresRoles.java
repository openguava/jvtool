package io.github.openguava.jvtool.lang.auth.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 角色认证：必须具有指定角色标识才能进入该方法
 * @author openguava
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresRoles {

    /**
     * 要校验的角色标识
     * @return
     */
    String[] value();
    
    /**
     * 验证逻辑：AND | OR，默认AND
     */
    Logical logical() default Logical.AND; 
}

package io.github.openguava.jvtool.lang.auth.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限认证：必须具有指定权限才能进入该方法
 * @author openguava
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermissions {

    /**
     * 要校验的权限标识
     */
    String[] value() default {};
    
    /**
     * 验证模式：AND | OR，默认AND
     */
    Logical logical() default Logical.AND; 

}


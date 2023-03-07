package io.github.openguava.jvtool.boot.swagger.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import io.github.openguava.jvtool.boot.swagger.config.SwaggerAutoConfiguration;
import io.github.openguava.jvtool.boot.swagger.config.SwaggerBeanPostProcessor;
import io.github.openguava.jvtool.boot.swagger.config.SwaggerWebConfiguration;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ SwaggerAutoConfiguration.class, SwaggerWebConfiguration.class, SwaggerBeanPostProcessor.class })
public @interface EnableBootSwagger2 {

}

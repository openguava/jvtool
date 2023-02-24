package io.github.openguava.jvtool.spring.bean.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;

import io.github.openguava.jvtool.lang.util.ArrayUtils;
import io.github.openguava.jvtool.lang.util.LogUtils;

@Order(Ordered.LOWEST_PRECEDENCE)
public class OnMissBeanCondition extends AbstractBeanCondition implements  Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		try {
			if(!metadata.isAnnotated(ConditionalOnMissBean.class.getName())) {
				return true;
			}
			MergedAnnotation<ConditionalOnMissBean> mergedAnnotation = metadata.getAnnotations().get(ConditionalOnMissBean.class);
			if(mergedAnnotation == null) {
				return true;
			}
			Class<?>[] values = mergedAnnotation.getClassArray("value");
			if(ArrayUtils.isEmpty(values)) {
				return true;
			}
			for (Class<?> type : values) {
				String[] names = context.getBeanFactory().getBeanNamesForType(type, true, false);
				if(ArrayUtils.isNotEmpty(names)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			LogUtils.debug(OnMissBeanCondition.class, e.getMessage());
			return true;
		}
	}
}

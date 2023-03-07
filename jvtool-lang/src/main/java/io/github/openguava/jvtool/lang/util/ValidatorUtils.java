package io.github.openguava.jvtool.lang.util;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

/**
 * hibernate-validator校验工具类
 * 参考文档：http://docs.jboss.org/hibernate/validator/6.0/reference/en-US/html_single/
 *
 */
public class ValidatorUtils {

	protected ValidatorUtils() {

	}

	/**
	 * 执行校验
	 * @param validator
	 * @param object
	 * @param groups
	 * @throws ConstraintViolationException
	 */
	public static void validate(Validator validator, Object object, Class<?>... groups)
			throws ConstraintViolationException {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(constraintViolations);
		}
	}
}

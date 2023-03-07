package io.github.openguava.jvtool.lang.util;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import io.github.openguava.jvtool.lang.constant.StringConstants;
import io.github.openguava.jvtool.lang.exception.UtilException;
import io.github.openguava.jvtool.lang.map.ConcurrentWeakKeyHashMap;
import io.github.openguava.jvtool.lang.Validate;

/**
 * 反射工具类
 * 
 * @author openguava
 *
 */
public class ReflectUtils {

	/**
	 * The package separator character: {@code '&#x2e;' == {@value}}.
	 */
	public static final char PACKAGE_SEPARATOR_CHAR = '.';
	
	private static final String SETTER_PREFIX = "set";

	private static final String GETTER_PREFIX = "get";

	/**
	 * 构造对象缓存
	 */
	private static final ConcurrentMap<Class<?>, Constructor<?>[]> CONSTRUCTORS_CACHE = new ConcurrentWeakKeyHashMap<>();

	/**
	 * 字段缓存
	 */
	private static final ConcurrentMap<Class<?>, Field[]> FIELDS_CACHE = new ConcurrentWeakKeyHashMap<>();

	/**
	 * 方法缓存
	 */
	private static final ConcurrentMap<Class<?>, Method[]> METHODS_CACHE = new ConcurrentWeakKeyHashMap<>();

	/**
	 * 获得对象数组的类数组
	 *
	 * @param objects 对象数组，如果数组中存在{@code null}元素，则此元素被认为是Object类型
	 * @return 类数组
	 */
	public static Class<?>[] getClasses(Object... objects) {
		Class<?>[] classes = new Class<?>[objects.length];
		Object obj;
		for (int i = 0; i < objects.length; i++) {
			obj = objects[i];
			classes[i] = (null == obj) ? Object.class : obj.getClass();
		}
		return classes;
	}

	/**
	 * 获取 class
	 * 
	 * @param className
	 * @return
	 */
	public static Class<?> getClassByName(String className) {
		try {
			return Class.forName(className);
		} catch (Exception e) {
			LogUtils.error(ReflectUtils.class, e.getMessage(), e);
			return null;
		}
	}

	// ----------------------------------- Begin Package

	/**
	 * <p>
	 * Gets the package name of an {@code Object}.
	 * </p>
	 *
	 * @param object      the class to get the package name for, may be null
	 * @param valueIfNull the value to return if null
	 * @return the package name of the object, or the null value
	 */
	public static String getPackageName(final Object object, final String valueIfNull) {
		if (object == null) {
			return valueIfNull;
		}
		return getPackageName(object.getClass());
	}

	/**
	 * <p>
	 * Gets the package name of a {@code Class}.
	 * </p>
	 *
	 * @param cls the class to get the package name for, may be {@code null}.
	 * @return the package name or an empty string
	 */
	public static String getPackageName(final Class<?> cls) {
		if (cls == null) {
			return StringConstants.STRING_EMPTY;
		}
		return getPackageName(cls.getName());
	}

	/**
	 * <p>
	 * Gets the package name from a {@code String}.
	 * </p>
	 *
	 * <p>
	 * The string passed in is assumed to be a class name - it is not checked.
	 * </p>
	 * <p>
	 * If the class is unpackaged, return an empty string.
	 * </p>
	 *
	 * @param className the className to get the package name for, may be
	 *                  {@code null}
	 * @return the package name or an empty string
	 */
	public static String getPackageName(String className) {
		if (StringUtils.isEmpty(className)) {
			return StringConstants.STRING_EMPTY;
		}

		// Strip array encoding
		while (className.charAt(0) == '[') {
			className = className.substring(1);
		}
		// Strip Object type encoding
		if (className.charAt(0) == 'L' && className.charAt(className.length() - 1) == ';') {
			className = className.substring(1);
		}

		final int i = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
		if (i == -1) {
			return StringConstants.STRING_EMPTY;
		}
		return className.substring(0, i);
	}

	/**
	 * 获取当前线程的类加载器
	 * 
	 * @return
	 */
	public static ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * 获取类加载器 获取优先级(1、当前线程,2、当前class,3、系统)
	 * 
	 * @return
	 */
	public static ClassLoader getClassLoader() {
		ClassLoader classLoader = getContextClassLoader();
		if (classLoader == null) {
			classLoader = ReflectUtils.class.getClassLoader();
			if (null == classLoader) {
				classLoader = ClassLoader.getSystemClassLoader();
			}
		}
		return classLoader;
	}

	/**
	 * 获得资源的URL(路径用/分隔)
	 * 
	 * @param resource
	 * @return
	 */
	public static URL getResource(String resource) {
		return getResource(resource, null);
	}

	/**
	 * 获得资源相对路径对应的URL
	 * 
	 * @param resource  资源相对路径，{@code null}和""都表示classpath根路径
	 * @param baseClass 基准Class，获得的相对路径相对于此Class所在路径，如果为{@code null}则相对ClassPath
	 * @return
	 */
	public static URL getResource(String resource, Class<?> baseClass) {
		if (resource == null) {
			resource = StringConstants.STRING_EMPTY;
		}
		if (baseClass != null) {
			return baseClass.getResource(resource);
		} else {
			return getClassLoader().getResource(resource);
		}
	}

	// ----------------------------------- Begin Constructor

	/**
	 * 查找类中的指定参数的构造方法，如果找到构造方法，会自动设置可访问为true
	 *
	 * @param <T>            对象类型
	 * @param clazz          类
	 * @param parameterTypes 参数类型，只要任何一个参数是指定参数的父类或接口或相等即可，此参数可以不传
	 * @return 构造方法，如果未找到返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
		if (null == clazz) {
			return null;
		}

		final Constructor<?>[] constructors = getConstructorsCached(clazz);
		Class<?>[] pts;
		for (Constructor<?> constructor : constructors) {
			pts = constructor.getParameterTypes();
			if (TypeUtils.isAllAssignableFrom(pts, parameterTypes)) {
				// 构造可访问
				setAccessible(constructor);
				return (Constructor<T>) constructor;
			}
		}
		return null;
	}

	/**
	 * 获得一个类中所有构造列表
	 *
	 * @param <T>       构造的对象类型
	 * @param beanClass 类，非{@code null}
	 * @return 字段列表
	 * @throws SecurityException 安全检查异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T>[] getConstructorsCached(final Class<T> beanClass) throws SecurityException {
		Validate.notNull(beanClass);
		Constructor<?>[] constructors = CONSTRUCTORS_CACHE.computeIfAbsent(beanClass, (k) -> getConstructorsDirectly(beanClass));
		return (Constructor<T>[]) constructors;
	}

	/**
	 * 获得一个类中所有构造列表，直接反射获取，无缓存
	 *
	 * @param beanClass 类
	 * @return 字段列表
	 * @throws SecurityException 安全检查异常
	 */
	public static Constructor<?>[] getConstructorsDirectly(Class<?> beanClass) throws SecurityException {
		Validate.notNull(beanClass);
		return beanClass.getDeclaredConstructors();
	}

	// ----------------------------------- End Constructor

	// ----------------------------------- Begin Method

	/**
	 * 调用方法
	 * @param instance 实例
	 * @param methodName 方法名
	 * @param paramTypes 参数类型
	 * @param paramArgs 参数值
	 * @return
	 * @throws InvocationTargetException
	 */
	public static Object invokeMethod(Object instance, String methodName, Class<?>[] paramTypes, Object[] paramArgs) throws InvocationTargetException {
		if(instance == null) {
			return null;
		}
		// 补充 paramTypes
		if(ArrayUtils.isNotEmpty(paramArgs) && ArrayUtils.isEmpty(paramTypes)) {
			paramTypes = new Class<?>[paramArgs.length];
			for(int i = 0; i < paramArgs.length; i++) {
				paramTypes[i] = paramArgs[i] != null ? paramArgs[i].getClass() : null;
			}
		}
		Class<?> clazz = instance.getClass();
		Method method = getMethod(clazz, methodName, paramTypes);
		if(method == null) {
			return null;
		}
		try {
			return method.invoke(instance, paramArgs);
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		}
	}
	
	/**
	 * 调用方法
	 * @param clazz
	 * @param methodName
	 * @param paramTypes
	 * @param instance
	 * @param paramArgs
	 * @return
	 * @throws InvocationTargetException
	 */
	public static Object invokeMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes, Object instance, Object[] paramArgs) throws InvocationTargetException {
		Method method = getMethod(clazz, methodName, paramTypes);
		if(method == null) {
			return null;
		}
		try {
			return method.invoke(instance, paramArgs);
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		}
	}
	
	/**
	 * 调用方法
	 * @param method
	 * @param instance
	 * @param paramArgs
	 * @return
	 * @throws InvocationTargetException
	 */
	public static Object invokeMethod(Method method, Object instance, Object[] paramArgs) throws InvocationTargetException {
		if(method == null) {
			return null;
		}
		try {
			if(!method.isAccessible()) {
				method.setAccessible(true);
			}
			return method.invoke(instance, paramArgs);
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		}
	}
	
	/**
	 * 查找指定方法 如果找不到对应的方法则返回null
	 * 
	 * @param clazz
	 * @param methodName 函数名
	 * @param paramTypes 参数类型(可空)
	 * @return
	 */
	public static Method getMethodByName(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		if (paramTypes == null || paramTypes.length == 0) {
			return getMethodByName(clazz, false, methodName);
		} else {
			return getMethod(clazz, methodName, paramTypes);
		}
	}

	/**
	 * 按照方法名查找指定方法名的方法，只返回匹配到的第一个方法，如果找不到对应的方法则返回{@code null}
	 *
	 * <p>
	 * 此方法只检查方法名是否一致，并不检查参数的一致性。
	 * </p>
	 *
	 * @param clazz      类，如果为{@code null}返回{@code null}
	 * @param ignoreCase 是否忽略大小写
	 * @param methodName 方法名，如果为空字符串返回{@code null}
	 * @return 方法
	 * @throws SecurityException 无权访问抛出异常
	 */
	public static Method getMethodByName(Class<?> clazz, boolean ignoreCase, String methodName)
			throws SecurityException {
		if (null == clazz || StringUtils.isBlank(methodName)) {
			return null;
		}

		final Method[] methods = getMethodsCached(clazz);
		if (ArrayUtils.isNotEmpty(methods)) {
			for (Method method : methods) {
				if (StringUtils.equals(methodName, method.getName(), ignoreCase)
						// 排除桥接方法
						&& false == method.isBridge()) {
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 查找指定方法 如果找不到对应的方法则返回{@code null}
	 *
	 * <p>
	 * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回{@code null}。
	 * </p>
	 *
	 * @param clazz      类，如果为{@code null}返回{@code null}
	 * @param methodName 方法名，如果为空字符串返回{@code null}
	 * @param paramTypes 参数类型，指定参数类型如果是方法的子类也算
	 * @return 方法
	 * @throws SecurityException 无权访问抛出异常
	 */
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) throws SecurityException {
		return getMethod(clazz, false, methodName, paramTypes);
	}

	/**
	 * 查找指定方法 如果找不到对应的方法则返回{@code null}<br>
	 * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回{@code null}。<br>
	 * 如果查找的方法有多个同参数类型重载，查找第一个找到的方法
	 *
	 * @param clazz      类，如果为{@code null}返回{@code null}
	 * @param ignoreCase 是否忽略大小写
	 * @param methodName 方法名，如果为空字符串返回{@code null}
	 * @param paramTypes 参数类型，指定参数类型如果是方法的子类也算
	 * @return 方法
	 * @throws SecurityException 无权访问抛出异常
	 */
	public static Method getMethod(Class<?> clazz, boolean ignoreCase, String methodName, Class<?>... paramTypes)
			throws SecurityException {
		if (null == clazz || StringUtils.isBlank(methodName)) {
			return null;
		}

		final Method[] methods = getMethodsCached(clazz);
		if (CollectionUtils.isNotEmpty(methods)) {
			for (Method method : methods) {
				if (StringUtils.equals(methodName, method.getName(), ignoreCase)
						&& TypeUtils.isAllAssignableFrom(method.getParameterTypes(), paramTypes)
						// 排除桥接方法，pr#1965@Github
						&& false == method.isBridge()) {
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 获得一个类中所有方法列表，包括其父类中的方法
	 *
	 * @param beanClass 类，非{@code null}
	 * @return 方法列表
	 * @throws SecurityException 安全检查异常
	 */
	public static Method[] getMethodsCached(final Class<?> beanClass) throws SecurityException {
		Method[] allMethods = METHODS_CACHE.computeIfAbsent(beanClass, (k) -> getMethodsDirectly(beanClass, true));
		return allMethods;
	}

	/**
	 * 获得一个类中所有方法列表，直接反射获取，无缓存
	 *
	 * @param beanClass             类
	 * @param withSuperClassMethods 是否包括父类的方法列表
	 * @return 方法列表
	 * @throws SecurityException 安全检查异常
	 */
	public static Method[] getMethodsDirectly(Class<?> beanClass, boolean withSuperClassMethods)
			throws SecurityException {
		Validate.notNull(beanClass);

		Method[] allMethods = null;
		Class<?> searchType = beanClass;
		Method[] declaredMethods;
		while (searchType != null) {
			declaredMethods = searchType.getDeclaredMethods();
			if (null == allMethods) {
				allMethods = declaredMethods;
			} else {
				allMethods = ArrayUtils.append(allMethods, declaredMethods);
			}
			searchType = withSuperClassMethods ? searchType.getSuperclass() : null;
		}

		return allMethods;
	}
	
	/**
	 * 调用 get 方法. 支持多级，如：对象名.对象名.方法
	 * @param obj
	 * @param propertyName
	 * @return
	 * @throws InvocationTargetException 
	 */
	public static Object invokeGetterMethod(Object obj, String propertyName) throws InvocationTargetException {
		Object object = obj;
		String[] names = StringUtils.split(propertyName, ".");
		for(String name : names) {
			String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
			object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
		}
		return object;
	}
	
	/**
	 * 获取 get 函数
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	public static Method getGetterMethod(Class<?> clazz, String propertyName) {
		String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(propertyName);
		return getMethod(clazz, getterMethodName, new Class<?>[] {});
	}
	
	/**
	 * 调用 set 方法, 仅匹配方法名。 支持多级，如：对象名.对象名.方法
	 * @param obj
	 * @param propertyName
	 * @param value
	 * @throws InvocationTargetException
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value) throws InvocationTargetException {
		Object object = obj;
		String[] names = StringUtils.split(propertyName, ".");
		for (int i = 0; i < names.length; i++) {
			if (i < names.length - 1) {
				String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
				object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
			} else {
				String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
				invokeMethod(object, setterMethodName, null, new Object[] { value });
			}
		}
	}
	
	/**
	 * 获取 set 函数
	 * @param clazz
	 * @param propertyName
	 * @param propertyType
	 * @return
	 */
	public static Method getSetterMethod(Class<?> clazz, String propertyName, Class<?> propertyType) {
		String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(propertyName);
		return getMethod(clazz, setterMethodName, new Class<?>[] { propertyType });
	}

	// ----------------------------------- End Method

	// ----------------------------------- Begin Field

	/**
	 * 获取字段值
	 *
	 * @param obj       对象
	 * @param fieldName 字段名
	 * @return 字段值
	 * @throws UtilException 包装IllegalAccessException异常
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		if (null == obj || StringUtils.isBlank(fieldName)) {
			return null;
		}
		return getFieldValue(obj, getField(obj.getClass(), fieldName));
	}

	/**
	 * 获取字段值
	 *
	 * @param obj   对象
	 * @param field 字段
	 * @return 字段值
	 * @throws UtilException 包装IllegalAccessException异常
	 */
	public static Object getFieldValue(Object obj, Field field) {
		if (null == obj || null == field) {
			return null;
		}
		setAccessible(field);
		Object result;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			throw new UtilException(e, "IllegalAccess for {}.{}", obj.getClass(), field.getName());
		}
		return result;
	}

	/**
	 * 设置字段值
	 *
	 * @param obj       对象
	 * @param fieldName 字段名
	 * @param value     值，值类型必须与字段类型匹配，不会自动转换对象类型
	 * @throws UtilsException 包装IllegalAccessException异常
	 */
	public static void setFieldValue(Object obj, String fieldName, Object value) {
		Validate.notNull(obj);
		Validate.notBlank(fieldName);

		final Field field = getField(obj.getClass(), fieldName);
		Validate.notNull(field, "Field [{}] is not exist in [{}]", fieldName, obj.getClass().getName());
		setFieldValue(obj, field, value);
	}

	/**
	 * 设置字段值
	 *
	 * @param obj   对象
	 * @param field 字段
	 * @param value 值，值类型必须与字段类型匹配，不会自动转换对象类型
	 * @throws UtilException UtilException 包装IllegalAccessException异常
	 */
	public static void setFieldValue(Object obj, Field field, Object value) {
		Validate.notNull(obj);
		Validate.notNull(field, "Field in [{}] not exist !", obj.getClass().getName());

		setAccessible(field);

		/*
		if (value != null) {
			Class<?> fieldType = field.getType();
			if (!fieldType.isAssignableFrom(value.getClass())) {
				// 对于类型不同的字段，尝试转换，转换失败则使用原对象类型
				final Object targetValue = ConvertUtils.convert(fieldType, value);
				if (null != targetValue) {
					value = targetValue;
				}
			}
		}
		*/

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			throw new UtilException(e, "IllegalAccess for {}.{}", obj.getClass(), field.getName());
		}
	}

	/**
	 * 查找指定类中的所有字段（包括非public字段），也包括父类和Object类的字段， 字段不存在则返回<code>null</code>
	 *
	 * @param beanClass 被查找字段的类,不能为null
	 * @param name      字段名
	 * @return 字段
	 * @throws SecurityException 安全异常
	 */
	public static Field getField(Class<?> beanClass, String name) throws SecurityException {
		final Field[] fields = getFieldsCached(beanClass);
		if (CollectionUtils.isNotEmpty(fields)) {
			for (Field field : fields) {
				if ((name.equals(field.getName()))) {
					return field;
				}
			}
		}
		return null;
	}

	/**
	 * 获得一个类中所有字段列表，包括其父类中的字段<br>
	 * 如果子类与父类中存在同名字段，则这两个字段同时存在，子类字段在前，父类字段在后。
	 *
	 * @param beanClass 类
	 * @return 字段列表
	 * @throws SecurityException 安全检查异常
	 */
	public static Field[] getFieldsCached(final Class<?> beanClass) throws SecurityException {
		Field[] allFields = FIELDS_CACHE.computeIfAbsent(beanClass, (k) -> getFieldsDirectly(beanClass, true));
		return allFields;
	}

	/**
	 * 获得一个类中所有字段列表，直接反射获取，无缓存<br>
	 * 如果子类与父类中存在同名字段，则这两个字段同时存在，子类字段在前，父类字段在后。
	 *
	 * @param beanClass            类
	 * @param withSuperClassFields 是否包括父类的字段列表
	 * @return 字段列表
	 * @throws SecurityException 安全检查异常
	 */
	public static Field[] getFieldsDirectly(Class<?> beanClass, boolean withSuperClassFields) throws SecurityException {
		Validate.notNull(beanClass);

		Field[] allFields = null;
		Class<?> searchType = beanClass;
		Field[] declaredFields;
		while (searchType != null) {
			declaredFields = searchType.getDeclaredFields();
			if (null == allFields) {
				allFields = declaredFields;
			} else {
				allFields = ArrayUtils.append(allFields, declaredFields);
			}
			searchType = withSuperClassFields ? searchType.getSuperclass() : null;
		}

		return allFields;
	}

	// ----------------------------------- End Field

	/**
	 * 实例化对象
	 *
	 * @param <T>    对象类型
	 * @param clazz  类
	 * @param params 构造函数参数
	 * @return 对象
	 * @throws UtilException 包装各类异常
	 */
	public static <T> T newInstance(Class<T> clazz, Object... params) {
		if (ArrayUtils.isEmpty(params)) {
			final Constructor<T> constructor = getConstructor(clazz);
			try {
				return constructor.newInstance();
			} catch (Exception e) {
				throw new UtilException(e, "Instance class [{}] error!", clazz);
			}
		}

		final Class<?>[] paramTypes = getClasses(params);
		final Constructor<T> constructor = getConstructor(clazz, paramTypes);
		if (null == constructor) {
			throw new UtilException("No Constructor matched for parameter types: [{}]", new Object[] { paramTypes });
		}
		try {
			return constructor.newInstance(params);
		} catch (Exception e) {
			throw new UtilException(e, "Instance class [{}] error!", clazz);
		}
	}

	/**
	 * 设置方法为可访问（私有方法可以被外部调用）
	 *
	 * @param <T>              AccessibleObject的子类，比如Class、Method、Field等
	 * @param accessibleObject 可设置访问权限的对象，比如Class、Method、Field等
	 * @return 被设置可访问的对象
	 */
	public static <T extends AccessibleObject> T setAccessible(T accessibleObject) {
		if (null != accessibleObject && !accessibleObject.isAccessible()) {
			accessibleObject.setAccessible(true);
		}
		return accessibleObject;
	}
	
	/**
	 * 获取可序列化 lambda 对象
	 * @param func
	 * @return
	 */
	public static <F extends Function<?, ?> & Serializable> SerializedLambda getLambda(F func) {
		Method writeReplaceMethod;
		try {
			writeReplaceMethod = func.getClass().getDeclaredMethod("writeReplace");
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		boolean isAccessible = writeReplaceMethod.isAccessible();
		writeReplaceMethod.setAccessible(true);
		SerializedLambda serializedLambda;
		try {
			serializedLambda = (SerializedLambda)writeReplaceMethod.invoke(func);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		writeReplaceMethod.setAccessible(isAccessible);
		return serializedLambda;
	}
	
	/**
	 * 获取 lambda 实现函数
	 * @param func
	 * @return
	 */
	public static <F extends Function<?, ?> & Serializable> Method getLambdaImplMethod(F func) {
		SerializedLambda serializedLambda = getLambda(func);
		if(serializedLambda == null) {
			return null;
		}
		Class<?> implClass = getClassByName(serializedLambda.getImplClass().replace("/", "."));
		if(implClass == null) {
			return null;
		}
		return getMethod(implClass, serializedLambda.getImplMethodName());
	}
	
	/**
	 * 获取 lambda 实现的字段
	 * @param func
	 * @return
	 */
	public static <F extends Function<?, ?> & Serializable> Field getLambdaImplField(F func) {
		Method getMethod = getLambdaImplMethod(func);
		if(getMethod == null) {
			return null;
		}
		// 字段名
		String fieldName = getMethod.getName();
		// 去除字段 get/is
		if(fieldName.startsWith("get")) {
			fieldName = fieldName.substring("get".length());
		} else if(fieldName.startsWith("is")) {
			fieldName = fieldName.substring("is".length());
		}
		// 驼峰式命名
		fieldName = String.valueOf(fieldName.charAt(0)).toLowerCase() + fieldName.substring(1);
		// 返回字段
		return getField(getMethod.getDeclaringClass(), fieldName);
	}
}

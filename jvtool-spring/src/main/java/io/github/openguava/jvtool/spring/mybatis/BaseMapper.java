package io.github.openguava.jvtool.spring.mybatis;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

	/**
	 * 创建查询条件封装
	 * 
	 * @return
	 */
	public default QueryWrapper<T> createQueryWrapper() {
		return new QueryWrapper<>();
	}

	/**
	 * 添加实体
	 * 
	 * @param entity
	 * @return
	 */
	public default int insertEntity(T entity) {
		return this.insert(entity);
	}

	/**
	 * 根据主键id更新实体
	 * 
	 * @param entity
	 * @return
	 */
	public default int updateEntityById(T entity) {
		return this.updateById(entity);
	}
	
	/**
	 * 根据Lambda条件更新实体
	 * @param entity
	 * @param consumer
	 * @return
	 */
	public default int updateEntity(T entity, Consumer<LambdaQueryWrapper<T>> consumer) {
		QueryWrapper<T> queryWrapper = this.createQueryWrapper();
		consumer.accept(queryWrapper.lambda());
		return this.update(entity, queryWrapper);
	}
	
	/**
	 * 根据Wrapper条件更新实体
	 * @param entity
	 * @param queryWrapper
	 * @return
	 */
	public default int updateEntity(T entity, QueryWrapper<T> queryWrapper) {
		return this.update(entity, queryWrapper);
	}
	
	/**
	 * 根据主键id删除实体
	 * @param entity
	 * @return
	 */
	public default int deleteEntityById(T entity) {
		return this.deleteById(entity);
	}
	
	/**
	 * 根据主键id删除实体
	 * @param entity
	 * @return
	 */
	public default int deleteEntityById(Serializable id) {
		return this.deleteById(id);
	}
	
	/**
	 * 根据Lambda条件删除实体
	 * @param consumer
	 * @return
	 */
	public default int deleteEntity(Consumer<LambdaQueryWrapper<T>> consumer) {
		QueryWrapper<T> queryWrapper = this.createQueryWrapper();
		consumer.accept(queryWrapper.lambda());
		return this.delete(queryWrapper);
	}
	
	/**
	 * 根据Wrapper条件删除实体
	 * @param queryWrapper 查询条件
	 * @return
	 */
	public default int deleteEntity(QueryWrapper<T> queryWrapper) {
		return this.delete(queryWrapper);
	}

	/**
	 * 根据主键id查询实体
	 * @param id
	 * @return
	 */
	public default T selectEntityById(Serializable id) {
		return this.selectById(id);
	}
	
	/**
	 * 查询单个实体
	 * @param consumer 条件
	 * @param columns 过滤的字段
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public default T selectEntityOne(Consumer<LambdaQueryWrapper<T>> consumer, SFunction<T, ?>... columns) {
		QueryWrapper<T> queryWrapper = this.createQueryWrapper();
		consumer.accept(queryWrapper.lambda().select(columns));
		List<T> list = this.selectList(queryWrapper);
		return !list.isEmpty() ? list.get(0) : null;
	}
	
	/**
	 * 根据Wrapper条件查询单个实体
	 * @param queryWrapper 条件
	 * @return
	 */
	public default T selectEntityOne(QueryWrapper<T> queryWrapper) {
		List<T> list = this.selectList(queryWrapper);
		return !list.isEmpty() ? list.get(0) : null;
	}
	
	/**
	 * 根据Lambda条件查询实体记录数
	 * @param consumer 条件
	 * @return
	 */
	public default Long selectEntityCount(Consumer<LambdaQueryWrapper<T>> consumer) {
		QueryWrapper<T> queryWrapper = this.createQueryWrapper();
		consumer.accept(queryWrapper.lambda());
		return this.selectCount(queryWrapper);
	}
	
	/**
	 * 根据Wrapper条件查询实体记录数
	 * @param queryWrapper 条件
	 * @return
	 */
	public default Long selectEntityCount(QueryWrapper<T> queryWrapper) {
		return this.selectCount(queryWrapper);
	}
	
	/**
	 * 根据Lambda条件查询实体列表
	 * @param consumer 条件
	 * @param columns 查询的字段
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public default List<T> selectEntityList(Consumer<LambdaQueryWrapper<T>> consumer, SFunction<T, ?>... columns) {
		QueryWrapper<T> queryWrapper = this.createQueryWrapper();
		consumer.accept(queryWrapper.lambda().select(columns));
		return this.selectList(queryWrapper);
	}
	
	/**
	 * 根据Wrapper条件查询实体列表
	 * @param queryWrapper 条件
	 * @return
	 */
	public default List<T> selectEntityList(QueryWrapper<T> queryWrapper) {
		return this.selectList(queryWrapper);
	}
}
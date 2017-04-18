package com.shlanbao.tzsc.base.dao;

import java.util.List;

import com.shlanbao.tzsc.base.model.PageParams;
/**
 * 通用dao接口
 * @author Leejean
 * @create 2014年8月25日下午10:04:31
 * @param <T> PO
 */
public interface BaseDaoI<T> {
	/**
	 * 保存并返回当前对象
	 * 
	 */
	public T saveAndReturn(T clazz) throws Exception;
	/**
	 * 批量添加
	 * @param list
	 * @param clazz
	 * @return
	 */
	public boolean batchInsertAndReturn(List<?> list,Class<T> clazz);
	/**
	 * 保存
	 * @author Leejean
	 * @create 2014-7-5下午12:06:02
	 * @param t 持久化对象
	 * @return 执行结果
	 */
	public abstract boolean save(T o);
	
	/**
	 * 保存
	 * @author Leejean
	 * @create 2014-7-5下午12:06:02
	 * @param t 持久化对象
	 * @return 执行结果
	 */
	public abstract String saveBackKey(T o);
	
	
	/**
	 * 保存
	 * @author Leejean
	 * @create 2014-7-5下午12:06:02
	 * @param t 持久化对象
	 * @return 执行结果
	 */
	public abstract boolean update(T o);
	
	/**
	 * 保存或者修改
	 * @author liuligong
	 * @create 2014-11-10 8:58
	 * @param t 持久化对象
	 * @return 执行结果
	 */
	public boolean saveOrUpdate(T o);
	/**
	 * 根据ID查找对象
	 * @author Leejean
	 * @create 2014-7-5下午12:06:17
	 * @param clazz 目标对象
	 * @param id ID
	 * @return 对象实例
	 */
	public abstract T findById(Class<T> c, String id);

	/**
	 * 普通分页查询
	 * @author Leejean
	 * @create 2014-7-5下午12:08:49
	 * @param hql HQL
	 * @param pageIndex 当前页
	 * @param pageSize 页大小
	 * @param params 参数
	 * @return 结果集
	 */
	public abstract List<T> queryByPage(String hql, int pageIndex, int pageSize,
			Object... params);

	/**
	 * 分页查询(适用于Easyui分页)
	 * 局限:排序仅支持单表
	 * @author Leejean
	 * @create 2014-6-27下午03:39:31
	 * @param hql HQL 目标表取别名为:o 无需后接空格   如:'from MyTable o'
	 * @param pageParams 分页参数
	 * @param params 用户输入参数，一般用来自于自定义Bean
	 * @return 结果集
	 */
	public abstract List<T> queryByPage(String hql, PageParams pageParams,
			Object... params);

	/**
	 * 分页查询带排序功能
	 * @author Leejean
	 * @create 2014-6-27下午03:39:31
	 * @param hql HQL语句
	 * @param pageIndex 页码
	 * @param pageSize 页大小
	 * @param sort 排序列
	 * @param order 升序asc&降序desc
	 * @param params 参数列表
	 * @return 结果集
	 */
	public abstract List<T> queryByPage(String hql, int pageIndex, int pageSize,
			String sort, String order, Object... params);

	/**
	 * 普通查询
	 * @param hql HQL语句
	 * @param params 参数列表
	 * @return 对象集合
	 */
	public abstract List<T> query(String hql, Object... params);
	/**
	 * 
	 * @author Leejean
	 * @create 2014年12月4日下午3:36:26
	 * @param clazz
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("hiding")
	public abstract <T> T query(Class<T> clazz,String hql, Object... params);
	/**
	 * 查询Object数组（使用在查询结果不为对象时，取值(Type)array[index]）
	 * @author Leejean
	 * @create 2014年12月4日下午3:36:35
	 * @param hql HQL
	 * @param params 参数
	 * @return
	 */
	public abstract List<Object[]> queryObjectArray(String hql, Object... params);
	public abstract List<Object[]> queryPageObjectArray(String hql, int pageIndex, int pageSize);
	/**
	 * 查询Object数组（使用在查询结果不为对象时，取值(Type)array[index]）
	 * @param hql
	 * @param pageParams
	 * @param params
	 * @return
	 */
	public abstract List<Object[]> queryObjectArray(String hql,PageParams pageParams, Object... params);
	
	/**
	 * 单值查询
	 * @author Leejean
	 * @create 2014-6-27下午03:39:31
	 * @param hql 
	 * @param params 
	 * @return
	 */
	public abstract Object unique(String hql, Object... params);
	
	/**
	 * 单值查询
	 * @author Leejean
	 * @create 2014-6-27下午03:39:31
	 * @param hql 
	 * @param params 
	 * @return
	 */
	public abstract T unique(Class<T> c,String hql, Object... params);

	/**
	 * 获得记录数(通常用于分页查询)
	 * @author Leejean
	 * @create 2014-6-27下午03:39:31 
	 * @param hql HQL语句
	 * @param params 参数列表
	 * @return 记录数
	 */
	public abstract long queryTotal(String hql, Object... params);

	/**
	 * 按条件删除或修改
	 * @author Leejean
	 * @create 2014-6-27下午03:39:31 
	 * @param hql
	 * @param params
	 * @return
	 */
	public abstract int updateByParams(String hql, Object... params);

	/**
	 * 按id删除对象
	 * @author Leejean
	 * @create 2014-7-5下午12:05:14
	 * @param id id
	 * @param clazz 目标对象
	 * @return
	 */
	public abstract int deleteById(String id, Class<T> clazz);

	/**
	 * 按条件删除或修改
	 * @author Leejean
	 * @create 2014-7-5下午12:05:51
	 * @param hql
	 * @param params
	 * @return
	 */
	public abstract int deleteByParams(String hql, Object... params);

	/**
	 * SQL拓展查询
	 * @author Leejean
	 * @create 2014-6-27下午03:39:31
	 * @param sql SQL语句
	 * @param params 参数列表
	 * @return 对象集合
	 */
	public abstract List<?> queryBySql(String sql, Object... params);
	/**
	 *说明：非注入方式获取查询session
	 * @param sql
	 * @return
	 * shisihai  
	 * 20162016年2月24日下午4:14:48
	 */
	public abstract List<?> queryBySqlExpand(String sql);
	/**
	 * 批量添加(高性能)
	 * @author Leejean
	 * @create 2014年11月17日上午9:32:52
	 * @param list VO集合
	 * @param clazz 目标PO类型，决定数据新增到哪个数据表
	 */
	@SuppressWarnings("rawtypes")
	public abstract void batchInsert(List list,Class clazz);
	
	/**
	 * 根据SQL语句更新
	 * @param sql
	 */
	public void updateInfo(String sql,List<Object> obj);
	/**
	 * 根据SQL语句更新
	 * @param sql
	 * @param obj
	 * @return
	 */
	public int updateBySql(String sql,List<Object> obj);
}
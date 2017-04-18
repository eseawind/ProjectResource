package com.shlanbao.tzsc.pms.sys.scj.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.Json;

/**
 * 
 * @author sunyuhang
 *
 */
public interface FivoServiceI {
	/**
	 * 增加节点
	 * @param uid pid
	 * @return
	 */
	public boolean saveFaverite(String uid,String pid);
	/**
	 * 删除节点
	 * @param id
	 * @return
	 */
	public boolean deleteFaverite(String id,String uid);
	/**
	 * 查询要插入的Faverite里是否已经插入了
	 * @param rid uid
	 * @return
	 */
	public boolean getByrId(String rid,String uid);
	/**
	 * 判断是不是父节点
	 * @param rid
	 * @return
	 */
	public List<String> getAllRidUid(String rid);
	
	
}
	

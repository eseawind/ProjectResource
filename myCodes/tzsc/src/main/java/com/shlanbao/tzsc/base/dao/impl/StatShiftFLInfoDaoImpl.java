package com.shlanbao.tzsc.base.dao.impl;


import org.springframework.stereotype.Repository;

import com.shlanbao.tzsc.base.dao.StatShiftFLInfoDaoI;
import com.shlanbao.tzsc.wct.eqm.shiftGl.beans.StatShiftFLInfo;

@Repository
public class StatShiftFLInfoDaoImpl extends BaseDaoImpl<StatShiftFLInfo> implements StatShiftFLInfoDaoI {
/**
 * 重写findbyid
 */
	@Override
	public StatShiftFLInfo findById(Long id) {
		try {
			return (StatShiftFLInfo) this.getCurrentSession().get(StatShiftFLInfo.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

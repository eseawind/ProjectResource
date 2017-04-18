package com.shlanbao.tzsc.pms.sch.shiftexchg.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sch.shiftexchg.beans.ShiftExchgBean;
import com.shlanbao.tzsc.pms.sch.shiftexchg.beans.ShiftExchgDetBean;
/**
 * 换班换牌
 * @author Leejean
 * @create 2014年11月25日下午1:23:10
 */
public interface ShiftExchgServiceI {
	/**
	 * 查询记录
	 * @author Leejean
	 * @create 2014年12月8日上午11:34:33
	 * @param shiftExchgBean type=1 换班  type=2 换牌
	 * @param pageParams
	 * @return
	 */
	public DataGrid getExchgs(ShiftExchgBean shiftExchgBean,PageParams pageParams);
	/**
	 * 根据换班换牌记录查询辅料结存
	 * @author Leejean
	 * @create 2014年12月8日下午7:03:05
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public DataGrid getExchgDetsByExchgId(String id) throws Exception;
	/**
	 * 添加换班换牌记录
	 * @author Leejean
	 * @create 2014年12月8日下午2:34:45
	 * @param shiftExchgBean
	 */
	public void addExchg(ShiftExchgBean shiftExchgBean);
	/**
	 * 编辑换班换牌记录
	 * @author Leejean
	 * @create 2014年12月8日下午2:34:47
	 * @param shiftExchgBean
	 */
	public void editExchg(ShiftExchgBean shiftExchgBean);
	/**
	 * 添加换班换牌记录明细
	 * @author Leejean
	 * @create 2014年12月8日下午2:34:50
	 * @param shiftExchgDetBean
	 */
	public void addExchgDet(ShiftExchgDetBean shiftExchgDetBean);
	/**
	 * 编辑换班换牌记录明细
	 * @author Leejean
	 * @create 2014年12月8日下午2:34:53
	 * @param shiftExchgDetBean
	 */
	public void editExchgDet(ShiftExchgDetBean shiftExchgDetBean);
	/**
	 * 删除换班换牌记录
	 * @author Leejean
	 * @create 2014年12月8日下午2:34:56
	 * @param id
	 */
	public void deleteExchg(String id);
	/**
	 * 删除换班换牌记录明细
	 * @author Leejean
	 * @create 2014年12月8日下午2:34:58
	 * @param id
	 */
	public void deleteExchgDet(String id);
	/**
	 * 
	 * @author Leejean
	 * @create 2014年12月3日下午1:52:35
	 * @param exchgId
	 * @return
	 * @throws Exception 
	 */
	public List<Combobox> getBomsWorkorderId(String workorderId,String exchgId) throws Exception;
	/**
	 * 根据ID获取换班换牌
	 * @author Leejean
	 * @create 2014年12月9日下午4:03:59
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public ShiftExchgBean getExchgById(String id) throws Exception;
	/**
	 * 根据ID获取换班换牌明细
	 * @author Leejean
	 * @create 2014年12月9日下午4:03:59
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public ShiftExchgDetBean getExchgDetById(String id) throws Exception;

	
}

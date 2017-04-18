package com.lanbao.dws.service.wct.eqpManager.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ibm.framework.dal.client.IPaginationDalClient;
import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.ibm.framework.dal.transaction.template.CallBackTemplate;
import com.lanbao.dws.common.data.Constants;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.wct.eqpManager.CosSparePartsBean;
import com.lanbao.dws.model.wct.eqpManager.EqmByLoginBean;
import com.lanbao.dws.model.wct.eqpManager.EqmFixRecBean;
import com.lanbao.dws.model.wct.eqpManager.EqmWheelCovelPlanBean;
import com.lanbao.dws.model.wct.eqpManager.EqmWheelCovelPlanParamBean;
import com.lanbao.dws.model.wct.eqpManager.SysUserBean;
import com.lanbao.dws.model.wct.menu.WctMenu;
import com.lanbao.dws.service.wct.eqpManager.IEqpManagerService;
/**
 * WCT菜单加载
 */
@Service
public class EqpManagerServiceImpl implements IEqpManagerService{
	@Autowired
    IPaginationDalClient dalClient;

	/**
	 * 查询wct顶部菜单
	 */
	@Override
	public List<WctMenu> queryWctTopMainMenu() {
		List<WctMenu> menus=dalClient.queryForList("wctMenu.queryTopMainMenu", null, WctMenu.class);
		StringBuffer url=new StringBuffer();
		for (WctMenu wctMenu : menus) {
			url.setLength(0);
			url.append(wctMenu.getMenu_url());
			if(StringUtil.notEmpty(url)){
				wctMenu.setMenu_url(url.append("&modul=").append(wctMenu.getModul()).toString());
			}
		}
		return menus;
	}
	/**
	 * 查询wct左侧主菜单(两级)
	 */
	@Override
	public void queryWctLeftMainMenu(Model model,WctMenu menu) {
		if(menu.getModul()!=null){
			List<WctMenu> mainMenus=dalClient.queryForList("wctMenu.queryLeftMainMenu", menu, WctMenu.class);
			List<WctMenu> childMenus=dalClient.queryForList("wctMenu.queryLeftChildMenu", menu, WctMenu.class);
			for (WctMenu mainMenu : mainMenus) {
				for (WctMenu childMenu : childMenus) {
					if(mainMenu.getId().equals(childMenu.getUpId()) && mainMenu.getModul().equals(childMenu.getModul())){
						mainMenu.getChildMenus().add(childMenu);
					}
				}
				//对子菜单排序
				Collections.sort(mainMenu.getChildMenus());
			}	
			//主菜单排序
			Collections.sort(mainMenus);	
			//对主菜单attr判断，如果为true，表示为默认页面，在model中放置
			model.addAttribute("leftMenu", mainMenus);
		}
	}
	
	
	
	/**
	 * [功能说明]：设备检修-查询登录用户
	 * @author wanchanghuang
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@Override
	public SysUserBean querySysUserByLoginName(EqmByLoginBean bean) {
		List<SysUserBean> list=dalClient.queryForList("eqpManager.querySysUserByLoginName", bean, SysUserBean.class);
		SysUserBean sub=null;
		String roleId="";
		String roleName="";
		if(list.size()>0 && list!=null){
			for(int i=0;i<list.size();i++){
				sub=list.get(i);
				roleId+=sub.getRoleId()+",";
				roleName+=sub.getRoleName()+",";
			}
			//截取后面,号
			sub=list.get(0);
			sub.setRoleId(roleId.substring(0, roleId.lastIndexOf(",")));
			sub.setRoleName(roleName.substring(0, roleName.lastIndexOf(",")));
			return sub;
		}
		return null;
	}
	
	/**
	 * [功能说明]：设备检修-查询卷烟机检修
	 * @author wanchanghuang
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@Override
	public PaginationResult<List<EqmWheelCovelPlanBean>> getResultPagePathForJxForRoler(
			Pagination pagination, EqmWheelCovelPlanBean bean) {
		return dalClient.queryForList("eqpManager.queryForEqmWheelCovelPlan", bean,EqmWheelCovelPlanBean.class, pagination);
	}
	
	/**
	 * [功能说明]：设备检修-查询卷烟机检修-检修项
	 * @author wanchanghuang
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@Override
	public List<EqmWheelCovelPlanParamBean> getResultPagePathForJxForRolerParam(
			EqmWheelCovelPlanParamBean bean) {
		//修改最后处理时间
		dalClient.execute("eqpManager.updateForEqmWheelCovelPlan", bean);
		//通过role_id,eqm_wcp_id得到相关角色维修项
		return dalClient.queryForList("eqpManager.queryForEqmWheelCovelPlanParam",bean,EqmWheelCovelPlanParamBean.class);
	}
	
	/**
	 * [功能说明]：设备检修-更换备品备件页
	 * @author wanchanghuang
	 * @date 2016年7月4日13:25:31
	 * 
	 * */
	@Override
	public PaginationResult<List<CosSparePartsBean>> getResultPagePathForJxForRolerSpareParts(
			Pagination pagination, CosSparePartsBean bean) {
		return dalClient.queryForList("eqpManager.queryForSpareParats", bean,CosSparePartsBean.class, pagination);
	}
	
	@Override
	public List<CosSparePartsBean> getRolerSpareParts(
			EqmWheelCovelPlanParamBean bean) {
		return dalClient.queryForList("eqpManager.queryForSpareParats", bean,CosSparePartsBean.class);
	}
	@Override
	public EqmWheelCovelPlanParamBean queryEqmWheelCovelPlanParamBeanById(
			EqmWheelCovelPlanParamBean bean) {
		
		return dalClient.queryForObject("eqpManager.queryEqmWheelCovelPlanParamBeanById", bean, EqmWheelCovelPlanParamBean.class);
	}
	
	/**
	 * [功能说明]：卷烟机检修，更换备品备件完成后，保存功能
	 *     1)修改备品备件表库存数量
	 *     2）添加备品备件记录表，添加记录
	 *     3)修改保养项状态及添加备注信息；
	 * @author wanchanghuang
	 * @date 2016年7月7日16:47:51
	 * @mether 事务处理
	 * */
	@Override
	public String saveSparePartOrParam(final String str, CosSparePartsBean cosspb, 
			final EqmFixRecBean eqmfrb, final EqmWheelCovelPlanParamBean eqmplanPb,final EqmByLoginBean elbBean) {
		dalClient.getTransactionTemplate().execute(new CallBackTemplate<String>() {
			@Override
			public String invoke() {
				//查询备品备件表 str= XF01#3,XF02#1 （ 备品备件ID#备件备件使用数量）
				String[] strf=str.split(",");
				if(!"".equals(strf[0]) ){ //验证是否使用了备品备件，如果没有使用，跳过去1,2步；
					CosSparePartsBean cspb=null;
					EqmFixRecBean erb=null;
					String[] valf=null;
					for(int i=0;i<(strf.length);i++){
						valf=strf[i].split("#");
						cspb=new CosSparePartsBean();
						cspb.setId(valf[0]);
						cspb.setNum(Double.parseDouble(valf[1]));
						dalClient.execute("eqpManager.updateCosSparePartsById", cspb);//修改备品备件库存量
						//添加备品备件历史记录表
						erb=new EqmFixRecBean();
						erb.setId(StringUtil.getUUID());
						erb.setShift_id(elbBean.getShiftId());//班次-从工单获取
						erb.setEqp_id(elbBean.getEqpId());//设备ID-从工单获取
						erb.setSpare_parts_id(valf[0]);//备品备件ID
						erb.setSpare_parts_num(Integer.parseInt(valf[1]));//备品备件使用数量
						erb.setEqmtrouble_id(eqmfrb.getEqmtrouble_id());//故障表ID-无法获取
						erb.setSource(eqmfrb.getSource());//来源
						erb.setRemark(eqmfrb.getRemark());//备注
						erb.setCreate_user_id(elbBean.getUserId());//创建人ID
						erb.setCreate_user_name(elbBean.getUserName());//创建人姓名
						erb.setCreate_user_time(new Date());//创建时间
						erb.setUpdate_user_id(elbBean.getUserId()); //最后修改人id
						erb.setUpdate_user_name(elbBean.getUserName());//最后修改人name
						erb.setUpdate_user_time(new Date());//最后修改时间
						dalClient.execute("eqpManager.insetEqmFixRceByObj", erb);
					}
				}
				//修改保养项
				eqmplanPb.setDes(eqmfrb.getRemark());//备注
				eqmplanPb.setStatus(Constants.STATUS1);//状态
				eqmplanPb.setActual_user_id(elbBean.getUserId());
				eqmplanPb.setActual_user_name(elbBean.getUserName());//实际保养人姓名
				eqmplanPb.setUpdate_time(new Date());
				dalClient.execute("eqpManager.updateParamById", eqmplanPb);
				return null;
			}
		});
        return null;
   }
	@Override
	public void updatePlan(EqmByLoginBean elbBean, EqmWheelCovelPlanBean bean) {
		bean.setLast_etime(new Date());//实际结束时间
		bean.setZx_user_id(elbBean.getUserId());//执行人ID
		bean.setZx_user_name(elbBean.getUserName());//执行人姓名
		bean.setSh_status(Constants.STATUS1);
		bean.setLast_etime(new Date());
		dalClient.execute("eqpManager.updatePlanById", bean);
	}
	
	/**
	 * [功能说明]：修改设备润滑项状态及设备润滑计划状态
	 * @author wanchanghuang
	 * @date 2016年7月28日11:25:51
	 * */
	@Override
	public void updatePlanOrParam(final EqmByLoginBean elbBean,
			final EqmWheelCovelPlanBean bean, final String chk_value) {
		dalClient.getTransactionTemplate().execute(new CallBackTemplate<String>() {
			@Override
			public String invoke() {
				//修改润滑计划
				bean.setLast_etime(new Date());//实际结束时间
				bean.setZx_user_id(elbBean.getUserId());//执行人ID
				bean.setZx_user_name(elbBean.getUserName());//执行人姓名
				bean.setSh_status(Constants.STATUS1);
				bean.setLast_etime(new Date());
				dalClient.execute("eqpManager.updatePlanById", bean);
				
				EqmWheelCovelPlanParamBean ecpb=new EqmWheelCovelPlanParamBean();
				//修改润滑项 将数组变成1','2 形式,方便做in处理 1,2,
				String chk_value2=chk_value.substring(0,chk_value.length()-1 );
				//chk_value2=chk_value2.replace(",",",'");
				ecpb.setId(chk_value2);
				ecpb.setStatus(Constants.STATUS1);
				ecpb.setUpdate_time(new Date());
				ecpb.setActual_user_id(elbBean.getUserId());
				ecpb.setActual_user_name(elbBean.getUserName());
				dalClient.execute("eqpManager.updateLubricatPlanOrParam", ecpb);
				return null;
			}
		});
	}
	
	
	
}

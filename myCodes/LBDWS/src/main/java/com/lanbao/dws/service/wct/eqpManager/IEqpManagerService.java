package com.lanbao.dws.service.wct.eqpManager;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.ibm.framework.dal.pagination.Pagination;
import com.ibm.framework.dal.pagination.PaginationResult;
import com.lanbao.dws.model.wct.eqpManager.CosSparePartsBean;
import com.lanbao.dws.model.wct.eqpManager.EqmByLoginBean;
import com.lanbao.dws.model.wct.eqpManager.EqmFixRecBean;
import com.lanbao.dws.model.wct.eqpManager.EqmWheelCovelPlanBean;
import com.lanbao.dws.model.wct.eqpManager.EqmWheelCovelPlanParamBean;
import com.lanbao.dws.model.wct.eqpManager.SysUserBean;
import com.lanbao.dws.model.wct.menu.WctMenu;
/**
 * WCT菜单加载
 */
public interface IEqpManagerService {
	/**
	 * 查询wct顶部菜单
	 * @return
	 */
	List<WctMenu> queryWctTopMainMenu();
	
	/**
	 * 查询wct左侧主菜单
	 * @return
	 */
	void queryWctLeftMainMenu(Model model,WctMenu menu);

	SysUserBean querySysUserByLoginName(EqmByLoginBean bean);

	PaginationResult<List<EqmWheelCovelPlanBean>> getResultPagePathForJxForRoler(
			Pagination pagination, EqmWheelCovelPlanBean bean);

	List<EqmWheelCovelPlanParamBean> getResultPagePathForJxForRolerParam(
			EqmWheelCovelPlanParamBean bean);

	PaginationResult<List<CosSparePartsBean>> getResultPagePathForJxForRolerSpareParts(Pagination pagination, CosSparePartsBean bean);

	List<CosSparePartsBean> getRolerSpareParts(EqmWheelCovelPlanParamBean bean);

	EqmWheelCovelPlanParamBean queryEqmWheelCovelPlanParamBeanById( EqmWheelCovelPlanParamBean bean);

	String saveSparePartOrParam(String str, CosSparePartsBean cosspb, EqmFixRecBean eqmfrb, EqmWheelCovelPlanParamBean eqmplanPb, EqmByLoginBean elbBean);

	void updatePlan(EqmByLoginBean elbBean, EqmWheelCovelPlanBean bean);

	void updatePlanOrParam(EqmByLoginBean elbBean, EqmWheelCovelPlanBean bean,
			String chk_value);
	
}

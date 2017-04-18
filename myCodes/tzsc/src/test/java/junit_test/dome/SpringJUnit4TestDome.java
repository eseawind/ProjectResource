package junit_test.dome;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.user.beans.UserBean;
import com.shlanbao.tzsc.pms.sys.user.service.UserServiceI;

@RunWith(SpringJUnit4ClassRunner.class)
//设置要加载的配置文件
@ContextConfiguration(locations = { 
		"classpath:spring.xml", 
		"classpath:spring-hibernate.xml" 
})
@TestExecutionListeners(listeners={
		DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class
})
//设置是否回滚数据 @TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
//@TransactionConfiguration(defaultRollback=false)
/**
 * Spring-JUnit4 测试案例(本来为测试dome，请不要再本测试类中新增代码，如需测试，请按模块新建测试案例)
 * 运行:选中待测试法方，右击run/debug as - JUnit Test 
 * @Rollback(false)
 * @author 
 * @create 2015年1月22日下午1:47:48
 */
public class SpringJUnit4TestDome extends AbstractTransactionalJUnit4SpringContextTests  {
	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	protected UserServiceI userService;
	@Before
	public void setUp() throws Exception {	

	}
	
	@Test
	public void findList(){
		long now0=System.currentTimeMillis();//开始毫秒
		
		UserBean userBean = new UserBean();
		PageParams pageParams = new PageParams();
		try{
			pageParams.setPage(1);pageParams.setRows(100);
			DataGrid gridList= userService.querySysUser(userBean,pageParams);	
			if(null!=gridList){
				log.info("总条数:"+gridList.getTotal());
				List<UserBean> list= gridList.getRows();
				for (UserBean user : list) {
					log.info(user.getId()+":"+user.getLoginName());
				}
			}
		}catch(Exception e){
			log.error("error:"+e.getMessage());
		}
		long over0=System.currentTimeMillis();//结束毫秒
		
		log.info((over0-now0)+"ms");
	}
	
	@Test
	public void test() {
		long cs=System.currentTimeMillis();
		List<UserBean> users = new ArrayList<UserBean>();
		int count= 10;
		for (int i = 1; i <= count; i++) {
			try {
				users.add(new UserBean("第"+i+"位用户"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		long ce=System.currentTimeMillis();
		System.out.println("构造"+count+"条数据,耗时："+(ce-cs)+" ms");
		long s=System.currentTimeMillis();
		try {
			userService.batchInsert(users);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		long e=System.currentTimeMillis();
		System.out.println("向用户表插入"+count+"条数据,耗时："+(e-s)+" ms");
	}
	@Test
	public void testConvert(){
		//userService.testConvert();
	}
	
	
	
}

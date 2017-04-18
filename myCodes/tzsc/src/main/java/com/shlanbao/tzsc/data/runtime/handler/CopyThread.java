package com.shlanbao.tzsc.data.runtime.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lanbao.dac.data.CommonData;
import com.shlanbao.tzsc.base.mapping.ChangeShiftDatas;
import com.shlanbao.tzsc.data.runtime.bean.DataSnapshot;
import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;
import com.shlanbao.tzsc.pms.sch.manualshift.beans.SchCalendarBean;
import com.shlanbao.tzsc.utils.params.EquipmentTypeDefinition;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.MathUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * DataSnapshot->NeedData 数据拷贝线程
 * @author Leejean
 * @create 2015年1月22日下午2:14:45
 */
public class CopyThread extends Thread{
	protected Logger log = Logger.getLogger(this.getClass());
	@Override
	public void run(){
		int code=0;
		try{
			//实例化允许允许装配的设备数据点信息
			Map<String,String> dataPonits = NeedDataPonits.getDataPonits();
			//获取原始数据
			List<EquipmentData> equipmentDatas = DataSnapshot.getInstance().getEqpData();
			//遍历每一台设备数据（设备信息，设备Data）
			for(EquipmentData data : equipmentDatas){
				String type = data.getType().toUpperCase();
				//PMS关注数据点
				if(dataPonits.containsKey(type)){//不允许装配的设备类型不会被实例化
					//复制INFO
					code=data.getEqp();
					EquipmentData equipmentData = new EquipmentData(data.getEqp(), data.getShift(), data.isOnline(),type);
					//获得每台设备的List<eqpDate>
					List<CommonData> commonDatas = data.getAllData();
					//点位集合
					List<String> points = StringUtil.splitToStringList(dataPonits.get(type), ",");
					//拷贝数据 
					//equipmentData-已经装配好的设备基本信息   commonDatas-设备下的数据集合 points-设备点位   type-类型
					copyDatas(equipmentData,commonDatas,points,type);
					//装配到NeedData
					NeedData.getInstance().setEquipmentData(equipmentData);	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//模拟条烟输送：【后期删除】
					//this.setLinkerData();
					//模拟成型机数据：【后期删除】
					//this.setFilterData();
					//模拟发射机数据：【后期删除】
					//this.setShooterData();
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				}
			}
		}catch(Exception e){
			log.error(code+":::CopyThread is error:"+e.getMessage());
		}
	}
	/**
	 	60001	管道1滤棒计int
		60002	管道2滤棒计int
		60003	管道3滤棒计int
		60004	管道4滤棒计int
		60005	管道5滤棒计int
		60006	管道6滤棒计int
		60007	管道7滤棒计int
		60008	管道8滤棒计int
		60009	管道9滤棒计int
		60010	管道10滤棒计int
		60011	管道1滤棒速int
		60012	管道2滤棒速int
		60013	管道3滤棒速int
		60014	管道4滤棒速int
		60015	管道5滤棒速int
		60016	管道6滤棒速int
		60017	管道7滤棒速int
		60018	管道8滤棒速int
		60019	管道9滤棒速int
		60020	管道10滤棒速int
		60021	管道1滤棒故障计int
		60022	管道2滤棒故障计int
		60023	管道3滤棒故障计int
		60024	管道4滤棒故障计int
		60025	管道5滤棒故障计int
		60026	管道6滤棒故障计int
		60027	管道7滤棒故障计int
		60028	管道8滤棒故障计int
		60029	管道9滤棒故障计int
		60030	管道10滤棒故障计int
		60031	模块10自清洁故障	int
		60032	模块10滤嘴故障int
		60033	模块10横放的过滤嘴int
		60034	模块10电机卡住int
		60035	料库门打开bool
		60036	压缩空气压力过低bool
		60037	频率变流器故障bool
		60038	料库料位最低bool
		60039	右侧料库料位最低bool
		60040	滤尘器已满bool
		60041	频率变流器Profibus故障bool
		60042	风机已关闭bool
		60043	紧急制动循环可信度bool
		60044	M1：模块风门打bool
		60045	M2：模块风门打bool
		60046	M3：模块风门打bool
		60047	M4：模块风门打bool
		60048	M5：模块风门打bool
		60049	M6：模块风门打bool
		60050	M7：模块风门打bool
		60051	M8：模块风门打bool
		60052	M9：模块风门打bool
		60053	M10：模块风门打开bool
		60054	M1：过滤嘴被卡bool
		60055	M2：过滤嘴被卡bool
		60056	M3：过滤嘴被卡bool
		60057	M4：过滤嘴被卡bool
		60058	M5：过滤嘴被卡bool
		60059	M6：过滤嘴被卡bool
		60060	M7：过滤嘴被卡bool
		60061	M8：过滤嘴被卡bool
		60062	M9：过滤嘴被卡bool
		60063	M10：过滤嘴被卡住bool
		60064	M1：自清洁失bool
		60065	M2：自清洁失bool
		60066	M3：自清洁失bool
		60067	M4：自清洁失bool
		60068	M5：自清洁失bool
		60069	M6：自清洁失bool
		60070	M7：自清洁失bool
		60071	M8：自清洁失bool
		60072	M9：自清洁失bool
		60073	M10：自清洁失败bool
		60074	M1：驱动系统故bool
		60075	M2：驱动系统故bool
		60076	M3：驱动系统故bool
		60077	M4：驱动系统故bool
		60078	M5：驱动系统故bool
		60079	M6：驱动系统故bool
		60080	M7：驱动系统故bool
		60081	M8：驱动系统故bool
		60082	M9：驱动系统故bool
		60083	M10：驱动系统故障bool
		60084	M1：不能识别鼓凸缘bool
		60085	M2：不能识别鼓凸缘bool
		60086	M3：不能识别鼓凸缘bool
		60087	M4：不能识别鼓凸缘bool
		60088	M5：不能识别鼓凸缘bool
		60089	M6：不能识别鼓凸缘bool
		60090	M7：不能识别鼓凸缘bool
		60091	M8：不能识别鼓凸缘bool
		60092	M9：不能识别鼓凸缘bool
		60093	M10：不能识别鼓凸缘bool
		60094	M1：驱动系统过热bool
		60095	M2：驱动系统过热bool
		60096	M3：驱动系统过热bool
		60097	M4：驱动系统过热bool
		60098	M5：驱动系统过热bool
		60099	M6：驱动系统过热bool
		60100	M7：驱动系统过热bool
		60101	M8：驱动系统过热bool
		60102	M9：驱动系统过热bool
		60103	M10：驱动系统过热bool
		60104	M1：驱动系统有效电压较bool
		60105	M2：驱动系统有效电压较bool
		60106	M3：驱动系统有效电压较bool
		60107	M4：驱动系统有效电压较bool
		60108	M5：驱动系统有效电压较bool
		60109	M6：驱动系统有效电压较bool
		60110	M7：驱动系统有效电压较bool
		60111	M8：驱动系统有效电压较bool
		60112	M9：驱动系统有效电压较bool
		60113	M10：驱动系统有效电压较高	bool
		60114	M1：电机电缆短bool
		60115	M2：电机电缆短bool
		60116	M3：电机电缆短bool
		60117	M4：电机电缆短bool
		60118	M5：电机电缆短bool
		60119	M6：电机电缆短bool
		60120	M7：电机电缆短bool
		60121	M8：电机电缆短bool
		60122	M9：电机电缆短bool
		60123	M10：电机电缆短路bool
		60124	模块1自清洁故障int
		60125	模块1滤嘴故障int
		60126	模块1横放的过滤嘴		int
		60127	模块1电机卡住	int
		60128	模块2自清洁故障		int
		60129	模块2滤嘴故障		int
		60130	模块2横放的过滤嘴	int
		60131	模块2电机卡住			int
		60132	模块3自清洁故障			int
		60133	模块3滤嘴故障			int
		60134	模块3横放的过滤嘴				int
		60135	模块3电机卡住		int
		60136	模块4自清洁故障		int
		60137	模块4滤嘴故障		int
		60138	模块4横放的过滤嘴			int
		60139	模块4电机卡住				int
		60140	模块5自清洁故障				int
		60141	模块5滤嘴故障				int
		60142	模块5横放的过滤嘴			int
		60143	模块5电机卡住				int
		60144	模块6自清洁故障				int
		60145	模块6滤嘴故障				int
		60146	模块6横放的过滤嘴			int
		60147	模块6电机卡住				int
		60148	模块7自清洁故障				int
		60149	模块7滤嘴故障				int
		60150	模块7横放的过滤嘴				int
		60151	模块7电机卡住				int
		60152	模块8自清洁故障				int
		60153	模块8滤嘴故障				int
		60154	模块8横放的过滤嘴			int
		60155	模块8电机卡住	int
		60156	模块9自清洁故障	int
		60157	模块9滤嘴故障	int
		60158	模块9横放的过滤嘴	int
		60159	模块9电机卡住		int
		60160	M1：密封靴位于上部位置				bool
		60161	M2：密封靴位于上部位置				bool
		60162	M3：密封靴位于上部位置				bool
		60163	M4：密封靴位于上部位置				bool
		60164	M5：密封靴位于上部位置				bool
		60165	M6：密封靴位于上部位置				bool
		60166	M7：密封靴位于上部位置				bool
		60167	M8：密封靴位于上部位置				bool
		60168	M9：密封靴位于上部位置				bool
		60169	M10：密封靴位于上部位置				bool
		60170	M1：密封靴位于下部位置				bool
		60171	M2：密封靴位于下部位置				bool
		60172	M3：密封靴位于下部位置				bool
		60173	M4：密封靴位于下部位置				bool
		60174	M5：密封靴位于下部位置				bool
		60175	M6：密封靴位于下部位置				bool
		60176	M7：密封靴位于下部位置				bool
		60177	M8：密封靴位于下部位置				bool
		60178	M9：密封靴位于下部位置			bool
		60179	M10：密封靴位于下部位置			bool
		60180	M1：驱动系统profibus故障		bool
		60181	M2：驱动系统profibus故障		bool
		60182	M3：驱动系统profibus故障		bool
		60183	M4：驱动系统profibus故障		bool
		60184	M5：驱动系统profibus故障		bool
		60185	M6：驱动系统profibus故障		bool
		60186	M7：驱动系统profibus故障		bool
		60187	M8：驱动系统profibus故障		bool
		60188	M9：驱动系统profibus故障		bool
		60189	M10：驱动系统profibus故障			bool
		60190	M1：横放的过滤嘴		bool
		60191	M2：横放的过滤嘴		bool
		60192	M3：横放的过滤嘴		bool
		60193	M4：横放的过滤嘴		bool
		60194	M5：横放的过滤嘴		bool
		60195	M6：横放的过滤嘴		bool
		60196	M7：横放的过滤嘴		bool
		60197	M8：横放的过滤嘴		bool
		60198	M9：横放的过滤嘴		bool
		60199	M10：横放的过滤嘴			bool
		60200	M1：紧急制动循环可信度			bool
		60201	M2：紧急制动循环可信度			bool
		60202	M3：紧急制动循环可信度			bool
		60203	M4：紧急制动循环可信度			bool
		60204	M5：紧急制动循环可信度			bool
		60205	M6：紧急制动循环可信度			bool
		60206	M7：紧急制动循环可信度			bool
		60207	M8：紧急制动循环可信度			bool
		60208	M9：紧急制动循环可信度			bool
		60209	M10：紧急制动循环可信度			bool
		60210	M1：驱动系统编码器故障			bool
		60211	M2：驱动系统编码器故障			bool
		60212	M3：驱动系统编码器故障			bool
		60213	M4：驱动系统编码器故障			bool
		60214	M5：驱动系统编码器故障			bool
		60215	M6：驱动系统编码器故障			bool
		60216	M7：驱动系统编码器故障			bool
		60217	M8：驱动系统编码器故障			bool
		60218	M9：驱动系统编码器故障			bool
		60219	M10：驱动系统编码器故障			bool
		60220	M1：驱动系统控制电压较低		bool
		60221	M2：驱动系统控制电压较低		bool
		60222	M3：驱动系统控制电压较低		bool
		60223	M4：驱动系统控制电压较低		bool
		60224	M5：驱动系统控制电压较低		bool
		60225	M6：驱动系统控制电压较低		bool
		60226	M7：驱动系统控制电压较低		bool
		60227	M8：驱动系统控制电压较低		bool
		60228	M9：驱动系统控制电压较低		bool
		60229	M10：驱动系统控制电压较低		bool
		60230	M1：驱动系统有效电压较低		bool
		60231	M2：驱动系统有效电压较低		bool
		60232	M3：驱动系统有效电压较低		bool
		60233	M4：驱动系统有效电压较低		bool
		60234	M5：驱动系统有效电压较低		bool
		60235	M6：驱动系统有效电压较低		bool
		60236	M7：驱动系统有效电压较低		bool
		60237	M8：驱动系统有效电压较低		bool
		60238	M9：驱动系统有效电压较低		bool
		60239	M10：驱动系统有效电压较低		bool
		60240	M1：驱动系统数字式输出端短路		bool
		60241	M2：驱动系统数字式输出端短路		bool
		60242	M3：驱动系统数字式输出端短路		bool
		60243	M4：驱动系统数字式输出端短路		bool
		60244	M5：驱动系统数字式输出端短路		bool
		60245	M6：驱动系统数字式输出端短路		bool
		60246	M7：驱动系统数字式输出端短路		bool
		60247	M8：驱动系统数字式输出端短路		bool
		60248	M9：驱动系统数字式输出端短路		bool
		60249	M10：驱动系统数字式输出端短路		bool
	 */
	private void setShooterData(){
			EquipmentData equipmentData = new EquipmentData();
			equipmentData.setEqp(131);
			equipmentData.setOnline(true);
			equipmentData.setShift(1);
			equipmentData.setType("YF27B");
			List<CommonData> datas = new ArrayList<CommonData>();
			datas.add(new CommonData("1","A线1#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
			datas.add(new CommonData("1","管道1滤棒计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("2","管道2滤棒" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("3","管道3滤棒计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("4","管道4滤棒计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("5","管道5滤棒计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("6","管道6滤棒计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("7","管道7滤棒计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("8","管道8滤棒计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("9","管道9滤棒计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("10","	管道10滤棒计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("11","	管道1滤棒速" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("12","	管道2滤棒速" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("13","	管道3滤棒速" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("14","	管道4滤棒速" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("15","	管道5滤棒速" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("16","	管道6滤棒速" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("17","	管道7滤棒速" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("18","	管道8滤棒速" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("19","	管道9滤棒速" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("20","	管道10滤棒速" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("21","	管道1滤棒故障计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("22","	管道2滤棒故障计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("23","	管道3滤棒故障计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("24","	管道4滤棒故障计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("25","	管道5滤棒故障计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("26","	管道6滤棒故障计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("27","	管道7滤棒故障计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("28","	管道8滤棒故障计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("29","	管道9滤棒故障计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("30","	管道10滤棒故障计" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("31","	模块10自清洁故障	" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("32","	模块10滤嘴故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("33","	模块10横放的过滤嘴" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("34","	模块10电机卡住" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("35","	料库门打开" ,"","","true","bool"));
			datas.add(new CommonData("36","	压缩空气压力过低" ,"","","true","bool"));
			datas.add(new CommonData("37","	频率变流器故障" ,"","","true","bool"));
			datas.add(new CommonData("38","	料库料位最低" ,"","","true","bool"));
			datas.add(new CommonData("39","	右侧料库料位最低" ,"","","true","bool"));
			datas.add(new CommonData("40","	滤尘器已满" ,"","","true","bool"));
			datas.add(new CommonData("41","	频率变流器Profibus故障" ,"","","true","bool"));
			datas.add(new CommonData("42","	风机已关闭" ,"","","true","bool"));
			datas.add(new CommonData("43","	紧急制动循环可信度" ,"","","true","bool"));
			datas.add(new CommonData("44","	M1：模块风门打" ,"","","true","bool"));
			datas.add(new CommonData("45","	M2：模块风门打" ,"","","true","bool"));
			datas.add(new CommonData("46","	M3：模块风门打" ,"","","true","bool"));
			datas.add(new CommonData("47","	M4：模块风门打" ,"","","true","bool"));
			datas.add(new CommonData("48","	M5：模块风门打" ,"","","true","bool"));
			datas.add(new CommonData("49","	M6：模块风门打" ,"","","true","bool"));
			datas.add(new CommonData("50","	M7：模块风门打" ,"","","true","bool"));
			datas.add(new CommonData("51","	M8：模块风门打" ,"","","true","bool"));
			datas.add(new CommonData("52","	M9：模块风门打" ,"","","true","bool"));
			datas.add(new CommonData("53","	M10：模块风门打开" ,"","","true","bool"));
			datas.add(new CommonData("54","	M1：过滤嘴被卡" ,"","","true","bool"));
			datas.add(new CommonData("55","	M2：过滤嘴被卡" ,"","","true","bool"));
			datas.add(new CommonData("56","	M3：过滤嘴被卡" ,"","","true","bool"));
			datas.add(new CommonData("57","	M4：过滤嘴被卡" ,"","","true","bool"));
			datas.add(new CommonData("58","	M5：过滤嘴被卡" ,"","","true","bool"));
			datas.add(new CommonData("59","	M6：过滤嘴被卡" ,"","","true","bool"));
			datas.add(new CommonData("60","	M7：过滤嘴被卡" ,"","","true","bool"));
			datas.add(new CommonData("61","	M8：过滤嘴被卡" ,"","","true","bool"));
			datas.add(new CommonData("62","	M9：过滤嘴被卡" ,"","","true","bool"));
			datas.add(new CommonData("63","	M10：过滤嘴被卡住" ,"","","true","bool"));
			datas.add(new CommonData("64","	M1：自清洁失" ,"","","true","bool"));
			datas.add(new CommonData("65","	M2：自清洁失" ,"","","true","bool"));
			datas.add(new CommonData("66","	M3：自清洁失" ,"","","true","bool"));
			datas.add(new CommonData("67","	M4：自清洁失" ,"","","true","bool"));
			datas.add(new CommonData("68","	M5：自清洁失" ,"","","true","bool"));
			datas.add(new CommonData("69","	M6：自清洁失" ,"","","true","bool"));
			datas.add(new CommonData("70","	M7：自清洁失" ,"","","true","bool"));
			datas.add(new CommonData("71","	M8：自清洁失" ,"","","true","bool"));
			datas.add(new CommonData("72","	M9：自清洁失" ,"","","true","bool"));
			datas.add(new CommonData("73","	M10：自清洁失败" ,"","","true","bool"));
			datas.add(new CommonData("74","	M1：驱动系统故" ,"","","true","bool"));
			datas.add(new CommonData("75","	M2：驱动系统故" ,"","","true","bool"));
			datas.add(new CommonData("76","	M3：驱动系统故" ,"","","true","bool"));
			datas.add(new CommonData("77","	M4：驱动系统故" ,"","","true","bool"));
			datas.add(new CommonData("78","	M5：驱动系统故" ,"","","true","bool"));
			datas.add(new CommonData("79","	M6：驱动系统故" ,"","","true","bool"));
			datas.add(new CommonData("80","	M7：驱动系统故" ,"","","true","bool"));
			datas.add(new CommonData("81","	M8：驱动系统故" ,"","","true","bool"));
			datas.add(new CommonData("82","	M9：驱动系统故" ,"","","true","bool"));
			datas.add(new CommonData("83","	M10：驱动系统故障" ,"","","true","bool"));
			datas.add(new CommonData("84","	M1：不能识别鼓凸缘" ,"","","true","bool"));
			datas.add(new CommonData("85","	M2：不能识别鼓凸缘" ,"","","true","bool"));
			datas.add(new CommonData("86","	M3：不能识别鼓凸缘" ,"","","true","bool"));
			datas.add(new CommonData("87","	M4：不能识别鼓凸缘" ,"","","true","bool"));
			datas.add(new CommonData("88","	M5：不能识别鼓凸缘" ,"","","true","bool"));
			datas.add(new CommonData("89","	M6：不能识别鼓凸缘" ,"","","true","bool"));
			datas.add(new CommonData("90","	M7：不能识别鼓凸缘" ,"","","true","bool"));
			datas.add(new CommonData("91","	M8：不能识别鼓凸缘" ,"","","true","bool"));
			datas.add(new CommonData("92","	M9：不能识别鼓凸缘" ,"","","true","bool"));
			datas.add(new CommonData("93","	M10：不能识别鼓凸缘" ,"","","true","bool"));
			datas.add(new CommonData("94","	M1：驱动系统过热" ,"","","true","bool"));
			datas.add(new CommonData("95","	M2：驱动系统过热" ,"","","true","bool"));
			datas.add(new CommonData("96","	M3：驱动系统过热" ,"","","true","bool"));
			datas.add(new CommonData("97","	M4：驱动系统过热" ,"","","true","bool"));
			datas.add(new CommonData("98","	M5：驱动系统过热" ,"","","true","bool"));
			datas.add(new CommonData("99","	M6：驱动系统过热" ,"","","true","bool"));
			datas.add(new CommonData("100","M7：驱动系统过热" ,"","","true","bool"));
			datas.add(new CommonData("101","M8：驱动系统过热" ,"","","true","bool"));
			datas.add(new CommonData("102","M9：驱动系统过热" ,"","","true","bool"));
			datas.add(new CommonData("103","M10：驱动系统过热" ,"","","true","bool"));
			datas.add(new CommonData("104","M1：驱动系统有效电压较" ,"","","true","bool"));
			datas.add(new CommonData("105","M2：驱动系统有效电压较" ,"","","true","bool"));
			datas.add(new CommonData("106","M3：驱动系统有效电压较" ,"","","true","bool"));
			datas.add(new CommonData("107","M4：驱动系统有效电压较" ,"","","true","bool"));
			datas.add(new CommonData("108","M5：驱动系统有效电压较" ,"","","true","bool"));
			datas.add(new CommonData("109","M6：驱动系统有效电压较" ,"","","true","bool"));
			datas.add(new CommonData("110","M7：驱动系统有效电压较" ,"","","true","bool"));
			datas.add(new CommonData("111","M8：驱动系统有效电压较" ,"","","true","bool"));
			datas.add(new CommonData("112","M9：驱动系统有效电压较" ,"","","true","bool"));
			datas.add(new CommonData("113","M10：驱动系统有效电压较高	" ,"","","true","bool"));
			datas.add(new CommonData("114","M1：电机电缆短" ,"","","true","bool"));
			datas.add(new CommonData("115","M2：电机电缆短" ,"","","true","bool"));
			datas.add(new CommonData("116","M3：电机电缆短" ,"","","true","bool"));
			datas.add(new CommonData("117","M4：电机电缆短" ,"","","true","bool"));
			datas.add(new CommonData("118","M5：电机电缆短" ,"","","true","bool"));
			datas.add(new CommonData("119","M6：电机电缆短" ,"","","true","bool"));
			datas.add(new CommonData("120","M7：电机电缆短" ,"","","true","bool"));
			datas.add(new CommonData("121","M8：电机电缆短" ,"","","true","bool"));
			datas.add(new CommonData("122","M9：电机电缆短" ,"","","true","bool"));
			datas.add(new CommonData("123","M10：电机电缆短路" ,"","","true","bool"));
			datas.add(new CommonData("124","模块1自清洁故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("125","模块1滤嘴故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("126","模块1横放的过滤嘴	" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("127","模块1电机卡住" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("128","模块2自清洁故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("129","模块2滤嘴故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("130","模块2横放的过滤嘴	" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("131","模块2电机卡住" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("132","模块3自清洁故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("133","模块3滤嘴故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("134","模块3横放的过滤嘴	" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("135","模块3电机卡住" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("136","模块4自清洁故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("137","模块4滤嘴故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("138","模块4横放的过滤嘴" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("139","模块4电机卡住" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("140","模块5自清洁故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("141","模块5滤嘴故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("142","模块5横放的过滤嘴" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("143","模块5电机卡住" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("144","模块6自清洁故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("145","模块6滤嘴故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("146","模块6横放的过滤嘴" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("147","模块6电机卡住" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("148","模块7自清洁故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("149","模块7滤嘴故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("150","模块7横放的过滤嘴	" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("151","模块7电机卡住" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("152","模块8自清洁故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("153","模块8滤嘴故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("154","模块8横放的过滤嘴" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("155","模块8电机卡住" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("156","模块9自清洁故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("157","模块9滤嘴故障" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("158","模块9横放的过滤嘴	" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("159","模块9电机卡住" ,"","",MathUtil.getRandomInt(1, 100).toString(),"int"));
			datas.add(new CommonData("160","M1：密封靴位于上部位置" ,"","","true","bool"));
			datas.add(new CommonData("161","M2：密封靴位于上部位置" ,"","","true","bool"));
			datas.add(new CommonData("162","M3：密封靴位于上部位置" ,"","","true","bool"));
			datas.add(new CommonData("163","M4：密封靴位于上部位置" ,"","","true","bool"));
			datas.add(new CommonData("164","M5：密封靴位于上部位置" ,"","","true","bool"));
			datas.add(new CommonData("165","M6：密封靴位于上部位置" ,"","","true","bool"));
			datas.add(new CommonData("166","M7：密封靴位于上部位置" ,"","","true","bool"));
			datas.add(new CommonData("167","M8：密封靴位于上部位置" ,"","","true","bool"));
			datas.add(new CommonData("168","M9：密封靴位于上部位置" ,"","","true","bool"));
			datas.add(new CommonData("169","M10：密封靴位于上部位置" ,"","","true","bool"));
			datas.add(new CommonData("170","M1：密封靴位于下部位置" ,"","","true","bool"));
			datas.add(new CommonData("171","M2：密封靴位于下部位置" ,"","","true","bool"));
			datas.add(new CommonData("172","M3：密封靴位于下部位置" ,"","","true","bool"));
			datas.add(new CommonData("173","M4：密封靴位于下部位置" ,"","","true","bool"));
			datas.add(new CommonData("174","M5：密封靴位于下部位置" ,"","","true","bool"));
			datas.add(new CommonData("175","M6：密封靴位于下部位置" ,"","","true","bool"));
			datas.add(new CommonData("176","M7：密封靴位于下部位置" ,"","","true","bool"));
			datas.add(new CommonData("177","M8：密封靴位于下部位置" ,"","","true","bool"));
			datas.add(new CommonData("178","M9：密封靴位于下部位置" ,"","","true","bool"));
			datas.add(new CommonData("179","M10：密封靴位于下部位置" ,"","","true","bool"));
			datas.add(new CommonData("180","M1：驱动系统profibus故障" ,"","","true","bool"));
			datas.add(new CommonData("181","M2：驱动系统profibus故障" ,"","","true","bool"));
			datas.add(new CommonData("182","M3：驱动系统profibus故障" ,"","","true","bool"));
			datas.add(new CommonData("183","M4：驱动系统profibus故障" ,"","","true","bool"));
			datas.add(new CommonData("184","M5：驱动系统profibus故障" ,"","","true","bool"));
			datas.add(new CommonData("185","M6：驱动系统profibus故障" ,"","","true","bool"));
			datas.add(new CommonData("186","M7：驱动系统profibus故障" ,"","","true","bool"));
			datas.add(new CommonData("187","M8：驱动系统profibus故障" ,"","","true","bool"));
			datas.add(new CommonData("188","M9：驱动系统profibus故障" ,"","","true","bool"));
			datas.add(new CommonData("189","M10：驱动系统profibus故障" ,"","","true","bool"));
			datas.add(new CommonData("190","M1：横放的过滤嘴","","","true","bool"));
			datas.add(new CommonData("191","M2：横放的过滤嘴","","","true","bool"));
			datas.add(new CommonData("192","M3：横放的过滤嘴","","","true","bool"));
			datas.add(new CommonData("193","M4：横放的过滤嘴","","","true","bool"));
			datas.add(new CommonData("194","M5：横放的过滤嘴","","","true","bool"));
			datas.add(new CommonData("195","M6：横放的过滤嘴","","","true","bool"));
			datas.add(new CommonData("196","M7：横放的过滤嘴","","","true","bool"));
			datas.add(new CommonData("197","M8：横放的过滤嘴","","","true","bool"));
			datas.add(new CommonData("198","M9：横放的过滤嘴","","","true","bool"));
			datas.add(new CommonData("199","M10：横放的过滤嘴","","","true","bool"));
			datas.add(new CommonData("200","M1：紧急制动循环可信度" ,"","","true","bool"));
			datas.add(new CommonData("201","M2：紧急制动循环可信度" ,"","","true","bool"));
			datas.add(new CommonData("202","M3：紧急制动循环可信度" ,"","","true","bool"));
			datas.add(new CommonData("203","M4：紧急制动循环可信度" ,"","","true","bool"));
			datas.add(new CommonData("204","M5：紧急制动循环可信度" ,"","","true","bool"));
			datas.add(new CommonData("205","M6：紧急制动循环可信度" ,"","","true","bool"));
			datas.add(new CommonData("206","M7：紧急制动循环可信度" ,"","","true","bool"));
			datas.add(new CommonData("207","M8：紧急制动循环可信度" ,"","","true","bool"));
			datas.add(new CommonData("208","M9：紧急制动循环可信度" ,"","","true","bool"));
			datas.add(new CommonData("209","M10：紧急制动循环可信" ,"","","true","bool"));
			datas.add(new CommonData("210","M1：驱动系统编码器故障" ,"","","true","bool"));
			datas.add(new CommonData("211","M2：驱动系统编码器故障" ,"","","true","bool"));
			datas.add(new CommonData("212","M3：驱动系统编码器故障" ,"","","true","bool"));
			datas.add(new CommonData("213","M4：驱动系统编码器故障" ,"","","true","bool"));
			datas.add(new CommonData("214","M5：驱动系统编码器故障" ,"","","true","bool"));
			datas.add(new CommonData("215","M6：驱动系统编码器故障" ,"","","true","bool"));
			datas.add(new CommonData("216","M7：驱动系统编码器故障" ,"","","true","bool"));
			datas.add(new CommonData("217","M8：驱动系统编码器故障" ,"","","true","bool"));
			datas.add(new CommonData("218","M9：驱动系统编码器故障" ,"","","true","bool"));
			datas.add(new CommonData("219","M10：驱动系统编码器故障","","","true","bool"));
			datas.add(new CommonData("220","M1：驱动系统控制电压较低" ,"","","true","bool"));
			datas.add(new CommonData("221","M2：驱动系统控制电压较低" ,"","","true","bool"));
			datas.add(new CommonData("222","M3：驱动系统控制电压较低" ,"","","true","bool"));
			datas.add(new CommonData("223","M4：驱动系统控制电压较低" ,"","","true","bool"));
			datas.add(new CommonData("224","M5：驱动系统控制电压较低" ,"","","true","bool"));
			datas.add(new CommonData("225","M6：驱动系统控制电压较低" ,"","","true","bool"));
			datas.add(new CommonData("226","M7：驱动系统控制电压较低" ,"","","true","bool"));
			datas.add(new CommonData("227","M8：驱动系统控制电压较低" ,"","","true","bool"));
			datas.add(new CommonData("228","M9：驱动系统控制电压较低" ,"","","true","bool"));
			datas.add(new CommonData("229","M10：驱动系统控制电压较低" ,"","","true","bool"));
			datas.add(new CommonData("230","M1：驱动系统有效电压较低","","","true","bool"));
			datas.add(new CommonData("231","M2：驱动系统有效电压较低","","","true","bool"));
			datas.add(new CommonData("232","M3：驱动系统有效电压较低","","","true","bool"));
			datas.add(new CommonData("233","M4：驱动系统有效电压较低","","","true","bool"));
			datas.add(new CommonData("234","M5：驱动系统有效电压较低","","","true","bool"));
			datas.add(new CommonData("235","M6：驱动系统有效电压较低","","","true","bool"));
			datas.add(new CommonData("236","M7：驱动系统有效电压较低","","","true","bool"));
			datas.add(new CommonData("237","M8：驱动系统有效电压较低","","","true","bool"));
			datas.add(new CommonData("238","M9：驱动系统有效电压较低","","","true","bool"));
			datas.add(new CommonData("239","M10：驱动系统有效电压较低	" ,"","","true","bool"));
			datas.add(new CommonData("240","M1：驱动系统数字式输出端短路" ,"","","true","bool"));
			datas.add(new CommonData("241","M2：驱动系统数字式输出端短路" ,"","","true","bool"));
			datas.add(new CommonData("242","M3：驱动系统数字式输出端短路" ,"","","true","bool"));
			datas.add(new CommonData("243","M4：驱动系统数字式输出端短路" ,"","","true","bool"));
			datas.add(new CommonData("244","M5：驱动系统数字式输出端短路" ,"","","true","bool"));
			datas.add(new CommonData("245","M6：驱动系统数字式输出端短路" ,"","","true","bool"));
			datas.add(new CommonData("246","M7：驱动系统数字式输出端短路" ,"","","true","bool"));
			datas.add(new CommonData("247","M8：驱动系统数字式输出端短路" ,"","","true","bool"));
			datas.add(new CommonData("248","M9：驱动系统数字式输出端短路" ,"","","true","bool"));
			datas.add(new CommonData("249","M10：驱动系统数字式输出端短路" ,"","","true","bool"));
			equipmentData.setData(datas);
			NeedData.getInstance().setEquipmentData(equipmentData);	
		}
	/**
	 * 
	 *  01	A线1#链条电机状态
		02	A线2#链条电机状态
		03	A线3#链条电机状态
		04	A线4#链条电机状态
		05	A线5#链条电机状态
		06	A线6#链条电机状态
		07	A线7#链条电机状态
		08	A线8#链条电机状态
		09	B线1#链条电机状态
		10	B线2#链条电机状态
		11	B线3#链条电机状态
		12	B线4#链条电机状态
		13	B线5#链条电机状态
		14	B线6#链条电机状态
		15	B线7#链条电机状态
		16	B线8#链条电机状态
		17	B线9#链条电机状态
		18	B线10#链条电机状态
		19	1#提升机状态
		20	2#提升机状态
		21	3#提升机状态
		22	4#提升机状态
		23	5#提升机状态
		24	6#提升机状态
		25	7#提升机状态
		26	8#提升机状态
		27	9#提升机状态
		28	10#提升机状态
		29	11#提升机状态
		30	12#提升机状态
		31	13#提升机状态
		32	14#提升机状态
		33	15#提升机状态
		34	16#提升机状态
		35	17#提升机状态
		36	18#提升机状态
		37	1#排包机状态
		38	2#排包机状态
		39	3#排包机状态
		40	4#排包机状态
		41	5#排包机状态
		42	6#排包机状态
		43	1#提升机产量
		44	2#提升机产量
		45	3#提升机产量
		46	4#提升机产量
		47	5#提升机产量
		48	6#提升机产量
		49	7#提升机产量
		50	8#提升机产量
		51	9#提升机产量
		52	10#提升机产量
		53	11#提升机产量
		54	12#提升机产量
		55	13#提升机产量
		56	14#提升机产量
		57	15#提升机产量
		58	16#提升机产量
		59	17#提升机产量
		60	18#提升机产量
		61	1#排包机产量
		62	2#排包机产量
		63	3#排包机产量
		64	4#排包机产量
		65	5#排包机产量
		66	6#排包机产量

	 * @author Leejean
	 * @create 2015年1月19日上午10:43:44
	 */
	private void setLinkerData(){
		EquipmentData equipmentData = new EquipmentData();
		equipmentData.setEqp(151);
		equipmentData.setOnline(true);
		equipmentData.setShift(1);
		equipmentData.setType("TYSS");
		List<CommonData> datas = new ArrayList<CommonData>();
		datas.add(new CommonData("1","A线1#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("2","A线2#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("3","A线3#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("4","A线4#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("5","A线5#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("6","A线6#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("7","A线7#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("8","A线8#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("9","B线1#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("10","B线2#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("11","B线3#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("12","B线4#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("13","B线5#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("14","B线6#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("15","B线7#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("16","B线8#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("17","B线9#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("18","B线10#链条电机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("19","1#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("20","2#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("21","3#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("22","4#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("23","5#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("24","6#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("25","7#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("26","8#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("27","9#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("28","10#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("29","11#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("30","12#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("31","13#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("32","14#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("33","15#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("34","16#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("35","17#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("36","18#提升机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("37","1#排包机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("38","2#排包机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("39","3#排包机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("40","4#排包机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("41","5#排包机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("42","6#排包机状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("43","	1#提升机产量" ,"","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("44","	2#提升机产量" ,"","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("45","	3#提升机产量" ,"","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("46","	4#提升机产量" ,"","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("47","	5#提升机产量" ,"","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("48","	6#提升机产量" ,"","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("49","	7#提升机产量" ,"","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("50","	8#提升机产量" ,"","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("51","	9#提升机产量" ,"","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("52","	10#提升机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("53","	11#提升机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("54","	12#提升机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("55","	13#提升机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("56","	14#提升机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("57","	15#提升机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("58","	16#提升机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("59","	17#提升机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("60","	18#提升机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("61","	1#排包机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("62","	2#排包机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("63","	3#排包机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("64","	4#排包机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("65","	5#排包机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		datas.add(new CommonData("66","	6#排包机产量","","",MathUtil.getRandomInt(0, 100).toString(),"stirng"));
		equipmentData.setData(datas);
		NeedData.getInstance().setEquipmentData(equipmentData);	
	}
	/**
	 * 成型机模拟数据
	 * 	01	产量
		02	总废品
		03	启动废品
		04	取样总数
		05	盘纸计数
		06	每班效率
		07	运行时间
		08	停机时间
		09	设备状态
		10	当前故障
		11	当前车速
	 * @author Leejean
	 * @create 2015年1月26日下午2:51:25
	 */
	private void setFilterData(){
		EquipmentData equipmentData = new EquipmentData();
		equipmentData.setEqp(101);
		equipmentData.setOnline(true);
		equipmentData.setShift(1);
		equipmentData.setType("ZL26C");
		List<CommonData> datas = new ArrayList<CommonData>();
		datas.add(new CommonData("1","产量        " ,"","",MathUtil.getRandomInt(0, 1000).toString(),"int"));
		datas.add(new CommonData("2","总废品    " ,"","",MathUtil.getRandomInt(0, 50).toString(),"int"));
		datas.add(new CommonData("3","启动废品" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("4","取样总数" ,"","",MathUtil.getRandomInt(0, 100).toString(),"int"));
		datas.add(new CommonData("5","盘纸计数" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("6","每班效率" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("7","运行时间" ,"","",MathUtil.getRandomInt(0, 500).toString(),"int"));
		datas.add(new CommonData("8","停机时间" ,"","",MathUtil.getRandomInt(0, 500).toString(),"int"));
		datas.add(new CommonData("9","设备状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("10","当前故障" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("11","当前车速" ,"","",MathUtil.getRandomInt(0, 10000).toString(),"int"));
		equipmentData.setData(datas);
		NeedData.getInstance().setEquipmentData(equipmentData);	
		
		equipmentData = new EquipmentData();
		equipmentData.setEqp(102);
		equipmentData.setOnline(true);
		equipmentData.setShift(1);
		equipmentData.setType("ZL26C");
		datas = new ArrayList<CommonData>();
		datas.add(new CommonData("1","产量        " ,"","",MathUtil.getRandomInt(0, 1000).toString(),"int"));
		datas.add(new CommonData("2","总废品    " ,"","",MathUtil.getRandomInt(0, 50).toString(),"int"));
		datas.add(new CommonData("3","启动废品" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("4","取样总数" ,"","",MathUtil.getRandomInt(0, 100).toString(),"int"));
		datas.add(new CommonData("5","盘纸计数" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("6","每班效率" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("7","运行时间" ,"","",MathUtil.getRandomInt(0, 500).toString(),"int"));
		datas.add(new CommonData("8","停机时间" ,"","",MathUtil.getRandomInt(0, 500).toString(),"int"));
		datas.add(new CommonData("9","设备状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("10","当前故障" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("11","当前车速" ,"","",MathUtil.getRandomInt(0, 10000).toString(),"int"));
		equipmentData.setData(datas);
		NeedData.getInstance().setEquipmentData(equipmentData);	
		
		
		equipmentData = new EquipmentData();
		equipmentData.setEqp(103);
		equipmentData.setOnline(true);
		equipmentData.setShift(1);
		equipmentData.setType("ZL22C");
		datas = new ArrayList<CommonData>();
		datas.add(new CommonData("1","产量        " ,"","",MathUtil.getRandomInt(0, 1000).toString(),"int"));
		datas.add(new CommonData("2","总废品    " ,"","",MathUtil.getRandomInt(0, 50).toString(),"int"));
		datas.add(new CommonData("3","启动废品" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("4","取样总数" ,"","",MathUtil.getRandomInt(0, 100).toString(),"int"));
		datas.add(new CommonData("5","盘纸计数" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("6","每班效率" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("7","运行时间" ,"","",MathUtil.getRandomInt(0, 500).toString(),"int"));
		datas.add(new CommonData("8","停机时间" ,"","",MathUtil.getRandomInt(0, 500).toString(),"int"));
		datas.add(new CommonData("9","设备状态" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("10","当前故障" ,"","",MathUtil.getRandomInt(0, 3).toString(),"int"));
		datas.add(new CommonData("11","当前车速" ,"","",MathUtil.getRandomInt(0, 10000).toString(),"int"));
		equipmentData.setData(datas);
		NeedData.getInstance().setEquipmentData(equipmentData);	
	}
	
	
	DataHandler hand = null;
	/**
	 * 
	 * @author Leejean
	 * @create 2015年1月7日下午3:38:26
	 * @param equipmentData 当前机台数据
	 * @param commonDatas 源datas
	 * @param points 允许的数据点
	 * @param isFl 是否为辅料数据
	 * equipmentData-已经装配好的设备基本信息   commonDatas-设备下的数据集合 points-设备点位   type-类型
	 */
	private void copyDatas(EquipmentData equipmentData,List<CommonData> commonDatas,List<String> points,String type) {
		Map<String, Object> calcValues = DataHandler.getCalcValues();
		List<CommonData> needsCommonDatas =  new ArrayList<CommonData>();
		//SchCalendarBean scb=null; 
		//Integer shift=0;
		for (CommonData commonData : commonDatas) {
			String id = commonData.getId();
			//当前数据点在needDataPoints.properties有设置
			if(points.contains(id)){
				if(type.contains("_FL")){ //所有设备辅料处理
				  commonDesposeFLDatas(needsCommonDatas, commonDatas, calcValues, type, equipmentData);
				  continue;
				}/*else if("UNKNOWN".equals(type)){ 
					//提升机-传过来的班次
					scb= NeedData.getInstance().getScbean();
					if(scb!=null){
						//3-对应包装机code=33  4-34
						String cid="3"+id;
						shift=Integer.parseInt(scb.getMdShiftCode());
						//早班无需减提升机   只有中班，晚班需要减，因为提升机早中晚都是累加数据的。不能得到当班产量
						if(shift!=1){
							 equipmentData.setShift(shift);//由于提升机早中晚都传0，所以 从新覆盖班次ID
							 changTSJShift(equipmentData,commonData,needsCommonDatas,cid);
							 continue;
						}
					}
				}*/
				needsCommonDatas.add(commonData);
			}		
		}
		equipmentData.setData(needsCommonDatas);
	}
	
	/**
	 * 功能描述：提升机换班处理,处理完成后存放在快照
	 * @createTime 2015年10月26日10:11:58
	 * @author wanchanghuang
	 * */
	public void changTSJShift(EquipmentData equipmentData,CommonData commonData,List<CommonData> needsCommonDatas,String id){
		Map<Integer,List<ChangeShiftDatas>> mapListShiftDatas = NeedData.getInstance().getMapShiftDatas();
		Integer shiftf=equipmentData.getShift();
		if(mapListShiftDatas==null){
			//获得班次，日期，设备code信息
			ChangeShiftDatas csd=new ChangeShiftDatas();
			csd.setDate(DateUtil.formatDateToString(new Date(), "yyyy-MM-dd"));
			csd.setShift(shiftf);
			csd.setEquipment_code(id);
			mapListShiftDatas=AutomaticShift.getInstance().getMapChangeShiftDates(csd);
		}
		CommonData newTSJCommonData =  new CommonData();
		newTSJCommonData.setId(commonData.getId());
		newTSJCommonData.setDes(commonData.getDes());
		Double val=Double.valueOf(commonData.getVal());
		List<ChangeShiftDatas> listShiftDatas=mapListShiftDatas.get(shiftf);
		//提升机产量
		for(ChangeShiftDatas csd : listShiftDatas){
			if(id.equals(csd.getEquipment_code())){
				val=val-(csd.getQty()*250);
				if(val<0){
				   val=0d;	//提升机(中班-早班<0),默认负值0
				}
			}
		}
		//由于提升机的数据是不清空的，中班=中-早  、  晚班=晚-中-早
		newTSJCommonData.setVal(val.toString());
		needsCommonDatas.add(newTSJCommonData);
	}
	
	/**
	 * 【功能说明】：辅料公共处理方法-控制流转
	 * @createTime 2015年10月23日11:21:19
	 * @author   wanchanghuang 
	 * needsCommonDatas -空   
	 * commonDatas - 原设备下数据集合
	 * calcValues -map
	 * equipmentData -封装已知设备基础信息：班次，班组，转速等
	 * */
	private void commonDesposeFLDatas(List<CommonData> needsCommonDatas,List<CommonData> commonDatas,
			Map<String, Object> calcValues,String type,EquipmentData equipmentData){
			//得到设备型号
			String eqpType = type.substring(0, type.indexOf("_"));
			if(EquipmentTypeDefinition.getRoller().contains(eqpType)){ //卷烟机
				calcRollerData(needsCommonDatas,commonDatas,equipmentData.getEqp(),calcValues);
			}
			if(EquipmentTypeDefinition.getPacker().contains(eqpType)){ //包装机
				calcPackerData(needsCommonDatas,commonDatas,equipmentData.getEqp(),calcValues);				
			}
			if(EquipmentTypeDefinition.getFilter().contains(eqpType)){ //成型机
				calcFilterData(needsCommonDatas,equipmentData.getEqp(),commonDatas,calcValues);
			}	
	}
	
	/** 修改后的辅料数据 更新-未被修改的辅料数据 */
	private void addCommonData(List<CommonData> needsCommonDatas,
			CommonData commonData) {
		CommonData isExsitData=null;
		for (CommonData data : needsCommonDatas) {
			if(data.getId().equals(commonData.getId())){
				data.setVal(commonData.getVal());
				isExsitData = data;
				break;
			}
		}
		if(isExsitData==null){
			needsCommonDatas.add(commonData);
		}
	}
	/**
	 * 计算卷烟机辅料系数
	 * @author Leejean
	 * @create 2015年1月9日上午8:45:22
	 * @param commonData
	 * @param calcValues <类型，List<系数对象> >
	 */
	private void calcRollerData(List<CommonData> needsCommonDatas,List<CommonData> commonDatas,Integer eqp,
			Map<String, Object> calcValues) {
		/*
		1
		2	盘纸
		3	水松纸
		4         滤棒计数[滤棒计数取自[设备id/1000]上的ID=8，需要构造]
		*/
		Object object= calcValues.get(String.valueOf(eqp/1000));
		if(object!=null){	
			RollerCalcValue calcValue = (RollerCalcValue) object;
			for (CommonData commonData : commonDatas) {
				
				CommonData newCommonData =  new CommonData();
				newCommonData.setId(commonData.getId());
				newCommonData.setDes(commonData.getDes());
				newCommonData.setVal("0");//赋值0未初始值
				Double oldVal=0.0;
				if(StringUtil.notNull(commonData.getVal())){
					oldVal=Double.valueOf(commonData.getVal());
				}
				//Double oldVal = Double.valueOf(commonData.getVal());
				
				/*处理【卷烟纸】*/
				if(commonData.getId().equals("2")){
					
					//米=卷烟纸滚轴系数*脉冲数
					Double newVal = calcValue.getJuanyanzhiAxisValue()*oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 3).toString());
					
				}
				/*处理【水松纸】*/
				if(commonData.getId().equals("3")){
					//水松纸辅料系数=水松纸宽度(0.06m)*克重(1.2g/㎡)
					//千克=水松纸滚轴系数*水松纸辅料系数*脉冲数
					Double newVal = calcValue.getShuisongzhiAxisValue()*calcValue.getShuisongzhiValue()*oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 3).toString());
				}
				addCommonData(needsCommonDatas, newCommonData);
			}
		}
		
		/*处理【滤棒】*/
		//如五号卷烟机的主机数据的在eqp=5上面，则辅料在eqp=5000上
		//得到所有主机数据，需要找id=8的滤棒数据
		List<CommonData> rollerData = DataSnapshot.getInstance().getEquipmentData(eqp/1000).getAllData();
		String lvbangData = "0";
		if(rollerData!=null){
			lvbangData = this.getLvbangData(rollerData);
		}
		CommonData newCommonData = new CommonData("4", "滤棒计数", "", "", lvbangData, "double");
		addCommonData(needsCommonDatas, newCommonData);

	}
	private String getLvbangData(List<CommonData> rollerData){
		for (CommonData commonData : rollerData) {
			if(commonData.getId().equals("8")){
				return commonData.getVal();
			}
		}
		return "0";
	}
	/**
	 * 计算包装机辅料系数
	 * @author Leejean
	 * @create 2015年1月9日上午8:45:22
	 * @param commonData
	 * @param calcValues
	 */
	private void calcPackerData(List<CommonData> needsCommonDatas,List<CommonData> commonDatas,Integer eqp,
			Map<String, Object> calcValues) {
		/*
		1	铝箔纸
		2   NULL
		3	商标纸(小盒纸)
		4	小透纸
		5	条盒纸
		6          大透纸[val取自条盒纸计数，需要构造ID：6]
		*/
		Object object= calcValues.get(String.valueOf(eqp/1000));
		/*if(null==object||"".equals(object)){
			if(null==hand){
				hand = new DataHandler();
			}
			calcValues = hand.saveCalcValues("packer",eqp);
		}*/
		if(object!=null){	
			PackerCalcValue calcValue = (PackerCalcValue) object;
			for (CommonData commonData : commonDatas) {
				CommonData newCommonData =  new CommonData();
				newCommonData.setId(commonData.getId());
				newCommonData.setDes(commonData.getDes());
				newCommonData.setVal("0");//赋值0未初始值
				Double oldVal=0.0;
				if(StringUtil.notNull(commonData.getVal())){
					oldVal=Double.valueOf(commonData.getVal());
				}
				/*处理【铝箔纸（内衬纸）】*/
				if(commonData.getId().equals("1")){
					//内衬纸辅料系数（面积*克重）
					//千克=张*内衬纸辅料系数
					Double newVal = calcValue.getNeichenzhiValue()*oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 3).toString());
				}
				/*处理【商标纸(小盒纸)】 直接使用*/
				if(commonData.getId().equals("3")){
					Double newVal = oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 0).toString());
				}
				/*处理【小透纸（小盒膜）】*/
				if(commonData.getId().equals("4")){
					//千克=张*小盒膜辅料系数
					Double newVal = calcValue.getXiaohemoValue()*oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 3).toString());
				}
				/*处理【条盒纸】*/
				if(commonData.getId().equals("5")){
					
					Double newVal = oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 0).toString());
					
					//处理【大透纸(条盒膜)】
					CommonData tiaohemo = new CommonData();
					tiaohemo.setId("6");
					tiaohemo.setVal("0");//赋值0未初始值
					tiaohemo.setDes("条盒膜计数");
					Double tiaohemoOldVal = oldVal;//Double.valueOf(commonData.getVal());
					//千克=条盒膜系数*脉冲数(张数)
					Double tiaohemoNewVal = calcValue.getTiaohemoValue()*tiaohemoOldVal;
					tiaohemo.setVariable(commonData.getVal());
					tiaohemo.setVal(tiaohemoNewVal.toString());
					
					addCommonData(needsCommonDatas, tiaohemo);
					
				}
				addCommonData(needsCommonDatas, newCommonData);
			}
		}
		
		
	}
	/**
	 * 计算成型机辅料系数
	 * @author Leejean
	 * @create 2015年1月9日上午8:46:12
	 * @param commonData
	 * @param calcValues
	 * 
	 */
	private void calcFilterData(List<CommonData> needsCommonDatas,Integer eqp,List<CommonData> commonDatas,
			Map<String, Object> calcValues) {
		/*
		 1	滤棒盘纸
		*/
		Object object= calcValues.get(String.valueOf(eqp/1000));
		if(object!=null){	
			FilterCalcValue calcValue = (FilterCalcValue) object;
			
			for (CommonData commonData : commonDatas) {
				CommonData newCommonData =  new CommonData();
				newCommonData.setId(commonData.getId());
				newCommonData.setDes(commonData.getDes());
				newCommonData.setVal("0");//赋值0未初始值
				Double oldVal=0.0;
				if(StringUtil.notNull(commonData.getVal())){
					oldVal=Double.valueOf(commonData.getVal());
				}
				/*处理【滤棒盘纸】*/
				if(commonData.getId().equals("1")){
					//千克=张*小盒膜辅料系数
					Double newVal = calcValue.getPanzhiAxisValue()*oldVal;
					newCommonData.setVariable(commonData.getVal());
					newCommonData.setVal(MathUtil.roundHalfUp(newVal, 3).toString());
				}else{
					newCommonData.setVal(MathUtil.roundHalfUp(oldVal, 3).toString());
				}
				addCommonData(needsCommonDatas, newCommonData);
			}
		}
		
	}
}

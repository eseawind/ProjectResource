package sqlLiteBase;

public class SysConstants {
	public static  String ROLLERTAB="ROLLER_";//卷烟机表
	public static String ROLLER_ZBTAB="ROLLER_ZB_";//卷烟机嘴棒接收机
	
	public static  String PACKAGETAB="PACKAGE_";//包装机表
	public static  String FL_TAB="FL_";//辅料数据表
	
	
	public static  String BOXERTAB="BOXER_";//封箱机数据表
	public static  String STORAGERTAB="STORAGE_";//储烟机
	public static  String FILTERTAB="FILTER_";//成型机数据
	
	public static  String LAUNCHERTAB="LAUNCHER_";//发射机数据
	public static  String LIFTERTAB="LIFTER_";//提升机
	
	public static  String INSTALLTAB="INSTALL_";//装盘机
	public static  String UNINSTALLTAB="UNINSTALL_";//卸盘机
	
	//设备号
	public static int[] EQPCODEARRY={1,2,3,4,5,6,7,8,9,10,11,12,//卷烟机
									 201,202,203,204,205,206,207,208,209,210,211,212,//嘴棒接收机
									 31,32,33,34,35,36,37,38,39,40,41,42,//包装机
									 61,62,63,64,//封箱机
									 71,72,73,74,75,76,77,78,79,80,81,82,//储烟机
									 101,102,103,104,//成型机
									 131,132,133,134,//发射机
									 151,//条烟输送机
									 161,162,163,164,//装盘机
									 181,182,183,184,185,186,187,188,//卸盘机
									 31000,32000,33000,34000,35000,36000,37000,38000,39000,40000,41000,42000,//包装机辅料
									 101000,102000,103000,104000//成型机辅料
									 };
	//设备所需点位 start
	//卷烟机
	public static int[] JYJPOINTS={8,21,22,15,7,11,16,18,19};
	//嘴棒接收机
	public static int[] ZBJSJ={16,17};
	//储烟机
	public static int[] CYJ={8};
	
	
	//包装机主数据
	public static int[] BZJBASE={2,3,4,5,6,7,8};
	//包装机辅料
	public static int[] BZJFX={1,2,3,4,5};
	//提升机
	public static int[] TSJ={25,26,27,28,29,30,31,32,33,34,35,36};
	
	
	//发射机
	public static int[] FSJ={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	//卸盘机
	public static int[] XPJ={49};
	
	//成型机
	public static int[] CXJ={1,2,11,5,7,8};
	//成型机辅料
	//将包装机辅料添加一位5 即可
	//装盘机
	public static int[] ZPJ={1};
	
	//封箱机
	public static int[] FXJ={53,71};
	//end
}

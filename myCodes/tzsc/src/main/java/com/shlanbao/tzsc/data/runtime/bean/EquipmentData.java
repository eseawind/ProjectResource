package com.shlanbao.tzsc.data.runtime.bean;

import java.util.ArrayList;
import java.util.List;

import com.lanbao.dac.data.CommonData;
/**
 * 设备数据模型
 * @author Leejean
 * @create 2015年1月22日下午2:10:50
 */
public class EquipmentData {
	/**
	 * 设备编码 对应设备表MD_EQUIPMENT：code
	 * 设备辅料编码=设备编码*1000
	 */
	private int eqp;
	/**
	 * 班次编码  对应设备表MD_SHIFT：code
	 */
	private int shift;
	/**
	 * 
	 * 网络状态
	 */
	private boolean online = false;
	/**
	 * 设备类型 凡是:"设备类型_FL" 未该设备的辅料信息
	 */
	private String type;//设备型号
	
	
	private String name;//用户名
	private String team;//班组
	
	private String userId;//用户主键ID
	private String teamCode;//班组
	private String shiftCode;//班次
	private String workshopCode;//车间
	
	
	private boolean isPass;
	/**
	 * 数据点集合
	 */
	private List<CommonData> data;
	public EquipmentData() {
		data = new ArrayList<CommonData>();
	}
	
	public EquipmentData(int eqp, int shift, boolean online, String type) {
		super();
		this.eqp = eqp;
		this.shift = shift;
		this.online = online;
		this.type = type;
	}



	public EquipmentData(int eqp, String type) {
		super();
		this.eqp = eqp;
		this.type = type;
	}

	public void clear(){
		data.clear();
	}
	public int getShift() {
		return shift;
	}
	public void setShift(int shift) {
		this.shift = shift;			
	}
	/**
	 * 根据数据点获得数据
	 */
	public CommonData getData(String id){
		for(int i=0;i<data.size();i++){
			if (data.get(i).getId().equalsIgnoreCase(id)){
				return data.get(i);
			}
		}
		return null;
	}
	/**
	 * 根据数据点获得值
	 */
	public String getVal(String id){
		for(int i=0;i<data.size();i++){
			if (data.get(i).getId().equalsIgnoreCase(id)){
				return data.get(i).getVal();
			}
		}
		return "";
	}
	public void setData(CommonData cd){
		boolean b =false;
		for(int i=0;i<data.size();i++){
			if (data.get(i).getId().equalsIgnoreCase(cd.getId())){
				b = true;
				//if(!cd.getVal().trim().equals("")){
				//	data.set(i, cd);
				//}
				data.set(i, cd);
				return;
			}
		}
		if(!b){
			addData(cd);
		}
	}
	public List<CommonData> getAllData(){
		return data;
	}
	
	/**
	 * 添加或更新单一值
	 * @param dataDefine
	 */
	public void addData(CommonData cd){
		if(cd == null){
			return;
		}
		
		CommonData dd = this.getData(cd.getId());
		if (dd == null){
			data.add(cd);
		}
		else if(!cd.getVal().trim().equals("")){
			dd.setVal(cd.getVal());
		}
	}
	public void removeData(String id){
		for(int i=0;i<data.size();i++){
			if (data.get(i).getId().equalsIgnoreCase(id)){
				data.remove(i);
			}
		}
	}
	public int getEqp() {
		return eqp;
	}
	public void setEqp(int eqp) {
		this.eqp = eqp;
	}

	public boolean isOnline() {
		return online;
	}
	
	public void setOnline(boolean online) {
		this.online = online;
	}
	public void setData(List<CommonData> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "EquipmentData [eqp=" + eqp + ", shift=" + shift + ", online="
				+ online + ", data=" + data + "]";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<CommonData> getData() {
		return data;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	public String getWorkshopCode() {
		return workshopCode;
	}

	public void setWorkshopCode(String workshopCode) {
		this.workshopCode = workshopCode;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
	
}

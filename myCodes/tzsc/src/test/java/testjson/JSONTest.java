package testjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

public class JSONTest {

	/**
	 * JSON
	 */
	@Test
	public void testArray() {

		String[] arr = { "asd", "dfgd", "asd", "234" };
		JSONArray jsonarray = JSONArray.fromObject(arr);
		System.out.println(jsonarray);

	}

	// ����ת����JSON
	@Test
	public void testObjectToJSON() {
		UserInfo user = new UserInfo(1001, "得瑟得瑟");
		JSONArray jsonArray = JSONArray.fromObject(user);
		System.out.println(jsonArray);
	}

	// Mapת����json�� ����jsonObject
	@Test
	public void testMapToJSON() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", 1001);
		map.put("userName", "ss");
		map.put("userSex", "对对对");
		JSONObject jsonObject = JSONObject.fromObject(map);
		System.out.println(jsonObject);
	}

	// Listת����JSON
	@Test
	public void testListToJSON() {

		List<UserInfo> list = new ArrayList<UserInfo>();
		UserInfo user = new UserInfo(1001, "ss多少");
		list.add(user);
		list.add(user);
		list.add(user);

		JSONArray jsonArray = JSONArray.fromObject(list);
		System.out.println(jsonArray);
	}
	public static void main(String[] args ){
		JSONTest test = new JSONTest();
		test.testArray();
	}
}

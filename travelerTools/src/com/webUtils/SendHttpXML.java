package com.webUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.commonUtil.OperationException;
import com.commonUtil.StringUtils;

/**
 * <p>
 * 功能描述：调用API
 * </p>
 * 作者：SShi11 日期：Apr 6, 2017 1:10:21 PM
 */
public class SendHttpXML {
	/**
	 * <p>
	 * 功能描述：发送XML数据到API
	 * </p>
	 * 
	 * @param url
	 *            地址：http://。。。
	 * @param xml
	 *            字符串xml
	 * @param contentType
	 *            为本类型 默认为text/xml
	 * @param codeType
	 *            字符编码 默认为UTF-8
	 * @return
	 * @throws Exception
	 *             作者：SShi11 日期：Apr 6, 2017 1:10:35 PM
	 */
	public static boolean sendHttpXml(String url, String xml, String contentType, String codeType) throws Exception {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		String contentTypeValue = StringUtils.isEmpty(contentType) ? "text/xml" : contentType;
		String codeTypeValue = StringUtils.isEmpty(contentType) ? "UTF-8" : codeType;
		String content = contentTypeValue + ";charset=" + codeTypeValue;
		httpPost.addHeader("Content-Type", content);
		StringEntity stringEntity = new StringEntity(xml, codeTypeValue);
		httpPost.setEntity(stringEntity);
		HttpResponse response = httpClient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("异常：" + xml);
			return false;
		}
		return true;
	}

	/**
	 * <p>功能描述：发送map形式的数据</p>
	 *@param url
	 *@param paramsMap
	 *作者：SShi11
	 *日期：Apr 12, 2017 11:30:27 PM
	 * @throws OperationException 
	 */
	public void sendMapData(String url, Map<String, String> paramsMap) throws Exception {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		// 创建参数列表
		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		for (Entry<String, String> entry : paramsMap.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, Charset.forName("UTF-8"));
		httpPost.setEntity(uefEntity);
		// 提交请求
		HttpResponse response = httpClient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new OperationException("状态值：" + statusCode);
		}
	}

}

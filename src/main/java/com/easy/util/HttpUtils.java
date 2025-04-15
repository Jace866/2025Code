/**

 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AIO DATA Co. Ltd</p>
 * <p>2014年6月9日</p>
 * @author Jan 
 */
package com.easy.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * desc: 平台Http工具类
 * 
 * @author Jan  date:2014年6月9日
 */
public class HttpUtils {
	public final static String CONTENTTYPE_XML="text/xml";
	public final static String CONTENTTYPE_WWWFROM="application/x-www-form-urlencoded";
	public final static String CONTENTTYPE_HTML="text/HTML";
	public final static String CONTENTTYPE_JSON="application/json;charset=utf-8";
	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30*1000).setConnectTimeout(30*1000).build();
	private static RequestConfig requestConfig1 = RequestConfig.custom().setSocketTimeout(5*1000).setConnectTimeout(5*1000).setConnectionRequestTimeout(5*1000).build();
	
	/**
	* desc: Http Get 请求
	* @author: Jan  ,  2014年6月10日 上午10:41:39 
	* @param url
	* @param charset
	* @return
	*/
	public static String doGet(String url, String charset, String contentType){
		if(StringUtils.isBlank(url)){
			throw new RuntimeException("请求的地址为空");
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url.trim());
		String responseStr = null;
		try {
			httpGet.setConfig(requestConfig);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity(); 
			if (entity != null) {
				responseStr = EntityUtils.toString(entity, charset);
			}
		} catch (ClientProtocolException e) {
//			throw new BusiException(ConstEC.HTTP_IOEXCEPTION);
			throw new RuntimeException("httpClient 请求异常",e.getCause());
		}catch (IOException e) {
			throw new RuntimeException("httpClient IO异常",e.getCause());
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException("httpClient IO异常",e.getCause());
			}
		}
		return responseStr;
	}
	
	public static String doDelete(String url, String charset){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete();
		String responseStr = null;
		try {
			httpDelete.setConfig(requestConfig);
			HttpResponse httpResponse = httpClient.execute(httpDelete);
			HttpEntity entity = httpResponse.getEntity(); 
			if (entity != null) {
				responseStr = EntityUtils.toString(entity, charset);
			}
		} catch (ClientProtocolException e) {
			throw new RuntimeException("httpClient 请求异常",e.getCause());
		}catch (IOException e) {
			throw new RuntimeException("httpClient IO异常",e.getCause());
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException("httpClient 关闭异常",e.getCause());
			}
		}
		return responseStr;
	}

	/**
	* desc: 获取响应返回
	* @author: Jan  ,  2014年6月10日 上午11:33:44 

	* @return
	*/
//	private static String getResponseBodyAsString(HttpResponse response, String charset){
//		StringBuilder sb = new StringBuilder();
//		HttpEntity httpEntity = response.getEntity();
//		try{
//			if(httpEntity != null){
//				httpEntity = new BufferedHttpEntity(httpEntity);
//				InputStream is = httpEntity.getContent();
//				BufferedReader br = new BufferedReader(new InputStreamReader(is,charset));
//				String str;
//				while((str=br.readLine())!=null){
//					sb.append(str);
//				}
//				is.close();
//			}
//		}catch(IOException e){
//			throw new RuntimeException("处理响应返回异常",e.getCause());
//		}
//		return sb.toString();
//	}

	public static String doPost(String url, Object obj){
		if(StringUtils.isBlank(url)){
			throw new RuntimeException("请求地址为空");
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url.trim());
		String responseStr = null;
		// 解决中文乱码问题
		StringEntity stringEntity = new StringEntity(obj.toString(), "UTF-8");
		stringEntity.setContentEncoding("UTF-8");
		httpPost.setEntity(stringEntity);
		httpPost.addHeader("Content-Type", CONTENTTYPE_JSON);
		httpPost.setConfig(requestConfig);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseStr = EntityUtils.toString(entity, "utf-8");
			}
		} catch (ClientProtocolException e) {
			throw new RuntimeException("HttpClient 请求异常",e.getCause());
		} catch (IOException e) {
			//e.printStackTrace();
			throw new RuntimeException("IO异常"+e.getMessage(),e.getCause());
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException("httpClient 关闭异常",e.getCause());
			}
		}
		return responseStr;
	}

	public static String doPost1(String url, Object obj){
		if(StringUtils.isBlank(url)){
			throw new RuntimeException("请求地址为空");
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);
		//httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 8000);
		HttpPost httpPost = new HttpPost(url.trim());
		String responseStr = null;
		// 解决中文乱码问题
		StringEntity stringEntity = new StringEntity(obj.toString(), "UTF-8");
		stringEntity.setContentEncoding("UTF-8");
		httpPost.setEntity(stringEntity);
		httpPost.addHeader("Content-Type", CONTENTTYPE_JSON);
		httpPost.setConfig(requestConfig1);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseStr = EntityUtils.toString(entity, "utf-8");
			}
		} catch (ConnectTimeoutException e) {
			throw new RuntimeException("HttpClient 请求超时",e.getCause());
		} catch (ClientProtocolException e) {
			throw new RuntimeException("HttpClient 请求异常",e.getCause());
		} catch (IOException e) {
			throw new RuntimeException("IO异常"+e.getMessage(),e.getCause());
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException("httpClient 关闭异常",e.getCause());
			}
		}
		return responseStr;
	}

	/**
	* desc: HttpClient POST请求
	* @author: Jan  ,  2014年6月10日 上午11:23:52 
	* @param url
	* @param reqMap
	* @param charset
	* @return
	*/
	public static String doPost(String url, Map<String, String> reqMap, String charset,  String conentType){
		if(StringUtils.isBlank(url)){
			throw new RuntimeException("请求地址为空");
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url.trim());
		String responseStr = null;
		StringEntity httpEntity = getStringEntity(reqMap, charset);
		httpEntity.setContentType(conentType);
		httpPost.setEntity(httpEntity);
		httpPost.setConfig(requestConfig);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			 HttpEntity entity = response.getEntity(); 
			if (entity != null) {
				responseStr = EntityUtils.toString(entity, charset);
			}
		} catch (ClientProtocolException e) {
			throw new RuntimeException("HttpClient 请求异常",e.getCause());
		} catch (IOException e) {
			throw new RuntimeException("IO异常",e.getCause());
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException("httpClient 关闭异常",e.getCause());
			}
		}
		return responseStr;
	}
	
	public static String doPost(String url, String content, String charset,  String conentType){
		if(StringUtils.isBlank(url)){
			throw new RuntimeException("请求地址为空");
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url.trim());
		String responseStr = null;
		StringEntity httpEntity = new StringEntity(content, charset);
		httpEntity.setContentType(conentType);
		httpPost.setEntity(httpEntity);
		httpPost.setConfig(requestConfig);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			 HttpEntity entity = response.getEntity(); 
			if (entity != null) {
				responseStr = EntityUtils.toString(entity, charset);
			}
		} catch (ClientProtocolException e) {
			throw new RuntimeException("HttpClient 请求异常",e.getCause());
		} catch (IOException e) {
			throw new RuntimeException("IO异常",e.getCause());
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException("httpClient 关闭异常",e.getCause());
			}
		}
		return responseStr;
	}
	
		/**
		* desc: Http POST 请求
		* @author: Jan  ,2014年8月19日 下午5:17:28
		* @return
		*/
	public static byte[] doPost(String url, byte[] bytes,  String conentType){
		if(StringUtils.isBlank(url)){
			throw new RuntimeException("Http请求地址为空");
		}
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create(); 
		CloseableHttpClient httpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(url.trim());
		byte[] responseBytes = null;
		ByteArrayEntity httpEntity = new ByteArrayEntity(bytes);
		httpEntity.setContentType(conentType);
		httpPost.setEntity(httpEntity);
		httpPost.setConfig(requestConfig);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			 HttpEntity entity = response.getEntity(); 
			if (entity != null) {
				responseBytes = EntityUtils.toByteArray(entity);
			}
		} catch (ClientProtocolException e) {
			throw new RuntimeException("httpClient 请求异常",e.getCause());
		} catch (IOException e) {
			throw new RuntimeException("httpClient IO异常",e.getCause());
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException("httpClient 关闭异常",e.getCause());
			}
		}
		return responseBytes;
	}
	
	
	/**
	* desc: 获取请求对象
	* @author: Jan  ,  2014年6月10日 上午11:18:42 
	* @param reqMap
	* @param charset
	* @return
	*/
	private static  StringEntity getStringEntity(Map<String, String> reqMap, String charset){
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> reqEntity : reqMap.entrySet()){
			sb.append(reqEntity.getKey()).append("=").append(reqEntity.getValue()).append("&");
		}
		sb.deleteCharAt(sb.lastIndexOf("&"));
		//System.out.println(sb.toString());
		StringEntity stringEntity = new StringEntity(sb.toString(), charset);
		return stringEntity;
	}
	public static Map<String,String> getParameterMap(HttpServletRequest request){
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> reqMap = new HashMap<String, String>();
		String name = null;
		String value = null;
		for(Map.Entry<String, String[]> entry : paramMap.entrySet()){
			name = entry.getKey();
			Object tmp = entry.getValue();
			if(tmp == null){
				value = "";
			}else if(tmp instanceof String[]){
				String[] values = (String[]) tmp;
				value = values[0];
			}else{
				value = tmp.toString();
			}
			reqMap.put(name, value);
		}
		return reqMap;
	}
}

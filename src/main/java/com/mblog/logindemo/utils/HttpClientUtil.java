package com.mblog.logindemo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class HttpClientUtil {
	public static final Logger logger = Logger.getLogger(HttpClientUtil.class);
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("User-Agent","");
		map.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		map.put("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		map.put("Connection", "keep-alive");
		map.put("Upgrade-Insecure-Requests", "1");
		map.put("Connection", "keep-alive");
		map.put("host", "s.weibo.com");
		map.put("cookie","YF-Page-G0=4b5a51adf43e782f0f0fb9c1ea76df93|1565745027|1565745006;SUB=_2A25wVy8tDeRhGeFM7FMT8CbOzj2IHXVTJQflrDV8PUNbmtBeLWf3kW9NQK-V1Y3Ei0r6NUHzoCTobuSfzaIE7_jb;cross_origin_proto=SSL;WBStorage=edfd723f2928ec64|undefined;login_sid_t=72b18f7901b64f538c936d6e7037552b;WBtopGlobal_register_version=307744aa77dd5677;SINAGLOBAL=881444138440.0574.1565745025208;Apache=881444138440.0574.1565745025208;YF-V5-G0=70942dbd611eb265972add7bc1c85888;wb_view_log_7271208251=1920*10801;Ugrow-G0=1ac418838b431e81ff2d99457147068c;wvr=6;webim_unReadCount=%7B%22time%22%3A1565745072729%2C%22dm_pub_total%22%3A1%2C%22chat_group_client%22%3A0%2C%22allcountNum%22%3A27%2C%22msgbox%22%3A0%7D;SSOLoginState=1565745002;SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5J1766jDiai4IZI8Gyn7d85JpX5K2hUgL.FoMES02EehnESK22dJLoIEXLxKBLBo.L1h-LxKMLBKzL12BLxKBLB.BLBK5LxKqL1h2LBKnLxK-LBo2LBo2t;SCF=AtD91KGt9Vu1F43z4LcrgMTK12cJ_Y87OOadSWiQYLUTL1AUb98XjwYuYpa8pbsAqA769973a2iDtcB_LgrJFw8.;_s_tentry=-;un=17030443536;UOR=,,login.sina.com.cn;SUHB=0nluotwblzFg6c;ALF=1597281020;ULV=1565745025217:1:1:1:881444138440.0574.1565745025208:;path=/; domain=.weibo.com");
		String url = "https://s.weibo.com/weibo?q=%E6%B5%81%E6%98%9F%E9%9B%A8&typeall=1&suball=1&timescope=custom::2019-08-14-23&Refer=g&page=2";
		String s = "";
		try {
			JSONObject result_json = new JSONObject();
			s = get(null, null, url, 0,"utf-8");
//			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * get请求
	 * @param header_map
	 * @param proxy	
	 * @param url
	 * @return
	 */
	public static String get(HashMap<String, String> header_map, String[] proxy, String url,int retryTimes) {
		String content = null;
		int retry_num = 0;
		do {
			CloseableHttpClient httpClient = null;
			CloseableHttpResponse response = null;
			HttpGet httpget = null;
			try {
				RequestConfig config = httpclientConfig(proxy);
				httpClient = HttpClients.custom().setDefaultRequestConfig(config).build(); // .setSslcontext(enableSSL()).
				httpget = new HttpGet(url);
				if(null != header_map && header_map.size() > 0){
					for (String k : header_map.keySet()) {
						httpget.addHeader(k, header_map.get(k));
					}
				}
				response = httpClient.execute(httpget);
				int statusCode = response.getStatusLine().getStatusCode();
  				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					content = EntityUtils.toString(entity);
		            FileWriter fw = new FileWriter("c.html");
		            BufferedWriter bw = new BufferedWriter(fw);
		            bw.write(content);
		            bw.close();
		            fw.close();
				}else if(statusCode == 302 || statusCode == 301){
//					String redirectUrl = response.getFirstHeader("location").getValue();
//					logger.info(redirectUrl);
				}else if(statusCode == 404){
					content = "404";
//					logger.info("请求路径错误:"+url);
				}
				
				closeRequest(response,httpget,httpClient);
				break;
			} catch (Exception e) {
				e.printStackTrace();
				closeRequest(response,httpget,httpClient);
				retry_num++;
			}
		} while(retry_num < retryTimes);
		return content;
	}
	
	
	/**
	 * 下载验证码
	 * @param header_map
	 * @param proxy	
	 * @param url
	 * @return
	 */
	public static void httpGetImg(HashMap<String, String> header_map, String[] proxy, String url,int retryTimes) {
		HttpEntity entity = null;
		int retry_num = 0;
		InputStream inputStream = null;
		do {
			CloseableHttpClient httpClient = null;
			CloseableHttpResponse response = null;
			HttpGet httpget = null;
			try {
				RequestConfig config = httpclientConfig(proxy);
				httpClient = HttpClients.custom().setDefaultRequestConfig(config).build(); // .setSslcontext(enableSSL()).
				httpget = new HttpGet(url);
				if(null != header_map && header_map.size() > 0){
					for (String k : header_map.keySet()) {
						httpget.addHeader(k, header_map.get(k));
					}
				}
				response = httpClient.execute(httpget);
  				if (response.getStatusLine().getStatusCode() == 200) {
					entity = response.getEntity();
					BufferedInputStream ins = new BufferedInputStream(entity.getContent()); 
					FileOutputStream fos = new FileOutputStream("captcha.png");
					byte[] buffer = new byte[4096]; 
					int len = 0; 
					while((len = ins.read(buffer)) > -1) { 
						fos.write(buffer, 0, len); 
					} 
					fos.close();
				}
				int statusCode = response.getStatusLine().getStatusCode();
				if(statusCode == 302 || statusCode == 301){
					String redirectUrl = response.getFirstHeader("location").getValue();
					System.out.println(redirectUrl);
				}
				closeRequest(response,httpget,httpClient);
				break;
			} catch (Exception e) {
				e.printStackTrace();
				closeRequest(response,httpget,httpClient);
				retry_num++;
			}
		} while(retry_num < retryTimes);
		return ;
	}
	
	/**
	 * 关闭连接
	 * @param response
	 * @param httpget
	 * @param httpClient
	 */
	private static void closeRequest(CloseableHttpResponse response, HttpGet httpget, CloseableHttpClient httpClient) {
		try {
			if (null != response) {
				response.close();
			}
			httpget.abort();
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 配置初始化
	 * 
	 * @return
	 */
	public static RequestConfig httpclientConfig(String... args) {
		if (null == args || args.length == 0) {
			RequestConfig defaultRequestConfig = RequestConfig.custom()
			.setSocketTimeout(1000 * 30).setConnectTimeout(1000 * 30)
			.setConnectionRequestTimeout(1000 * 30).setStaleConnectionCheckEnabled(false)
			.build();
			return defaultRequestConfig;
		} else {
			@SuppressWarnings("null")
			int port = Integer.valueOf(args[1]);
			HttpHost proxy = new HttpHost(args[0], port, "http");
			RequestConfig defaultRequestConfig = RequestConfig.custom()
//					.setSocketTimeout(1000 * 30).setConnectTimeout(1000 * 30)
//					.setConnectionRequestTimeout(1000 * 30)
					.setProxy(proxy)
					.build();
			return defaultRequestConfig;
		}

	}
	
	/**
	 * 调用接口获取任务
	 * @param url
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static String sendHttpPost(String url, String body) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.setEntity(new StringEntity(body));
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String responseContent = EntityUtils.toString(entity, "UTF-8"); 
		response.close();
		httpClient.close();
		return responseContent;
	}
	
	public static String sendHttpPost1(String url, String body) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("accept", " */*");
		httpPost.addHeader("accept-language", "zh-CN,zh;q=0.9");
		httpPost.addHeader("cache-control", "no-cache,no-cache");
//		httpPost.addHeader("content-length", "122");
		httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
		httpPost.addHeader("cookie", "PHPSESSID=ochrv0pfdm1shr54d33htgvtj0; __51cke__=; UM_distinctid=16a96630c635ca-0eaffe5294d71d-b781636-1fa400-16a96630c659c6; CNZZDATA1261560540=1129260612-1557300677-https%253A%252F%252Fwww.baidu.com%252F%7C1557300677; __tins__18646970=%7B%22sid%22%3A%201557303350397%2C%20%22vd%22%3A%202%2C%20%22expires%22%3A%201557305181522%7D; __51laig__=15");
		httpPost.addHeader("origin", "https://www.weibovideo.com");
		httpPost.addHeader("pragma", "no-cache");
		httpPost.addHeader("referer", "https://www.weibovideo.com/");
		httpPost.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
		httpPost.addHeader("x-requested-with", "XMLHttpRequest");
		httpPost.setEntity(new StringEntity(body));
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String responseContent = EntityUtils.toString(entity, "UTF-8"); 
		response.close();
		httpClient.close();
		return responseContent;
	}
	
	/**
	 * get请求
	 * @param header_map
	 * @param proxy	
	 * @param url
	 * @return
	 */
	public static String get(HashMap<String, String> header_map, String[] proxy, String url,int retryTimes,String charset) {
		String content = null;
		int retry_num = 0;
		do {
			CloseableHttpClient httpClient = null;
			CloseableHttpResponse response = null;
			HttpGet httpget = null;
			try {
				RequestConfig config = httpclientConfig(proxy);
				httpClient = HttpClients.custom().setDefaultRequestConfig(config).build(); // .setSslcontext(enableSSL()).
				httpget = new HttpGet(url);
				if(null != header_map && header_map.size() > 0){
					for (String k : header_map.keySet()) {
						httpget.addHeader(k, header_map.get(k));
					}
				}
				response = httpClient.execute(httpget);
				int statusCode = response.getStatusLine().getStatusCode();
  				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					content = EntityUtils.toString(entity,charset);
				}else if(statusCode == 302 || statusCode == 301){
//					String redirectUrl = response.getFirstHeader("location").getValue();
//					logger.info(redirectUrl);
				}else if(statusCode == 404){
					content = "404";
//					logger.info("请求路径错误:"+url);
				}
				closeRequest(response,httpget,httpClient);
				break;
			} catch (Exception e) {
				e.printStackTrace();
				closeRequest(response,httpget,httpClient);
				retry_num++;
			}
		} while(retry_num < retryTimes);
		return content;
	}

	/**
	 * post请求
	 * @param header_map
	 * @param params
	 * @param proxy
	 * @param url
	 * @param retryTimes
	 * @return
	 */
	public static JSONObject post(HashMap<String, String> header_map,HashMap<String, String> params, String[] proxy, String url,int retryTimes) {
		JSONObject json = new JSONObject();
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse  response = null;
		HttpClientContext context = HttpClientContext.create();
		HttpPost httpPost = null;
		try {
			BasicCookieStore cookieStore = new BasicCookieStore();
			context.setCookieStore(cookieStore);
			RequestConfig config = httpclientConfig(proxy);
			httpClient = HttpClients.custom().setDefaultRequestConfig(config).setDefaultCookieStore(cookieStore).build(); //.setSslcontext(enableSSL()).
			httpPost = new HttpPost();
			httpPost.setURI(new URI(url));
			for (String k : header_map.keySet()) {
				httpPost.addHeader(k, header_map.get(k));
			}
			ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
			for(String param_key : params.keySet()){
				list.add(new BasicNameValuePair(param_key,params.get(param_key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
			response = httpClient.execute(httpPost,context);
			String content = null;
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				content = EntityUtils.toString(entity,"utf-8");
				System.out.println(content);
			}
			if(statusCode == 302 || statusCode == 301){
				String redirectUrl = response.getFirstHeader("location").getValue();
				json.put("redirectUrl", redirectUrl);
			}
			List<Cookie> cookies = context.getCookieStore().getCookies();
			StringBuffer cookie_buffer = new StringBuffer();
			for(Cookie c : cookies){
				System.out.println(c.getName()+":"+c.getValue());
				cookie_buffer.append(c.getName()+"="+c.getValue()+";");
			}
			json.put("redirectCookie", cookie_buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			try {
				httpPost.abort();
				httpClient.close();
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return json;
	}
}

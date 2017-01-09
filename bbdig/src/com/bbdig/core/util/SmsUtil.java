package com.bbdig.core.util;
//package com.bbtu.core.util;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.URLEncoder;
// 
//public class SmsUtil {
//
//	public static void main(String[] args) {
//
//		System.out.println("sms test");
//	}
//
//	/**
//	 * http://web5.mobset.com/ 大鱼短信平台
//	 * 
//	 * @param mobile
//	 * @param content
//	 * @return
//	 * @throws IOException
//	 */
//	public static boolean sendSMSByDayu(String mobile, String content) throws IOException {
//
//		try {
//			String url = "http://gw.api.taobao.com/router/rest";
//			// 成为开发者，创建应用后系统自动生成
//			String appkey = "23311614";
//			String secret = "3ef6002f9a2fc2bcfc2b9460b58fd18f";
//
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setExtend("123456");
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("注册验证");
//			req.setSmsParamString("{\"code\":\"1234\",\"product\":\"阿里大鱼\",\"item\":\"阿里大鱼\"}");
//			req.setRecNum("13889475305");
//			req.setSmsTemplateCode("SMS_5023410");
//			AlibabaAliqinFcSmsNumSendResponse rsp;
//
//			rsp = client.execute(req);
//			//System.out.println(rsp.getBody());
//			return true;
//			
//		} catch (ApiException e) {
//			System.out.println("短信发送异常 ： " + e);
//			return false;
//		}
//
//		
//	}
//
//	/**
//	 * http://web5.mobset.com/ 至泽短信平台
//	 * 
//	 * @param mobile
//	 * @param content
//	 * @return
//	 * @throws IOException
//	 */
//	public static boolean sendSMS(String mobile, String content) throws IOException {
//		boolean sendStatus = false;
//
//		try {
//			URL url = null;
//			String corpID = "125196";// 账户名 TODO
//			String LoginName = "like";
//			String pwd = "210401";// 密码
//			String sendContent = URLEncoder.encode(content.replaceAll("<br/>", " "), "GBK");// GBK?
//			url = new URL("http://web.mobset.com/SDK/Sms_Send.asp?CorpID=" + corpID + "&LoginName=" + LoginName
//					+ "&passwd=" + pwd + "&send_no=" + mobile + "&msg=" + sendContent + "&Timer=");
//
//			URLConnection connection = url.openConnection();
//			connection.connect();
//			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//			String line;
//			String result = "";
//			while ((line = in.readLine()) != null) {
//				result += line;
//			}
//			in.close();
//
//			// TODO check result
//			sendStatus = true;
//
//			return sendStatus;
//		} catch (Exception e) {
//			System.out.println("短信发送异常 ： " + e);
//			return false;
//		}
//
//	}
//
//}

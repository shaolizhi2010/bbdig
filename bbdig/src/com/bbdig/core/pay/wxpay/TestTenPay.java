package com.bbdig.core.pay.wxpay;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class TestTenPay {

//	public static void main(String[] args) {
//
//		try {
//			String product_name = "测试用商品-名称";
//			int oid = 123;
//			int order_price = 4567;
//
//			HttpServletRequest request = null;
//			HttpServletResponse response = null;
//
//			RequestHandler reqHandler = new RequestHandler(request, response);
//			// 初始化
//			reqHandler.init();
//			reqHandler.init(WxPayConstants.APP_ID, WxPayConstants.APP_SECRET, WxPayConstants.APP_KEY,
//					WxPayConstants.PARTNER, WxPayConstants.PARTNER_KEY);
//
//			// 获取token值
//			String token = reqHandler.GetToken();
//
//			if (StringUtils.isNotBlank(token)) {
//
//				SortedMap<String, String> packageParams = new TreeMap<String, String>();
//				packageParams.put("bank_type", "WX"); // 商品描述
//				packageParams.put("body", product_name); // 商品描述
//				packageParams.put("notify_url", "http://123.57.226.66/admin/weixinpay/notify"); // 接收财付通通知的URL
//				packageParams.put("partner", WxPayConstants.PARTNER); // 商户号
//				packageParams.put("out_trade_no", oid + ""); // 商家订单号
//				packageParams.put("total_fee", order_price + ""); // 商品金额,以分为单位
//				// packageParams.put("total_fee", "1"); //商品金额,以分为单位
//				packageParams.put("spbill_create_ip", request.getRemoteAddr()); // 订单生成的机器IP，指用户浏览器端IP
//				packageParams.put("fee_type", "1"); // 币种，1人民币 66
//				packageParams.put("input_charset", "GBK"); // 字符编码
//
//				// 获取package包
//				String packageValue = reqHandler.genPackage(packageParams);
//
//				String noncestr = Sha1Util.getNonceStr();
//				String timestamp = Sha1Util.getTimeStamp();
//				String traceid = "mytestid_001";
//
//				// 设置支付参数
//				SortedMap<String, String> signParams = new TreeMap<String, String>();
//				signParams.put("appid", WxPayConstants.APP_ID);
//				signParams.put("appkey", WxPayConstants.APP_KEY);
//				signParams.put("noncestr", noncestr);
//				signParams.put("package", packageValue);
//				signParams.put("timestamp", timestamp);
//				signParams.put("traceid", traceid);
//
//				String sign = Sha1Util.createSHA1Sign(signParams);
//				// 增加非参与签名的额外参数
//				signParams.put("app_signature", sign);
//				signParams.put("sign_method", "sha1");
//
//				// 获取prepayId
//				String prepayid = reqHandler.sendPrepay(signParams);
//
//				if (StringUtils.isNotBlank(prepayid)) {
//					// 签名参数列表
//					SortedMap<String, String> prePayParams = new TreeMap<String, String>();
//					prePayParams.put("appid", WxPayConstants.APP_ID);
//					prePayParams.put("appkey", WxPayConstants.APP_KEY);
//					prePayParams.put("noncestr", noncestr);
//					prePayParams.put("package", "Sign=WXPay");
//					prePayParams.put("partnerid", WxPayConstants.PARTNER);
//					prePayParams.put("prepayid", prepayid);
//					prePayParams.put("timestamp", timestamp);
//					// 生成签名
//					sign = Sha1Util.createSHA1Sign(prePayParams);
//
//					// 输出参数
//					// json.put("retcode", "0");
//					// json.put("retmsg", "OK");
//					// json.put("appid", WeiXinPayConfig.APP_ID);
//					// json.put("partnerid", WeiXinPayConfig.PARTNER);
//					// json.put("noncestr", noncestr);
//					// json.put("package", "Sign=WXPay");
//					// json.put("prepayid", prepayid);
//					// json.put("timestamp", timestamp);
//					// json.put("sign", sign);
//					// json.put("errorCode", 200);
//					// json.put("errorMessage", "交易成功!");
//					// 测试帐号多个app测试，需要判断Token是否失效，否则重新获取一次
//					if (reqHandler.getLasterrCode() == "40001") {
//						token = reqHandler.getTokenReal();
//					}
//				} else {
//					// json.put("errorCode", 202);
//					// json.put("errorMessage", "错误：获取prepayId失败");
//				}
//			} else {
//				// json.put("errorCode", 203);
//				// json.put("errorMessage", "错误：获取不到Token.Error="
//				// + reqHandler.error);
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//	}

}

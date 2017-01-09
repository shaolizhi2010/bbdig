package com.bbdig.core.pay.wxpay;

public class WxPayConstants {
	
	// 支付完成后的回调处理页面
	public static String NOTIFY_URL = "http://likexiu.com/pay/weixinpay/notify"; //TODO
	//public static String NOTIFY_URL = "http://howard19980.oicp.net:8080/carService/admin/weixinpay/notify";
	public static String CODE_NOTIFY_URL="http://likexiu.com/pay/wxpay/code/callBack";
    
    public static String APP_ID = "wx361aeed6377d8924";
    
	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	public static String mchID = "1268559201";
	
	//这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改

	public static String key = "likexiulikekeji8764113387642200l";

    public static String APP_SECRET = "fe1c32ff863f1596c083763b2e10cef3";

    public static String APP_KEY = "likexiulikekeji8764113387642200l";

	// 商户号
    //public static String PARTNER = "1229213601";

	// 密钥
    public static String PARTNER_KEY = "ccbe3d4fd2ead3bfcad2d91ceb3e5b8f";
    
    public static String SIGN_METHOD = "sha1";//签名算法常量值
    
    public static String GATEURL = "https://api.weixin.qq.com/pay/genprepay?access_token=";//获取预支付id的接口url
    
    public static String mpPrePayURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//获取预支付id的接口url
    
}

package com.bbdig.core.constant;

public class Constants {
	public static final String LOGIN_USER = "loginUser";
	
	public static final int LOGIN_STATUS_TRUE = 1;//已登陆
	public static final int LOGIN_STATUS_FALSE = 2;//未登录
	
	public static final int SEX_MALE = 1;//男
	public static final int SEX_FEMALE = 2;//女
	
	//创建用户方式，1为平台创建的用户。
	public static final int USER_CREATOR_TYPE_PLATFORM = 1;
	public static final int USER_CREATOR_TYPE_USER =0;
	
	public static final String ERROR_MSG_SYSTEM = "操作未成功，请稍后重试";
	

	
	public static final int DELETE_FLAG_TRUE = 1;//删除
	public static final int DELETE_FLAG_FALSE = 0;//未删除
	
	
	public static final int BOOLEAN_TRUE  = 1;//true
	public static final int BOOLEAN_FALSE = 0;//false
	
	public static final int EVALUATE_MESSAGE  = 1;//评价
	public static final int EVALUATE_REOLY = 2;//回复
	
	public static final int USERLGOINNAME_EXIST = 1;//用户名存在
	public static final int USERLGOINNAME_NOTEXIST = 2;//用户名不存在
	
	public static final int ORDER_CREATOR_TYPE_PLATFORM = 1;//平台创建的订单
	
	public static final int PAGESIZE = 15;//分页条数
	
	public static final int INBUSINESS = 1;//营业中
	
	public static final String REFUSE_REASONS = "商户超过30分钟未接单，系统自动拒单";//
	
	public static final int MIN = 3*600000;//30分钟
	public static final int REFTYPE_USER = 1;//分享人类型 1用户2商家
	public static final int REFTYPE_SHOP = 2;//分享人类型 1用户2商家
	public static final int ISDEAL_NO = 0;//0未提醒客服
	public static final int ISDEAL_YES = 1;//1已提醒客服
	public static final double LATITUDE = 38.922176;
	public static final double LONGITUDE = 121.63356;
	
	public static final String nicknameExt = "网盘盒子分享";
	
}

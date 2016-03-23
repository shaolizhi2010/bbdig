package com.bbdig.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.bbdig.util.U;

@Entity
public class BaseEntity {
	@Id
	public ObjectId id;
	
	//统计
	public int clicks; //被点击次数
	public long keeps; //收藏次数
	public int showTimes; //展现次数
	public long ups; //推荐次数 被顶次数
	public long downs;//被踩次数
	public long favScore; //松鹤被喜爱程度评分
	public long score;//系统评分 得分

	// stastics
	public int scanTimes; // 呗后台分析程序扫描的次数，0标识没扫描过
	
	//创建人
	//@Reference(ignoreMissing = true, lazy = true)
	//public User createBy;
	
	//所有人 权限检查时候使用
	//@Reference(ignoreMissing = true, lazy = true)
	//public User owner;
	
	//日期跟踪
	public String createDate = U.dateTime();//添加日期
	public String lastModifyDate;//最后修改日期
	public String deleteDate;//删除日期
	
	public boolean deleteFlag;//删除标志，有些不执行真的删除
	public boolean useAble=true;//是否可用 
	
	//是否有系统自动生成
	public boolean isRobot;
	
	//权限控制
	//-10
	//0 只有自己 
	//1O 超级管理员
	//2O 管理员
	//5O 任意登录用户     
	//9O 所有人,包括未登录
	//详见 com.tgy.util.C
	public int authCreate;
	public int authDelete;
	public int authUpdate;
	public int authQuery;
	
	//system 
	public String vesion_="0.3";//数据对应的系统版本，每个版本所产生的数据格式可能不同，加此标记，便于处理数据。
	
	public void copyBasic(BaseEntity from){
		if(from==null){
			return ;
		}
		this.showTimes = from.showTimes;
		this.favScore = from.favScore;
		this.ups = from.ups;
		this.downs = from.downs;
		this.keeps = from.keeps;
	}

}

package com.bbdig.timer;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.core.app.AppConfig;
import com.bbdig.service.KeywordService;
import com.bbdig.service.TiebaBdyDigService;
import com.bbdig.service.UserService;


@Service("tiebaBdyTask")
public class TiebaBdyTask {
	
	@Autowired
	TiebaBdyDigService bdyDigService;
	
	@Autowired
	private KeywordService keywordService;
	
	@Autowired
	private UserService userService;

	private static Logger logger = Logger.getLogger(TiebaBdyTask.class);
	
 	
	public void run() {
	 	try {
	 		
	 		if(AppConfig.isTiebaDdyTaskRunningFlag()==true){//正在运行
	 			logger.warn("TiebaBdyTask 运行中 暂不运行");
	 			return;
	 		}
//	 		if(AppConfig.isResourceCheckTaskRunningFlag()==true){
//	 			logger.warn("ResourceCheckTask 运行中 退出");
//	 			return;
//	 		}
	 		AppConfig.setTiebaDdyTaskRunningFlag(true);
	 		
//	 		bdyDigService.digAndSave(  "资源",2); 
	 		
	 		//List<User> robots = userService.robotList();
	 		
	 		
	 		Random rand = new Random(); //每次随机抓取
	 		
	 		int randInt = rand.nextInt(10);
	 		
	 		if(randInt == 0){
	 			bdyDigService.digAndSave("thevoice");
	 			bdyDigService.digAndSave("fringe");
	 			bdyDigService.digAndSave("童话镇");
	 			bdyDigService.digAndSave("heroes");
	 			bdyDigService.digAndSave("豪斯医生");
	 			bdyDigService.digAndSave("海贼王");
		 		  bdyDigService.digAndSave("火影忍者");
		 		  bdyDigService.digAndSave("柯南");
	 		}
	 		if(randInt == 1){
	 			bdyDigService.digAndSave("真爱如血");
	 			bdyDigService.digAndSave("灵书妙探");
	 			bdyDigService.digAndSave("老爸老妈浪漫史");
	 			bdyDigService.digAndSave("疑犯追踪");
	 			bdyDigService.digAndSave("bones");
	 			
	 			bdyDigService.digAndSave("天降之物");
		 		  bdyDigService.digAndSave("灼眼的夏娜");
		 		  bdyDigService.digAndSave("滑头鬼之孙");
	 		}
	 		if(randInt == 2){
	 			bdyDigService.digAndSave("辛普森一家");
	 			bdyDigService.digAndSave("吸血鬼日记第三季");
	 			bdyDigService.digAndSave("拉字至上");
	 			bdyDigService.digAndSave("少狼");
	 			bdyDigService.digAndSave("revenge");
	 			bdyDigService.digAndSave("刀剑神域");
		 		  bdyDigService.digAndSave("网球王子");
		 		 
	 		}
	 		if(randInt == 3){
	 			bdyDigService.digAndSave("特效化妆师大对决");
	 			bdyDigService.digAndSave("南方公园");
	 			bdyDigService.digAndSave("凯莉日记");
	 			bdyDigService.digAndSave("吸血鬼日记");
	 			bdyDigService.digAndSave("行尸走肉");
	 			 bdyDigService.digAndSave("黑子的篮球");
		 		  bdyDigService.digAndSave("夏目友人帐");
	 		}
	 		if(randInt == 4){
	 			bdyDigService.digAndSave("行尸走肉第二季","行尸走肉");
	 			bdyDigService.digAndSave("尼基塔");
	 			bdyDigService.digAndSave("妙女神探");
	 			bdyDigService.digAndSave("六人行");
	 			bdyDigService.digAndSave("音乐资源");

		 		  bdyDigService.digAndSave("犬夜叉");
		 		  bdyDigService.digAndSave("进击的巨人");
	 		}
	 		if(randInt == 5){
	 			bdyDigService.digAndSave("gossip");
	 			bdyDigService.digAndSave("邪恶力量");
	 			bdyDigService.digAndSave("越狱");
	 			bdyDigService.digAndSave("glee");
	 			bdyDigService.digAndSave("绿箭侠");

		 		  bdyDigService.digAndSave("黑执事");
		 		  bdyDigService.digAndSave("叛逆的勒鲁什");
	 		}
	 		if(randInt == 6){
	 			bdyDigService.digAndSave("犯罪心理");
	 			bdyDigService.digAndSave("疯狂主妇");
	 			bdyDigService.digAndSave("破产姐妹");
	 			bdyDigService.digAndSave("lost");
	 			bdyDigService.digAndSave("维尼夫妇资源","维尼夫妇");
	 			bdyDigService.digAndSave("杀戮都市");
		 		  bdyDigService.digAndSave("妖精的尾巴");
		 		  
	 		}
	 		if(randInt == 7){
	 			bdyDigService.digAndSave("梅林传奇");
	 			bdyDigService.digAndSave("美少女的谎言");
	 			bdyDigService.digAndSave("兄弟连");
	 			bdyDigService.digAndSave("美剧复仇");
	 			bdyDigService.digAndSave("天才理论传");
	 			bdyDigService.digAndSave("家庭教师");
		 		  bdyDigService.digAndSave("银魂");
		 		  
	 		}
	 		if(randInt == 8){
	 			bdyDigService.digAndSave("灌篮高手");
		 		  bdyDigService.digAndSave("死神");
	 			bdyDigService.digAndSave("国土安全");
	 			bdyDigService.digAndSave("无耻之徒");
	 			bdyDigService.digAndSave("绝命毒师");
	 			bdyDigService.digAndSave("实习医生格蕾");
	 			bdyDigService.digAndSave("吸血鬼日记第四季","吸血鬼日记");
	 		}
	 		if(randInt == 9){
	 			bdyDigService.digAndSave("软件下载");
	 			bdyDigService.digAndSave("软件百度云");
	 			bdyDigService.digAndSave("3d电影下载","电影");
	 			bdyDigService.digAndSave("小说资源交流",8,null);
	 			bdyDigService.digAndSave("重生之资源大亨",8,null);
	 		}
	 		
	 		if(randInt>8){
	 			bdyDigService.digAndSave(  "小说下载",8,null); 
	 		}
	 		if(randInt>7){
	 			bdyDigService.digAndSave(  "云盘资源共享",2, null); 
		 		bdyDigService.digAndSave(  "权利的游戏",11,null);
		 		bdyDigService.digAndSave(  "斯巴达克斯",12,null); 
		 		bdyDigService.digAndSave(  "日剧",13, null);
		 		bdyDigService.digAndSave(  "考试",18,null);
		 		bdyDigService.digAndSave(  "生活大爆炸",14,null); 
		 		bdyDigService.digAndSave(  "电影",5, null);
	 		}
	 		if(randInt>6){
	 			bdyDigService.digAndSave(  "电子书",16,null); 
	 			bdyDigService.digAndSave(  "韩剧",34, null);
		 		bdyDigService.digAndSave(  "英剧",9, null);
		 		bdyDigService.digAndSave(  "行尸走肉",15,null);
		 		bdyDigService.digAndSave(  "考研资料",17,null); 
		 		bdyDigService.digAndSave(  "百度网盘",2, null);
		 		bdyDigService.digAndSave(  "高清下载",2, null);  
		 		bdyDigService.digAndSave(  "电影圈",5,null); 
	 		}	
 
	 		if(randInt>4){
	 			bdyDigService.digAndSave(  "教程",19,null);
		 		bdyDigService.digAndSave(  "动漫",10, null); 
		 		bdyDigService.digAndSave(  "动漫网盘",3, null);
		 		bdyDigService.digAndSave(  "游戏下载",20,null);
		 		bdyDigService.digAndSave(  "后宫动漫",10, null);
		 		bdyDigService.digAndSave(  "最新电影",5, null); 
	 		}
	 		if(randInt>4){
		 		//bdyDigService.digAndSave(  "腐女动漫",10, null); 
		 		bdyDigService.digAndSave(  "美剧",4, null); 
		 		bdyDigService.digAndSave(  "动漫资源",3, null); 
		 		bdyDigService.digAndSave(  "美剧资源站",4, null);
		 		bdyDigService.digAndSave(  "电影资源共享",5, null);
		 		bdyDigService.digAndSave(  "nba",14247, null);
		 		
		 		bdyDigService.digAndSave(  "泰剧",20858, null);
		 		bdyDigService.digAndSave(  "西部世界",27662, null);
		 		
	 		}
	 		
	 		AppConfig.setTiebaDdyTaskRunningFlag(false);
	 		
	 		/*
	 		List<HotKeywordsView> hotKeywords =  keywordService.hotKeywords(0,100);
	 		
	 		for( HotKeywordsView keyword : hotKeywords ){
	 				User robotUser = userService.getOrCreateRobotByNickname(keyword.getKeyword());
	 				if(robotUser!=null){
	 					bdyDigService.digAndSave(keyword.getKeyword() ,robotUser.getId());  //挖！
	 				}
	 				else{
	 					logger.warn("dig " + keyword.getKeyword() + " 失败，关键字被占 无法创建robot用户");
	 				}
	 		}
	 		*/
	 		
		} catch (Exception e) {
			logger.warn(e);
			AppConfig.setTiebaDdyTaskRunningFlag(false);
		} 
		
	}
	


}

package com.bbdig.entity;

public class TiebaDigHistory extends BaseEntity{
	
	public String tieziID; //tieba.baidu.com/12345 id=12345
	public int pageNum; //页数，如  tieba.baidu.com/12345?pn=2 pageNum是2 
	
	//这个帖子，曾经被爬虫发现过过多少次。
	//爬虫发现一个链接后，会先判断是否需要抓取他，单不论是否进行抓取，都标记一下
	//这样就可以大概知道，这个链接是否经常出现，出现的频率
	//如果一个链接（帖子），经常在首页出现，那就不同对待，比如就多抓几页
	public int foundTimes; 
	
	//public int digTimes; //抓取次数
	
	//上一次抓取的时间
	//如果一个帖子，虽然已经抓过了，但上次是n天前抓取的
	//那说明有更新，就在抓一次
	public String lastDigTime;
}

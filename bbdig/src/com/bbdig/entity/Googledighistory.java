package com.bbdig.entity;

import java.util.Date;

public class Googledighistory {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column googledighistory.id
	 * @mbggenerated  Fri May 27 17:51:09 CST 2016
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column googledighistory.keyword
	 * @mbggenerated  Fri May 27 17:51:09 CST 2016
	 */
	private String keyword;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column googledighistory.pageNum
	 * @mbggenerated  Fri May 27 17:51:09 CST 2016
	 */
	private Integer pagenum;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column googledighistory.lastDigTimeTs
	 * @mbggenerated  Fri May 27 17:51:09 CST 2016
	 */
	private Date lastdigtimets;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column googledighistory.id
	 * @return  the value of googledighistory.id
	 * @mbggenerated  Fri May 27 17:51:09 CST 2016
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column googledighistory.id
	 * @param id  the value for googledighistory.id
	 * @mbggenerated  Fri May 27 17:51:09 CST 2016
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column googledighistory.keyword
	 * @return  the value of googledighistory.keyword
	 * @mbggenerated  Fri May 27 17:51:09 CST 2016
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column googledighistory.keyword
	 * @param keyword  the value for googledighistory.keyword
	 * @mbggenerated  Fri May 27 17:51:09 CST 2016
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column googledighistory.pageNum
	 * @return  the value of googledighistory.pageNum
	 * @mbggenerated  Fri May 27 17:51:09 CST 2016
	 */
	public Integer getPagenum() {
		return pagenum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column googledighistory.pageNum
	 * @param pagenum  the value for googledighistory.pageNum
	 * @mbggenerated  Fri May 27 17:51:09 CST 2016
	 */
	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column googledighistory.lastDigTimeTs
	 * @return  the value of googledighistory.lastDigTimeTs
	 * @mbggenerated  Fri May 27 17:51:09 CST 2016
	 */
	public Date getLastdigtimets() {
		return lastdigtimets;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column googledighistory.lastDigTimeTs
	 * @param lastdigtimets  the value for googledighistory.lastDigTimeTs
	 * @mbggenerated  Fri May 27 17:51:09 CST 2016
	 */
	public void setLastdigtimets(Date lastdigtimets) {
		this.lastdigtimets = lastdigtimets;
	}
}
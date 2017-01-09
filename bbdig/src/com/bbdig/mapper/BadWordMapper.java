package com.bbdig.mapper;

import com.bbdig.entity.BadWord;
import com.bbdig.entity.BadWordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BadWordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bad_word
     *
     * @mbggenerated Mon Apr 11 16:37:16 CST 2016
     */
    int countByExample(BadWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bad_word
     *
     * @mbggenerated Mon Apr 11 16:37:16 CST 2016
     */
    int deleteByExample(BadWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bad_word
     *
     * @mbggenerated Mon Apr 11 16:37:16 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bad_word
     *
     * @mbggenerated Mon Apr 11 16:37:16 CST 2016
     */
    int insert(BadWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bad_word
     *
     * @mbggenerated Mon Apr 11 16:37:16 CST 2016
     */
    int insertSelective(BadWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bad_word
     *
     * @mbggenerated Mon Apr 11 16:37:16 CST 2016
     */
    List<BadWord> selectByExample(BadWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bad_word
     *
     * @mbggenerated Mon Apr 11 16:37:16 CST 2016
     */
    BadWord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bad_word
     *
     * @mbggenerated Mon Apr 11 16:37:16 CST 2016
     */
    int updateByExampleSelective(@Param("record") BadWord record, @Param("example") BadWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bad_word
     *
     * @mbggenerated Mon Apr 11 16:37:16 CST 2016
     */
    int updateByExample(@Param("record") BadWord record, @Param("example") BadWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bad_word
     *
     * @mbggenerated Mon Apr 11 16:37:16 CST 2016
     */
    int updateByPrimaryKeySelective(BadWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bad_word
     *
     * @mbggenerated Mon Apr 11 16:37:16 CST 2016
     */
    int updateByPrimaryKey(BadWord record);
}
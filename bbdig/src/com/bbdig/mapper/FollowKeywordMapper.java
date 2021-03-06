package com.bbdig.mapper;

import com.bbdig.entity.FollowKeyword;
import com.bbdig.entity.FollowKeywordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FollowKeywordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_keyword
     *
     * @mbggenerated Sat Apr 30 21:18:45 CST 2016
     */
    int countByExample(FollowKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_keyword
     *
     * @mbggenerated Sat Apr 30 21:18:45 CST 2016
     */
    int deleteByExample(FollowKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_keyword
     *
     * @mbggenerated Sat Apr 30 21:18:45 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_keyword
     *
     * @mbggenerated Sat Apr 30 21:18:45 CST 2016
     */
    int insert(FollowKeyword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_keyword
     *
     * @mbggenerated Sat Apr 30 21:18:45 CST 2016
     */
    int insertSelective(FollowKeyword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_keyword
     *
     * @mbggenerated Sat Apr 30 21:18:45 CST 2016
     */
    List<FollowKeyword> selectByExample(FollowKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_keyword
     *
     * @mbggenerated Sat Apr 30 21:18:45 CST 2016
     */
    FollowKeyword selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_keyword
     *
     * @mbggenerated Sat Apr 30 21:18:45 CST 2016
     */
    int updateByExampleSelective(@Param("record") FollowKeyword record, @Param("example") FollowKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_keyword
     *
     * @mbggenerated Sat Apr 30 21:18:45 CST 2016
     */
    int updateByExample(@Param("record") FollowKeyword record, @Param("example") FollowKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_keyword
     *
     * @mbggenerated Sat Apr 30 21:18:45 CST 2016
     */
    int updateByPrimaryKeySelective(FollowKeyword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_keyword
     *
     * @mbggenerated Sat Apr 30 21:18:45 CST 2016
     */
    int updateByPrimaryKey(FollowKeyword record);
}